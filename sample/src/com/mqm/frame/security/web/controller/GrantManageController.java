package com.mqm.frame.security.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.extendpoint.IGrantExtendPoint;
import com.mqm.frame.security.acl.extendpoint.IGrantShowShape;
import com.mqm.frame.security.acl.extendpoint.IPrincipalType;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecParams;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.util.SecurityUtil;
import com.mqm.frame.util.web.JsonTreeVo;

/**
 * 
 * <pre>
 * 授权管理前端控制器。
 * </pre>
 * 
 * @author linjunxiong linjunxiong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("security/authorization")
public class GrantManageController extends FbrpBaseController {

	@Resource
	private IRoleService roleService;
	@Resource
	private IStaffService staffService;
	@Resource
	private IGrantService grantService;

	/**
	 * 获取Tabnames。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @param map
	 *            ModelMap
	 * 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("grantManage.do")
	public String getTabnames(HttpServletRequest request, ModelMap map) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		List<String> tabnames = new ArrayList<String>();
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
			tabnames.add(grantExtendPoint.getGrantName());
			IGrantShowShape valueGrant = grantExtendPoint.getGrantShowShape();
			showShapeMap.put(grantExtendPoint.getGrantName(), valueGrant);
		}
		FbrpSecRole fbrpSecRole = new FbrpSecRole();
		FbrpSecParams params = new FbrpSecParams();
		params.setParam3(this.getUser().getOrgId());
		params.setParam4(this.getUser().getOrgName());
		//FbrpSecStaff fbrpSecStaff = new FbrpSecStaff();
		PagedResult<FbrpSecRole> pr1 = this.roleService.queryByParamFilterAdminRole(fbrpSecRole, pageIndex, pageSize,this.getUser().getOrgId());
	 
		//PagedResult<FbrpSecStaff> pr2 = this.staffService.pagedQuery(fbrpSecStaff, pageIndex, pageSize);
		//map.put("map", showShapeMap);
		map.put("tabnames", tabnames);
		map.put("pr1", pr1);
		map.put("vo", params);
		//map.put("pr2", pr2);
		return "security/authorization/grantManage";
	}

	/**
	 * 权限授权。
	 * 
	 * @param roleId
	 *            String
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @return int
	 */
	@RequestMapping(value = "grantManage.do", params = "updateGrant")
	public @ResponseBody
	int updateGrant(String roleId, HttpServletRequest request) {
		Map<String, IGrantShowShape> showShapeMap = getShowShapeMap();
		String idArray = request.getParameter("bSelect");
		Map<String,String[]> map = new HashMap<String,String[]>();
		for (String string : idArray.split("#111#")) {
			String[] arr = string.split("#222#");
			String[] value = arr.length==1 ? new String[0] : arr[1].split(",");
			map.put(arr[0], value);
		}
		
		Map<String,String> newEntry = new HashMap<String, String>();
		for (Map.Entry<String, IGrantShowShape> ent: showShapeMap.entrySet()) {
			String clsName = ent.getValue().getModelClass().getName();
			String[] ids = map.get(ent.getKey());
			for (String id : ids) {
				newEntry.put(id, clsName);
			}
			/*if(ids.length>0){
				
				int resultCode = showShapeMap.get(null).updateGranted(roleId, ids);
				if (resultCode == 0) {
					return 0;
				}
			}*/
		}
		
		this.grantService.updateGrant(roleId, newEntry);
		
		return 1;

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
			if (showShapeMap.get(key).getGrantExtendPoint() == null) {
				showShapeMap.get(key).setGrantExtendpoint(mappoint.get(key), true);
			}
		}
		return showShapeMap;
	}

	/**
	 * 点击了查询按钮。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @param map
	 *            ModelMap
	 * 
	 * @param param1
	 *            String
	 * 
	 * @param param2
	 *            String
	 * 
	 * @return PagedResult
	 */
	@RequestMapping(value = "grantManage.do", params = "query")
	public @ResponseBody
	PagedResult<FbrpSecRole> buttonquery(HttpServletRequest request, ModelMap map, FbrpSecParams params) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		FbrpSecRole fbrpSecRole = new FbrpSecRole();
		fbrpSecRole.setCode(params.getParam1());
		fbrpSecRole.setName(params.getParam2());
		String orgcode = params.getParam3();
		PagedResult<FbrpSecRole> pagedResult = this.roleService.queryByParamFilterAdminRole(fbrpSecRole, pageIndex, pageSize,orgcode);
		// PagedResult<FbrpSecRole> pagedQuery =
		// this.roleService.pagedQuery(fbrpSecRole, currentPage, pageSize);
		/*
		 * else if ("byStaff".equals(type)) { FbrpSecStaff fbrpSecStaff = new
		 * FbrpSecStaff(); fbrpSecStaff.setCode(param1);
		 * fbrpSecStaff.setLoginid(param2); pagedResult =
		 * this.staffService.pagedQuery(fbrpSecStaff, pageIndex, pageSize); }
		 */
		return pagedResult;
	}

	/**
	 * 点击了重置按钮。
	 */
	public void buttonreset() {
		// this.selectPageBean.reset();
	}

	/**
	 * 改变Role权限。
	 * 
	 * @param roleid
	 *            String
	 * 
	 * @param principalinstanname
	 *            String
	 *            
	 * @param sjssjgDm
	 * 			  String
	 * 
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "grantManage.do", params = "changeRole")
	public  Map<String, Object> changeRole (String roleid, String principalinstanname,String sjssjgDm) {
		if (roleid == null || "".equals(roleid.trim())) {
			return null;
		}
		Map<String, IGrantExtendPoint> mappoint = SecurityUtil.getAllGrantExtendPoint();

		HashMap<String, Object> map = new HashMap<String, Object>();
		Map<String, IGrantShowShape> showShapeMap = getShowShapeMap();
		
		for (String key : showShapeMap.keySet()) {
			IGrantShowShape shape = showShapeMap.get(key);
			if (shape.getGrantExtendPoint() == null) {
				shape.setGrantExtendpoint(mappoint.get(key), true);
			}
			IPrincipalType pType = mappoint.get(key).getPrincipalType();
			if (pType == null || pType.getPrincipalName().equals("角色")) {
				boolean showCheckbox;
				if(sjssjgDm==null){
					if("supadmin".equals(this.getUser().getLoginId())){
						shape.init(Arrays.asList(roleid), true);
						showCheckbox = true;
					}else{
						 shape.init(Arrays.asList(roleid), false);
						 showCheckbox = false;
					}
				}else if(sjssjgDm.equals(this.getUser().getOrgId())){
					shape.init(Arrays.asList(roleid), true);
					showCheckbox = true;
				}else{
					if("supadmin".equals(this.getUser().getLoginId())){
						shape.init(Arrays.asList(roleid), true);
						showCheckbox = true;
					}else{
						 shape.init(Arrays.asList(roleid), false);
						 showCheckbox = false;
					}
				}
				Object[] result = shape.getResult();
				if("TREE".equals(shape.getType())){
					result = filterOrphanNode(result);
				}
				Object[] obj = { shape.getType(), result };
				map.put(key, obj);
				map.put("showCheckbox", showCheckbox);
			}
		}
		return map;
	}

	private Object[] filterOrphanNode(Object[] result) {
		List<JsonTreeVo> rs = (List<JsonTreeVo>)result[0];
		Map<String,JsonTreeVo> mm = new HashMap<String, JsonTreeVo>();
		for (Object obj : rs) {
			if (obj instanceof JsonTreeVo) {
				JsonTreeVo menu = (JsonTreeVo) obj;
				mm.put(menu.getId(), menu);
			}
		}
		List<JsonTreeVo> list = new ArrayList<JsonTreeVo>();
		for (Object obj : rs) {
			if (obj instanceof JsonTreeVo) {
				JsonTreeVo menu = (JsonTreeVo) obj;
				int safe = 0;
				JsonTreeVo pMenu = menu; 
				while(safe<30){
					safe++;
					String pId = pMenu.getpId();
					if(pId==null||"0".equals(pId)){
						list.add(menu);
						break;
					}else if(mm.containsKey(pId)){
						pMenu = mm.get(pId);
					}else{
						break;
					}
				}
			}
		}
		result = new Object[]{this.toJson(list)};
		return result;
	}

}
