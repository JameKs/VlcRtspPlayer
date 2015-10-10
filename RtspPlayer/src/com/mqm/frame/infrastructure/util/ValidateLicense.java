/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.util.DateTimeUtil;
import com.mqm.frame.util.InternationalizationUtil;
import com.mqm.frame.util.license.CollectMacAddress;
import com.mqm.frame.util.license.LicenseColl;
import com.mqm.frame.util.license.LicenseInfo;

/**
 * <pre>
 * 完成License的校验。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ValidateLicense {

	private static final Log log = LogFactory.getLog(ValidateLicense.class);

	/**
	 * 校验License。
	 * 
	 * @param licenseColl LicenseColl
	 * @param path String
	 * @return boolean
	 */
	public static boolean validateLicense(LicenseColl licenseColl, String path) {
		// 是否为合法用户
		boolean payedUser = true;
		List<String> macAddressList = CollectMacAddress.getMacAddress();
		for (LicenseInfo licenseInfo : licenseColl.getLicenseInfoList()) {
			String productName = licenseInfo.getProductName();
			String expirationDate = licenseInfo.getExpiration();
			String signature = licenseInfo.getSignature();
			boolean flag = false;
			String data = new StringBuffer().append(productName)
					.append(expirationDate).toString();
			java.security.cert.CertificateFactory cf;
			try {
				cf = java.security.cert.CertificateFactory.getInstance("X.509");

				java.security.cert.Certificate cert = cf
						.generateCertificate(new FileInputStream(path
								+ "/WEB-INF/fbrp.cer"));

				// 获得公钥
				PublicKey pubKey = cert.getPublicKey();
				// 创建同公钥关联的Signature对象
				Signature sig = Signature.getInstance("SHA1withDSA");
				sig.initVerify(pubKey);
				sig.update(InternationalizationUtil.getBytes(data));
				// 验证License
				boolean verifies = sig.verify(Base64.decodeBase64(InternationalizationUtil
						.getBytes(signature)));
				if (verifies) {
					Date evalDate = DateTimeUtil.parseDate(expirationDate);
					if (evalDate.before(new Date())) {
						// 非法用户
						payedUser = false;
					} else {
						flag = true;
						payedUser = true;
						System.out.println("您使用的" + productName
								+ " License将于" + expirationDate + "日过期！");
					}
				}
			} catch (Exception e) {
				log.error("", e);
			}
			
			if (!flag) {
				payedUser = false;
				System.out.println("您使用的" + productName
						+ " License已过期，请与我们联系并获取License！");
			}
		}
		return payedUser;
	}

}