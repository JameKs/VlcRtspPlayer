package com.mqm.frame.infrastructure.base.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 泛型Controller基类。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @param <T>
 */
public abstract class GenericController<T extends ValueObject> {
	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * 常量。
	 */
	public static final String VIEW_EDIT = "edit";
	
	/**
	 * 常量。
	 */
	public static final String VIEW_LIST = "list";
	
	/**
	 * 常量。
	 */
	public static final String SUCCESS = "success";

	protected abstract IGenericService<T> getService();

	/**
	 * 创建实体对象，将其存放到model。
	 * 
	 * @param model Model
	 * 
	 * @return String
	 */
	// @RequestMapping("add.do")
	public String add(Model model) {
		T t = this.getService().newValueObject();
		model.addAttribute("vo", t);
		return VIEW_EDIT;
	}

	/**
	 * 查找实体，将其存放到model。
	 * 
	 * @param voId String
	 * 
	 * @param model Model
	 * 
	 * @return String
	 */
	// @RequestMapping("edit.do")
	public String edit(String voId, Model model) {
		T vo = this.getService().find(voId);
		if (vo == null) {
			throw new FbrpException("Data not found @" + voId);
		}
		model.addAttribute("vo", vo);
		return VIEW_EDIT;
	}
	
	/**
	 * 创建实体。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param t T
	 * 
	 * @return String
	 */
	// @RequestMapping("create.do")
	public String create(HttpServletRequest request, @RequestParam("vo") T t) {
		// TODO luxiaocheng 这里可以考虑加入校验信息
		this.getService().create(t);
		// TODO luxiaocheng 需要读取子类中，类级别中@RequestMapping的设置值
		return "redirect:" + VIEW_LIST;
	}

	/**
	 * 更新实体。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param t T
	 * 
	 * @return String
	 */
	// @RequestMapping("update.do")
	public String update(HttpServletRequest request, @RequestParam("vo") T t) {
		// TODO luxiaocheng 这里可以考虑加入校验信息
		this.getService().update(t);
		// TODO luxiaocheng 需要读取子类中，类级别中@RequestMapping的设置值
		return "redirect:" + VIEW_LIST;
	}

	/**
	 * 创建或更新实体。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param t T
	 * 
	 * @return String
	 */
	// @RequestMapping("createOrUpdate.do")
	public String createOrUpdate(HttpServletRequest request,
			@RequestParam("vo") T t) {
		// TODO luxiaocheng 这里可以考虑加入校验信息
		this.getService().createOrUpdate(t);
		// TODO luxiaocheng 需要读取子类中，类级别中@RequestMapping的设置值
		return "redirect:" + VIEW_LIST;
	}

	/**
	 * 根据PMap查询条件，分页查找实体类信息，并作排序操作，将其存放到model。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param obj Object
	 * 
	 * @param model Model
	 * 
	 * @return String
	 */
	// TODO luxiaocheng 这里可以考虑加入排序信息
	// @RequestMapping("query.do")
	public String query(HttpServletRequest request,
			@RequestParam("condition") Object obj, Model model) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PMap pm = this.onPagedQuery(obj);
		model.addAttribute("pr",
				this.getService().pagedQuery(pm, pageIndex, pageSize));
		return VIEW_LIST;
	}

	/**
	 * 列出某个实体类在数据库中的所有记录，将其存放到model。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param model Model
	 * 
	 * @return String
	 */
	// TODO luxiaocheng 这里可以考虑加入排序信息
	// @RequestMapping("list.do")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("datas", this.getService().list());
		return VIEW_LIST;
	}

	// --------------------------------------------------------------------------------------

	/**
	 * 
	 * 创建实体对象。

	 * @return T
	 */
	// @RequestMapping("addAjax.do")
	public T addAjax() {
		T t = this.getService().newValueObject();
		return t;
	}

	/**
	 * 根据voId查找实体类。
	 * 
	 * @param voId String
	 * 
	 * @return T
	 */
	// @RequestMapping("editAjax.do")
	public T edit(String voId) {
		T vo = this.getService().find(voId);
		if (vo == null) {
			throw new FbrpException("Data not found @" + voId);
		}
		return vo;
	}

	/**
	 * 创建实体。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param t T
	 * 
	 * @return String
	 */
	// @RequestMapping("createAjax.do")
	public String createAjax(HttpServletRequest request, @RequestParam("vo") T t) {
		// TODO luxiaocheng 这里可以考虑加入校验信息
		// TODO luxiaocheng 使用异常机制处理创建过程中发生的异常
		this.getService().create(t);
		// TODO luxiaocheng 可根据不同的参数值返回：标志、ID、实体类
		return SUCCESS;
	}

	/**
	 * 更新实体。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param t T
	 * 
	 * @return String
	 */
	// @RequestMapping("updateAjax.do")
	public String updateAjax(HttpServletRequest request, @RequestParam("vo") T t) {
		// TODO luxiaocheng 这里可以考虑加入校验信息
		this.getService().update(t);
		return SUCCESS;
	}

	/**
	 * 创建或更新实体。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param t T
	 * 
	 * @return String
	 */
	// @RequestMapping("createOrUpdateAjax.do")
	public String createOrUpdateAjax(HttpServletRequest request,
			@RequestParam("vo") T t) {
		// TODO luxiaocheng 这里可以考虑加入校验信息
		this.getService().createOrUpdate(t);
		return SUCCESS;
	}

	/**
	 * 根据PMap查询条件，分页查找实体类信息，并作排序操作。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param obj Object
	 * 
	 * @return PagedResult<T>
	 */
	// TODO luxiaocheng 这里可以考虑加入排序信息
	// @RequestMapping("queryAjax.do")
	public PagedResult<T> queryAjax(HttpServletRequest request,
			@RequestParam("condition") Object obj) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PMap pm = this.onPagedQuery(obj);
		return this.getService().pagedQuery(pm, pageIndex, pageSize);
	}

	/**
	 * 列出某个实体类在数据库中的所有记录。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param model Model
	 * 
	 * @return List<T>
	 */
	// TODO luxiaocheng 这里可以考虑加入排序信息
	// @RequestMapping("listAjax.do")
	public List<T> listAjax(HttpServletRequest request, Model model) {
		return this.getService().list();
	}

	// --------------------------------------------------------------------------------------

	private PMap onPagedQuery(Object obj) {
		if (obj instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) obj;
			return this.onPagedQuery(map);
		} else if (obj instanceof ValueObject) {
			ValueObject vo = (ValueObject) obj;
			return this.onPagedQuery(vo);
		} else {
			throw new FbrpException("仅接受Map类型或ValueObject类型的参数.");
		}
	}

	protected PMap onPagedQuery(Map<String, Object> map) {
		throw new FbrpException("Controller 调用分页查询时，未实现onPagedQuery方法.");
	}

	protected PMap onPagedQuery(T t) {
		throw new FbrpException("Controller 调用分页查询时，未实现onPagedQuery方法.");
	}

	protected int getPageIndex(HttpServletRequest request) {
		String str = request.getParameter("pageIndex");
		if (str == null && !StringUtils.hasText(str)) {
			return 1;
		}
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.info("pageIndex 参数转换出现错误!", e);
		}
		return 1;
	}

	protected int getPageSize(HttpServletRequest request) {
		String str = request.getParameter("pageSize");
		if (str == null && !StringUtils.hasText(str)) {
			return 10;
		}
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			log.info("pageSize 参数转换出现错误!", e);
		}
		return 10;
	}

}
