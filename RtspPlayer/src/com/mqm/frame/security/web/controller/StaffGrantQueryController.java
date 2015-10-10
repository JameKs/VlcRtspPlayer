package com.mqm.frame.security.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.util.VOUtils;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.extendpoint.IGrantShowShape;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.util.SecurityUtil;
import com.mqm.frame.util.StringUtil;

/**
 * 
 * <pre>
 * 人员权限查询控制器。
 * </pre>
 * 
 * @author lijiawei lijiawei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("security/authorization")
public class StaffGrantQueryController extends FbrpBaseController {

	private static final Log log = LogFactory
			.getLog(StaffGrantQueryController.class);

	@Resource
	private IStaffService staffService;
	@Resource
	private IRoleService roleService;
	/**
	 * <pre>
	 * 加工查询条件VO。
	 * 员工(Staff)VO作为查询条件，非超级管理员时，填充逻辑应用ID节为查询条件。
	 * </pre>
	 * 
	 * @param vo
	 *            员工
	 * @param isSupAdmin
	 *            是否超级管理员
	 */
	private void processConditionVo(FbrpSecStaff vo, boolean isSupAdmin) {
		if (vo == null) {
			return;
		}
		if (StringUtil.isStrEmpty(vo.getOrgName())) {
			vo.setOrgid(null);
		}
		String code = vo.getCode();
		if (!StringUtil.isStrEmpty(code)) {
			vo.setCode(code.trim());
		}
		String name = vo.getName();
		if (!StringUtil.isStrEmpty(name)) {
			vo.setName(name.trim());
		}
	}

	/**
	 * 根据查询条件查询人员权限列表。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param condStaffVO FbrpSecStaff
	 * 
	 * @param map ModelMap
	 *  
	 * @return String
	 */
	@RequestMapping("staffGrantQuery.do")
	public String queryGrant(HttpServletRequest request,
			@ModelAttribute("paramVo") FbrpSecStaff condStaffVO, ModelMap map) {
		processConditionVo(condStaffVO, false);
		try {
			int pageIndex = this.getPageIndex(request);
			int pageSize = this.getPageSize(request);
			
			List<String> tabnames = new ArrayList<String>();
			Map<String, IGrantExtendPoint> mappoint = SecurityUtil
					.getAllGrantExtendPoint();
			List<IGrantExtendPoint> list = new ArrayList();
			list.addAll(mappoint.values());
			Collections.sort(list, new Comparator<IGrantExtendPoint>() {
				public int compare(IGrantExtendPoint o1, IGrantExtendPoint o2) {
					return o1.getSortNo() - o2.getSortNo();
				}
			});
			Map<String, IGrantShowShape> showShapeMap = new HashMap<String, IGrantShowShape>();
			for (IGrantExtendPoint grantExtendPoint : list) {
				tabnames.add(grantExtendPoint.getGrantName());
				IGrantShowShape valueGrant = grantExtendPoint.getGrantShowShape();
				showShapeMap.put(grantExtendPoint.getGrantName(), valueGrant);
			}
			condStaffVO.setOrgCode(this.getUser().getOrgId());
			condStaffVO.setOrgName(this.getUser().getOrgName());
			PagedResult<FbrpSecStaff> pr = this.staffService.pagedQuery(
					condStaffVO,pageIndex, pageSize, Order.ASC("code"),
					Order.ASC("name"));
			map.put("map", showShapeMap);
			map.put("tabnames", tabnames);
			map.put("pr", pr);
			/*this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
					FbrpCommonAuditLog.LOGTYPE_SELECT, "获取人员信息列表");*/

		} catch (Exception e) {
			log.error("query error!", e);
			this.exceptionMsg.setMainMsg("查询时异常！");
		}
		return "security/authorization/staffGrantQuery";
	}
	
	/**
	 * 根据查询条件查询人员列表.
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param condStaffVO FbrpSecStaff
	 * 
	 * @return PagedResult<FbrpSecStaff>
	 */
	@RequestMapping(value = "staffGrantQuery.do", params = "queryStaff")
	@ResponseBody
	public PagedResult<FbrpSecStaff> queryStaffGrant(HttpServletRequest request,
			@ModelAttribute("paramVo") FbrpSecStaff condStaffVO) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
