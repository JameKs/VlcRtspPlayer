package com.icanft.common.main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.icanft.cdgl.service.ICdxxService;
import com.icanft.cdgl.vo.Cdxx;
import com.icanft.cdgl.vo.JsonTree;
import com.icanft.common.ContextUtil;
import com.icanft.common.DefaultController;
import com.icanft.common.constant.BaseConstants;
import com.icanft.xtgl.yhgl.vo.User;

/**
 * 
 * <pre>
 * 框架入口对应的。
 * </pre>
 * 
 * @author meihu
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
public class MainController extends DefaultController {

	private static final Log log = LogFactory.getLog(MainController.class);

	@Resource
	private ICdxxService cdxxService;

	/**
	 * 框架入口。
	 * 
	 * @param mm
	 *            ModelMap
	 * 
	 * @return String
	 */
	@RequestMapping("main.do")
	public String main(ModelMap mm) {
		return "main";
	}

	/**
	 * 顶部入口。
	 * 
	 * @param mm
	 *            ModelMap
	 * 
	 * @return String
	 */
	@RequestMapping("header.do")
	public String header(ModelMap mm) {
		return "header";
	}

	@RequestMapping(value = "header.do", params = "getToken")
	@ResponseBody
	public String getToken(HttpServletRequest request) {
		String authorizerdm = request.getParameter("authorizerdm");
		String loginId = request.getParameter("loginId");
		String accountname = null;
		if (!StringUtils.isEmpty(authorizerdm)) {
			accountname = authorizerdm;
		} else if (!StringUtils.isEmpty(loginId)) {
			accountname = loginId;
		}
		String token = null;// service.getToken(accountname);
		return token;
	}

	/**
	 * 左侧入口。
	 * 
	 * @param mm
	 *            ModelMap
	 * 
	 * @return String
	 */
	@RequestMapping("left.do")
	public String left(ModelMap mm, HttpServletRequest req) {
		User user = this.getUser();
		String userId = user.getId();
		
		List<Cdxx> voList = null;
		if ("3".equals(userId)) {//管理员加载所有的
			voList = cdxxService.findAll(BaseConstants.TREE_HAS_NO_ROOT);
		}
		voList = cdxxService.findAllUserMenu(userId);
		
		Collections.sort(voList, new Comparator<Cdxx>() {
			@Override
			public int compare(Cdxx o1, Cdxx o2) {
				return o1.getSortNo() - o2.getSortNo();
			}
		});

		List<JsonTree> jsonTrees = new ArrayList<JsonTree>();

		for (Cdxx vo : voList) {
			JsonTree jsonTree = new JsonTree();
			jsonTree.setId(vo.getId());
			jsonTree.setpId(vo.getpId());
			jsonTree.setName(vo.getCnName());

			if ("E1801EBE06D211E588790023540C477B".equals(jsonTree.getpId())) {
				jsonTree.setOpen(true);
			}
			jsonTree.getExts().put("url", vo.getUrl());
			jsonTree.getExts().put("icon", vo.getImageUrl());
			jsonTrees.add(jsonTree);
		}

		// TODO luxiaocheng 需要优化的代码
		HashMap<String, JsonTree> map = new HashMap<String, JsonTree>();
		HashMap<String, List<JsonTree>> collection = new HashMap<String, List<JsonTree>>();
		List<JsonTree> list = new ArrayList<JsonTree>();
		for (JsonTree vo : jsonTrees) {
			map.put(vo.getId(), vo);// 非根节点
			if ("E1801EBE06D211E588790023540C477B".equals(vo.getpId())) {// 根节点
				collection.put(vo.getId(), new ArrayList<JsonTree>());
				list.add(vo);// 根节点
			}
		}
		for (JsonTree vo : jsonTrees) {
			String p = vo.getpId();
			if (!"E1801EBE06D211E588790023540C477B".equals(p)) {// 非根节点
				int safe = 0;
				while (!collection.containsKey(p) && map.containsKey(p)) {// 非根节点包含，根节点不包含
					p = map.get(p).getpId();
					if (safe++ > 30) {
						break;
					}
				}
				if (collection.containsKey(p)) {
					collection.get(p).add(vo);
				}
			}
		}
		try {
			mm.put("treeData", JSONArray.fromObject(collection).toString());
			mm.put("top", JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			log.error("", e);
		}
		return "left";
	}


	/**
	 * 修改当前登录状态用户的密码。
	 * 
	 * @param oldPassword
	 *            String
	 * 
	 * @param newPassword
	 *            String
	 * 
	 * @param newPassword_confirm
	 *            String
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("editPassword")
	public String editPassword(String oldPassword, String newPassword,
			String newPassword_confirm) {
		User user = this.getUser();
		// FbrpSecStaff fbrpSecStaff = null;
		// fbrpSecStaff = this.staffService.find(user.getStaffId());
		//
		// this.userDetailsService.checkUser(fbrpSecStaff, oldPassword);
		//
		// if (oldPassword == null || "".equals(oldPassword)
		// || newPassword == null || "".equals(newPassword)
		// || oldPassword == null || "".equals(oldPassword)) {
		// return "密码不能为空";
		// }
		//
		// if (!oldPassword.equals(user.getPasswd())) {
		// return "旧密码错误";
		// }
		//
		// if (newPassword.equals(newPassword_confirm)) {
		// this.userDetailsService.savePassword(user.getLoginId(),
		// oldPassword, newPassword);
		// } else {
		// return "新密码两次输入不一致";
		// }
		return "success";
	}

	/**
	 * 在注销的时候，有必要先清空某些缓存数据。
	 * 
	 * @return String
	 * 
	 */
	@RequestMapping("mainlogout")
	public RedirectView mainlogout(String _csrf,HttpServletResponse resp) {
		// 这个数据要先清空了
		String page = (String) ContextUtil.get("", ContextUtil.SCOPE_SESSION);
		String url = "logout";
//		if (StringUtils.hasText(page)) {
//			return "redirect:/" + url + "?spring-security-redirect=" + page;
//		}
//		return "redirect:/" + url + "?_csrf=" + _csrf;
		return new RedirectView("logout", false, false, false);
	}

}
