/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

/**
 * <pre>
 * 扩展ExceptionTranslationFilter类。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpExceptionTranslationFilter extends GenericFilterBean {

    //~ Instance fields ================================================================================================

    private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
//    private PortResolver portResolver = new PortResolverImpl();
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    private RequestCache requestCache = new HttpSessionRequestCache();

    //~ Methods ========================================================================================================

    /* (non-Javadoc)
	 * @see org.springframework.web.filter.GenericFilterBean#afterPropertiesSet()
	 */
    @Override
    public void afterPropertiesSet() {
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint must be specified");
//        Assert.notNull(portResolver, "portResolver must be specified");
    }
    /* (non-Javadoc)
	 * @see org.springframework.web.filter.GenericFilterBean#doFilter
	 * (javax.servlet.ServletRequest, javax.servlet.ServletResponse,javax.servlet.FilterChain)
	 */
	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            chain.doFilter(request, response);

            if (logger.isDebugEnabled()) {
                logger.debug("Chain processed normally");
            }
        }
        catch (IOException ex) {
            throw ex;
        }
        catch (Exception ex) {
            // Try to extract a SpringSecurityException from the stacktrace
            Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
            RuntimeException ase = (AuthenticationException)
                    throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);

            if (ase == null) {
                ase = (AccessDeniedException)throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }

            if (ase != null) {
                handleException(request, response, chain, ase);
            } else {
                // Rethrow ServletExceptions and RuntimeExceptions as-is
                if (ex instanceof ServletException) {
                    throw (ServletException) ex;
                }
                else if (ex instanceof RuntimeException) {
                    throw (RuntimeException) ex;
                }

                // Wrap other Exceptions. These are not expected to happen
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 获取authenticationEntryPoint。
     * 
     * @return AuthenticationEntryPoint
     */
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    protected AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return authenticationTrustResolver;
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            RuntimeException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationException) {
        	
        	
        	
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication exception occurred; redirecting to authentication entry point", exception);
            }

            sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
        }
        else if (exception instanceof AccessDeniedException) {
            if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Access is denied (user is anonymous); redirecting to authentication entry point",
                            exception);
                }

                sendStartAuthentication(request, response, chain, new InsufficientAuthenticationException(
                        "Full authentication is required to access this resource"));
            }
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Access is denied (user is not anonymous); delegating to AccessDeniedHandler",
                            exception);
                }

                accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
            }
        }
    }

    protected void sendStartAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            AuthenticationException reason) throws ServletException, IOException {
        // SEC-112: Clear the SecurityContextHolder's Authentication, as the
        // existing Authentication is no longer considered valid
        SecurityContextHolder.getContext().setAuthentication(null);
        requestCache.saveRequest(request, response);
        logger.debug("Calling Authentication entry point.");
        authenticationEntryPoint.commence(request, response, reason);
    }

    /**
     * 设置accessDeniedHandler。
     * 
     * @param accessDeniedHandler AccessDeniedHandler
     */
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        Assert.notNull(accessDeniedHandler, "AccessDeniedHandler required");
        this.accessDeniedHandler = accessDeniedHandler;
    }

    /**
     * 设置authenticationEntryPoint。
     * 
     * @param authenticationEntryPoint AuthenticationEntryPoint
     */
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * 设置authenticationTrustResolver。
     * 
     * @param authenticationTrustResolver AuthenticationTrustResolver
     */
    public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
        Assert.notNull(authenticationTrustResolver, "authenticationTrustResolver must not be null");
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

//    public void setCreateSessionAllowed(boolean createSessionAllowed) {
//        this.createSessionAllowed = createSessionAllowed;
//    }

//    public void setPortResolver(PortResolver portResolver) {
//        this.portResolver = portResolver;
//    }

    /**
     * 设置throwableAnalyzer。
     * 
     * @param throwableAnalyzer ThrowableAnalyzer
     */
    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        Assert.notNull(throwableAnalyzer, "throwableAnalyzer must not be null");
        this.throwableAnalyzer = throwableAnalyzer;
    }

    /**
     * 设置requestCache。
     * 
     * @param requestCache RequestCache
     */
    public void setRequestCache(RequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache cannot be null");
        this.requestCache = requestCache;
    }

    /**
     * Default implementation of <code>ThrowableAnalyzer</code> which is capable of also unwrapping
     * <code>ServletException</code>s.
     */
    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {

    	@Override
        protected void initExtractorMap() {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
                public Throwable extractCause(Throwable throwable) {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }

    }


}