//		condStaffVO.setOrgCode(this.getUser().getOrgId());
		PagedResult<FbrpSecStaff> pr = this.staffService.pagedQuery(condStaffVO, pageIndex, pageSize, Order.ASC("code"), Order.ASC("name"));
		return pr;
	}

	/**
	 * 查看Staff权限。
	 * 
	 * @param staffid String
	 * 
	 * @param principalinstanname String
	 *  
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "staffGrantQuery.do", params = "changeStaff")
	public @ResponseBody
	Map<String, Object> changeStaff(@RequestParam String staffid, @RequestParam String principalinstanname) {
		if (staffid == null || "".equals(staffid.trim())) {
			return null;
		}
		Map<String, IGrantExtendPoint> mappoint = SecurityUtil.getAllGrantExtendPoint();

		HashMap<String, Object> map = new HashMap<String, Object>();
		Map<String, IGrantShowShape> showShapeMap = getShowShapeMap();
		@SuppressWarnings("unchecked")
		List<FbrpSecRole> roles = this.roleService.getRolesByStaffId(staffid);
		for (String key : showShapeMap.keySet()) {
			IGrantExtendPoint point = mappoint.get(key);
			IGrantShowShape shape = showShapeMap.get(key);
			if (shape.getGrantExtendPoint() == null) {
				shape.setGrantExtendpoint(point, true);
			}
			if (point.getPrincipalType() == null || point.getPrincipalType().getPrincipalName().equals("角色")) {
				shape.init(VOUtils.extractIds(roles), false);
				Object[] obj = { shape.getType(), shape.getResult() };
				map.put(key, obj);
			}
		}
		return map;
	}
	
	/**
	 * 获取权限。
	 * 
	 * @return Map<String, IGrantShowShape>
	 */
	public Map<String, IGrantShowShape> getShowShapeMap() {
		Map<String, IGrantExtendPoint> mappoint = SecurityUtil.getAllGrantExtendPoint();
		List<IGrantExtendPoint> list = new ArrayList();
		list.addAll(mappoint.values());
		Collections.sort(list, new Comparator<IGrantExtendPoint>() {
			public int compare(IGrantExtendPoint o1, IGrantExtendPoint o2) {
				return o1.getSortNo() - o2.getSortNo();
			}
		});
		Map<String, IGrantShowShape> showShapeMap = new HashMap<String, IGrantShowShape>();
		for (IGrantExtendPoint grantExtendPoint : list) {
			IGrantShowShape valueGrant = grantExtendPoint.getGrantShowShape();
			showShapeMap.put(grantExtendPoint.getGrantName(), valueGrant);
		}

		for (String key : showShapeMap.keySet()) {
			IGrantShowShape grantShape = showShapeMap.get(key);
			if (grantShape.getGrantExtendPoint() == null) {
				grantShape.setGrantExtendpoint(mappoint.get(key), true);
			}
		}
		return showShapeMap;
	}
	
	
	
	
	
	
/*	@RequestMapping(value="staffGrantQuery.do", params="changeStaff")
	public @ResponseBody Map<String,Object>  changeStaff(@RequestParam String staffId) {
		 Map<String, IGrantShowShape> map = new HashMap<String, IGrantShowShape>();
		List<String> tabnames = new ArrayList<String>();
		Map<String, IGrantExtendPoint> mappoint = SecurityUtil.getAllGrantExtendPoint();
		List<IGrantExtendPoint> list = new ArrayList();
		list.addAll(mappoint.values());
		Collections.sort(list, new Comparator<IGrantExtendPoint>() {
			public int compare(IGrantExtendPoint o1, IGrantExtendPoint o2) {
				return o1.getSortNo() - o2.getSortNo();
			}
		});
		for (IGrantExtendPoint grantExtendPoint : list) {
			tabnames.add(grantExtendPoint.getGrantName());
			IGrantShowShape valueGrant = grantExtendPoint.getGrantShowShape();
			valueGrant.setGrantExtendpoint(grantExtendPoint,false);
			map.put(grantExtendPoint.getGrantName(), valueGrant);
		}
				
		Map<String, IPrincipalType> beanmap = ContextUtil.getApplicationContext().getBeansOfType(IPrincipalType.class);
		
		List list2 = new ArrayList();
		for (IPrincipalType principalType : beanmap.values()) {
			list2.addAll(principalType.getPrincipalsByStaffId(staffId));
		}
		List sids = new ArrayList();
		for (int i = 0; i < list2.size(); i++) {
			sids.add(getObjectMethodResult(list2.get(i), "getId"));
		}
		
		HashMap<String, Object> resultmap = new HashMap<String, Object>();
		for (String key : map.keySet()) {
		    map.get(key).init(sids,false);
			Object[] obj = {map.get(key).getType(),map.get(key).getResult()};
			resultmap.put(key, obj);
		}
		resultmap.put("tabnames", tabnames);
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFFGRANTQUERY, FbrpCommonAuditLog.LOGTYPE_SELECT, "获取人员["+staffId+"]的所有的权限");
		return resultmap;
	}

	private String getObjectMethodResult(Object object, String methodName) {
		Class<?> typeClass = ClassUtils.getUserClass(object.getClass());
		String result = "";
		try {
			Method method = typeClass.getMethod(methodName, new Class[] {});
			result = (String) method.invoke(object, new Object[] {});
		} catch (Exception e) {
			log.error(typeClass.getName() + "的getId 方法不存在", e);
		}
		return result;
	}*/

}
