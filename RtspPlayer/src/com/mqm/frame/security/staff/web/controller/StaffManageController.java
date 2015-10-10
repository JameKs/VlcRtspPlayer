/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.staff.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.service.IKeyGen;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.security.role.vo.FbrpSecRoleMember;
import com.mqm.frame.security.role.vo.FbrpSecRoleVO;
import com.mqm.frame.security.staff.service.IStaffService;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.security.staff.vo.GyUuvOrgVO;
import com.mqm.frame.security.staff.vo.StaffOAVO;
import com.mqm.frame.security.staff.vo.StaffUumVO;
import com.mqm.frame.util.CSVUtil;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 人员管理前端控制器。
 * </pre>
 * 
 * @author zouyongqiao zouyongqiao@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("security/staff")
public class StaffManageController extends FbrpBaseController {

	private static final Log log = LogFactory
			.getLog(StaffManageController.class);

	/** 分页插入时每页数据数量。 */
	public static final int PAGE_SIZE = 5000;

	// Service层
	@Resource
	private IStaffService staffService;
	@Resource
	private IGrantService grantService;
	@Resource
	private IRoleService roleService;
	
	@Resource
	private IKeyGen keyGen;

//	/**
//	 * 日期转换。
//	 * 
//	 * @param binder WebDataBinder
//	 */
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
//		binder.registerCustomEditor(Date.class, dateEditor);
//	}

	/**
	 * 根据条件查询。
	 * 
	 * @param request
	 *            HttpServletRequest
	 *            
	 * @param fbrpSecStaff
	 *            FbrpSecStaff
	 *            
	 * @param map
	 *            ModelMap
	 *            
	 * @param role          
	 *           FbrpSecRoleVO 
	 *           
	 * @return String
	 */
	@RequestMapping("staffManage.do")
	public String query(HttpServletRequest request,
			 FbrpSecStaff fbrpSecStaff, ModelMap map,
			FbrpSecRoleVO role) {
		Boolean isSupAdmin = this.grantService.isAdmin(getUser().getLoginId());
		processConditionVo(fbrpSecStaff, isSupAdmin);
		try {
			UserProfileVO user = this.getUser();
			fbrpSecStaff.setOrgName(user.getOrgName());
			fbrpSecStaff.setOrgCode(user.getOrgId());
			PagedResult<FbrpSecStaff> pr = loadPageResult(request, fbrpSecStaff);
			map.put("rootCode", user.getOrgId());
			map.put("pr", pr);
			map.put("pr1", new ArrayList<FbrpSecRoleVO>());
			map.put("editData", new FbrpSecStaff());
			map.put("setAccountData", new FbrpSecStaff());
			map.put("userCode", user.getLoginId());
			map.put("userVO", new StaffUumVO());
			
			FbrpSecRoleVO vo = new FbrpSecRoleVO();
			vo.setOrgName(user.getOrgName());
			map.put("paramVO", vo);
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
					FbrpCommonAuditLog.LOGTYPE_SELECT, "获取人员信息列表");
		} catch (Exception e) {
			log.error("query error!", e);
		}
		return "security/staff/staffManage";
	}

	/**
	 * 人员分页查询。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramVo
	 *            FbrpSecStaff
	 * @return PagedResult<FbrpSecStaff>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "query")
	public PagedResult<FbrpSecStaff> loadPageResult(HttpServletRequest request,
			FbrpSecStaff paramVo) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		
		if(StringUtil.isStrEmpty(paramVo.getOrgCode())){
			paramVo.setOrgCode(this.getUser().getOrgId());
		}	
		PagedResult<FbrpSecStaff> pr = this.staffService.pagedQuery(paramVo,
				pageIndex, pageSize, Order.ASC("loginid"), Order.ASC("name"));
		
		String loginId = this.getUser().getLoginId();
		if (pr != null) {
			for (FbrpSecStaff fss : pr.getData()) {
				if ("1".equals(fss.getSex())) {
					fss.setSex("男");
				} else if("0".equals(fss.getSex())){
					fss.setSex("女");
				}else{
					fss.setSex("");
				}
			}

			/*for (FbrpSecStaff fss : pr.getData()) {
				if ("SUPADMIN".equals(fss.getCode())) {
					pr.getData().remove(fss);
					pr.setTotal(pr.getTotal() - 1);
					break;
				}
			}
			for (FbrpSecStaff fss : pr.getData()) {
				if (loginId.equals(fss.getLoginid())) {
					pr.getData().remove(fss);
					pr.setTotal(pr.getTotal() - 1);
					break;
				}
			}*/
		}
		return pr;
	}

	/**
	 * 保存账户的设置或修改。
	 * 
	 * @param setAccountData FbrpSecStaff
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "saveAccount")
	public String saveAccount(
			@ModelAttribute("setAccountData") FbrpSecStaff setAccountData) {
		String id = setAccountData.getId();
		String loginid = setAccountData.getLoginid();
		try {
			if ((loginid != null && !"".equals(loginid.trim()))
					&& this.staffService.checkLoginIdExist(id, loginid)) {
				this.exceptionMsg.setMainMsg("帐号已存在");
			} else {
				this.staffService.saveOrUpdate(setAccountData);
				this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
						FbrpCommonAuditLog.LOGTYPE_INSERT, "新增人员帐号");
			}
		} catch (Exception e) {
			log.error("", e);
			this.exceptionMsg.setMainMsg("保存信息发生异常");
		}
		return "redirect:staffManage.do";
	}

	/**
	 * 删除一条数据。
	 * 
	 * @param id
	 *            String
	 *            
	 * @return String          
	 */
	@RequestMapping(value = "staffManage.do", params = "deleteRow")
	public String deleteStaff(@RequestParam String id) {
		FbrpSecStaff del = this.staffService.find(id);
		// TODO xiaocheng_lu 删除后，最好将该用户断开
		this.staffService.deleteStaffByLogic(del);
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
				FbrpCommonAuditLog.LOGTYPE_DELETE, "逻辑删除人员[" + del.getId()
						+ "]");
		return "redirect:staffManage.do";
	}

	/**
	 * 删除多条人员记录。
	 * 
	 * @param id String[]
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "deleteAll")
	public String deleteAll(String[] id) {
		if ("".equals(id) || id == null) {
			return "redirect:staffManage.do";
		}
		List<FbrpSecStaff> staffList = new ArrayList<FbrpSecStaff>();
		for (int i = 0; i < id.length; i++) {
			staffList.add(new FbrpSecStaff(id[i]));
		}
		this.staffService.deleteAll(staffList);
		return "redirect:staffManage.do";
	}

	private FbrpSecStaff createStaff() {
		FbrpSecStaff newStaff = new FbrpSecStaff();
		UserProfileVO user = super.getUser();
		newStaff.setCreatorId(user.getStaffId());
		newStaff.setCreatedTime(new Date());
		newStaff.setLastModifierId(user.getStaffId());
		newStaff.setLastModifiedTime(new Date());
		newStaff.setStatus("y");
		newStaff.setDelFlag("n");
		return newStaff;
	}

	/**
	 * 保存一条记录。
	 * 
	 * @param editData FbrpSecStaff
	 * 
	 * @param result BindingResult
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "save")
	public String ok(
			@ModelAttribute("editData") @Valid FbrpSecStaff editData,
			BindingResult result) {

		if (result.hasErrors()) {
			return "security/staff/staffEdit";
		}

		try {
			String sex = editData.getSex();
			if (sex != null && ("-1".equals(sex) || "".equals(sex.trim()))) {
				editData.setSex(null);
			}

			UserProfileVO user = super.getUser();
			editData.setCreatorId(user.getStaffId());
			editData.setCreatedTime(new Date());
			editData.setLastModifierId(user.getStaffId());
			editData.setLastModifiedTime(new Date());
			editData.setStatus("y");
			editData.setDelFlag("n");

			this.staffService.saveOrUpdate(editData);
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
					FbrpCommonAuditLog.LOGTYPE_INSERT, "新增人员");
		} catch (Exception e) {
			log.error("", e);
			this.exceptionMsg.setMainMsg("保存信息发生异常");
		}
		return "redirect:staffManage.do";
	}
	
	/**
	 * 手动保存人员信息。
	 * 
	 * @param user StaffUumVO
	 * 
	 * @return Map<String, String>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "saveStaff")
	public Map<String, String> saveStaff(@Valid StaffUumVO user) {
		Map<String, String> map = new HashMap<String, String>();
		UserProfileVO userP = super.getUser();
		user.setCjrDm(userP.getLoginId());
		this.staffService.saveUser(user, userP.getStaffId());
		map.put("success", "1");
		return map;
	}

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
	 * 导入文件。
	 * 
	 * @param multipartFile
	 *            MultipartFile
	 * @return 
	 *       String  
	 */
	@RequestMapping(value = "staffManage.do", params = "upload")
	public String mydoFieldImport(
			@RequestParam("myFile") MultipartFile multipartFile) {
		try {
			List<String> messageList = staffService.saveCSVData(multipartFile
					.getInputStream());
			// this.exceptionMsg.setMainMsg(messageList.toString());
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
					FbrpCommonAuditLog.LOGTYPE_INSERT, "批量导入人员");
		} catch (Exception e) {
			log.error("文件导入出错!", e);
			this.exceptionMsg.setMainMsg("文件导入出错!", e);
		}
		return "redirect:staffManage.do";
	}

	/**
	 * 导出文件。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param response HttpServletResponse
	 */
	@RequestMapping(value = "staffManage.do", params = "exportData")
	public void exportData(HttpServletRequest request,
			HttpServletResponse response) {
		exportCsv(request, response);
	}

	/**
	 * 导出CSV文件。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param response HttpServletResponse
	 */
	public void exportCsv(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 取得文件的绝对路径
			ServletContext servletCtx = request.getSession()
					.getServletContext();
			String fileDir = servletCtx.getRealPath("/staff_template");
			if (!mkdir(fileDir)) {
				this.exceptionMsg.setMainMsg("临时文件夹创建不成功");
				return;
			}
			String filePath = fileDir + "/staffTemplate.csv";
			log.info(filePath);

			File exportFile = new File(filePath);
			if (exportFile.exists()) {
				exportFile.delete();
			}
			createCSVFile(exportFile);
			InputStream is = new FileInputStream(exportFile);
			this.downLoadFile(is, "/staffTemplate.csv",
					"application/x-download;charset=UTF-8", response);
			is.close();
			this.auditLog(FbrpCommonAuditLog.BIZTYPE_STAFF,
					FbrpCommonAuditLog.LOGTYPE_SELECT, "导出人员");
		} catch (IOException e) {
			log.error("", e);
			this.exceptionMsg.setMainMsg("导出时发生异常");
		}
	}

	/**
	 * 用于创建文件夹的方法。
	 * 
	 * @param mkdirName
	 *            String
	 * 
	 * @return boolean
	 */
	public boolean mkdir(String mkdirName) {
		boolean isDir = false;
		try {
			File dirFile = new File(mkdirName);
			isDir = dirFile.exists();
			if (!isDir) {
				isDir = dirFile.mkdir();
			}
		} catch (Exception e) {
			log.error("", e);
			return false;
		}
		return isDir;
	}

	/**
	 * 创建CSV文件。
	 * 
	 * @param file
	 *            File
	 */
	private void createCSVFile(File file) {
		List<String> headerList = new ArrayList<String>();
		headerList.add("ID");
		headerList.add("CODE");
		headerList.add("NAME");
		headerList.add("STATUS");
		headerList.add("ID_NUMBER");
		headerList.add("BIRTHDAY");
		headerList.add("SEX");
		headerList.add("TEL");
		headerList.add("EMAIL");
		headerList.add("ADDRESS");
		headerList.add("LOGIN_ID");
		headerList.add("PASSWD");
		headerList.add("FAILED_LOGIN_COUNT");
		headerList.add("MAX_FAILED_LOGIN_COUNT");
		headerList.add("PASSWD_EXPIRE_DAYS");
		headerList.add("PASSWD_EXPIRE_TIME");
		headerList.add("ACCOUNT_EXPIRE_TIME");
		headerList.add("LAST_LOGIN_IP");
		headerList.add("LAST_LOGIN_TIME");
		headerList.add("LOGIN_IP");
		headerList.add("LOGIN_TIME");
		headerList.add("ONLINE_COUNT");
		headerList.add("EXT1");
		headerList.add("EXT2");
		headerList.add("EXT3");
		headerList.add("EXT4");
		headerList.add("EXT5");
		headerList.add("EXT6");
		headerList.add("EXT7");
		headerList.add("EXT8");
		headerList.add("EXT9");
		headerList.add("CREATOR_ID");
		headerList.add("CREATED_TIME");
		headerList.add("LAST_MODIFIER_ID");
		headerList.add("LAST_MODIFIED_TIME");
		headerList.add("DEL_FLAG");
		headerList.add("VERSION");
		CSVUtil.createCSVHeader(file, headerList);

		String sql = "SELECT a.* FROM FBRP_SEC_STAFF a where DEL_FLAG='"
				+ FbrpSecStaff.DEL_FLAG_NORMAL + "' ";
		// String sql =
		// " SELECT * FROM FBRP_SEC_STAFF  WHERE DEL_FLAG='n' AND CODE LIKE '%"+this.condStaffVO.getCode()+"%' AND NAME LIKE '%"+this.condStaffVO.getName()+"%'";
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("code", "%"+this.condStaffVO.getCode()+"%");
		// paramMap.put("name", "%"+this.condStaffVO.getName()+"%");

		int count = 0;
		// this.sqlExecutionEngineService.getResultCount(
		// BaseConstants.AUTH_localDataBase, sql);
		int queryTotal = 0;
		List<Map<String, Object>> data;
		while (queryTotal < count) {
			data = null;
			// this.sqlExecutionEngineService
			// .querySql(BaseConstants.AUTH_localDataBase,
			// InternationalizationUtil.toLowerCase(sql),
			// queryTotal, 5000);
			queryTotal += 5000;
			CSVUtil.appendCSVContent(file, data, headerList);
		}
	}

	/**
	 * 下载模板。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param response HttpServletResponse
	 */
	@RequestMapping(value = "staffManage.do", params = "downloadTemp")
	public void downloadTemp(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			download(request, response);
		} catch (Exception e) {
			log.error("下载模板出错", e);
			this.exceptionMsg.setMainMsg(e);
		}
	}

	/**
	 * 下载。
	 * 
	 * @throws Exception
	 *             异常
	 */
	private void download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 输出文件
		response.setContentType("application/x-msdownload");
		response.setHeader("Cache-Control", "max-age=0");
		response.setHeader("Content-Disposition",
				"attachment; filename=staffTemplate.csv");
		OutputStream outputStream = null;
		outputStream = response.getOutputStream();

		FileInputStream fis = new FileInputStream(request.getRealPath("/")
				+ "/pages/fbrp/security/staff/staffTemplate.csv");
		BufferedInputStream inputStream = new BufferedInputStream(fis);
		BufferedOutputStream out = new BufferedOutputStream(outputStream);

		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, i);
		}
		out.flush();
		inputStream.close();
		outputStream.close();
		out.close();
	}

	/**
	 * 返回 PagedResult。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param map ModelMap
	 * 
	 * @return PagedResult<FbrpSecRole>
	 */
	@RequestMapping(value = "staffManage.do", params = "getRoles")
	public @ResponseBody
	PagedResult<FbrpSecRole> getRoles(HttpServletRequest request, ModelMap map) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		FbrpSecRole fbrpSecRole = null;
		PagedResult<FbrpSecRole> pr  = this.roleService.queryByParamFilterAdminRole(
				fbrpSecRole, pageIndex, pageSize,this.getUser().getOrgId());
		 
		return pr;
	}
	
	/**
	 * 为人员设置角色。
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "setRole")
	public String setRole() {
		return "staffList/setRole";
	}

	/**
	 * 人员角色查询功能。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * 
	 * @param role
	 *            FbrpSecRoleVO
	 * 
	 * @return PagedResult<FbrpSecRoleVO>
	 */
	@RequestMapping(value = "staffManage.do", params = "queryRole")
	@ResponseBody
	public PagedResult<FbrpSecRoleVO> queryStaff(HttpServletRequest request,
			FbrpSecRoleVO role) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		String parameter = request.getParameter("filterFlag");
		String staffid = request.getParameter("staffid");
		role.setOrgCode(this.getUser().getOrgId());
		role.setStaffid(staffid);
		PagedResult<FbrpSecRoleVO> result = null;
		if ("0".equals(parameter)) {
			result = this.roleService.queryRole(pageIndex, pageSize, role);
		} else if ("-1".equals(parameter)) {
			result = this.roleService.queryRoleById(pageIndex, pageSize, role);
		} else if ("1".equals(parameter)) {
			result = this.roleService.queryExistedRoleById(pageIndex, pageSize,
					role);
		}
		return result;
	}

	/**
	 * 批量添加角色。
	 * 
	 * @param staffId
	 *            String
	 * 
	 * @param roleId
	 *            String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "addRole")
	@ResponseBody
	public String addRole(String staffId, String roleId) {
		List<FbrpSecRoleMember> list = new ArrayList<FbrpSecRoleMember>();
		String[] roleid = roleId.split("&");
		for (int i = 0; i < roleid.length; i++) {
			FbrpSecRoleMember vo = new FbrpSecRoleMember();
			vo.setId(this.keyGen.getUUIDKey());
			vo.setStaffId(staffId);
			vo.setRoleId(roleid[i]);
			list.add(vo);
		}
		this.roleService.batchAddRoles(list);
		return "ok";
	}

	/**
	 * 批量删除角色。
	 * 
	 * @param staffId
	 *            String
	 * 
	 * @param roleId
	 *            String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "deleteRole")
	@ResponseBody
	public String deleteRole(String staffId, String roleId) {
		List<FbrpSecRoleMember> list = new ArrayList<FbrpSecRoleMember>();
		String[] roleid = roleId.split("&");
		for (int i = 0; i < roleid.length; i++) {
			FbrpSecRoleMember vo = new FbrpSecRoleMember();
			vo.setStaffId(staffId);
			vo.setRoleId(roleid[i]);
			list.add(vo);
		}
		this.roleService.batchUpdateRoles(list);
		return "ok";
	}
	
	/**
	 * 同步人员。
	 * 
	 * @param request HttpServletRequest
	 * @param vo StaffOAVO
	 * @param map ModelMap
	 * 
	 * @return String
	 */
	@RequestMapping(value = "staffManage.do", params = "synchronizeStaff")
	public String synchronizeStaff(HttpServletRequest request, StaffOAVO vo, ModelMap map) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		Map<String, String> m = new HashMap<String, String>();
		m.put("actionType", "MOV");
		m.put("sjssjgDm", this.getUser().getOrgId());
		
		//设置我的待办参数
		String sxFlag = request.getParameter("sxFlag");
		String tblx = request.getParameter("tblx");
		if(StringUtil.isStrEmpty(sxFlag)) {
			sxFlag = "1";
			tblx = "MOV";
		}
		if("1".equals(sxFlag)){ 
			m.put("sxFlag", sxFlag);
			m.put("rwjsrwDm", this.getUser().getLoginId());
			if("ADD".equals(tblx)){
				m.put("actionType", "ADD");
				m.put("task_ly", "dbrw_xtgl_dtbtjry");
			}else if("MOD".equals(tblx)){
				m.put("actionType", "MOD");
				m.put("task_ly", "dbrw_xtgl_dtbxgry");
			}else if("MOV".equals(tblx)){
				m.put("actionType", "MOV");
				m.put("task_ly", "dbrw_xtgl_dtbydry");
			}	
		}
		
		vo.setSjssjgDm(this.getUser().getOrgId());
		vo.setOrgName(this.getUser().getOrgName());	
		PagedResult<StaffOAVO> pr = this.staffService.selectUser(m, pageIndex, pageSize);
		PagedResult<StaffOAVO> pr_new = new PagedResult<StaffOAVO>(new ArrayList<StaffOAVO>(), 0, pageIndex, pageSize);
		if("MOD".equals(tblx)){
			map.put("pr2", pr);
			map.put("pr1", pr_new);
			map.put("pr3", pr_new);
		}else if("MOV".equals(tblx)){
			map.put("pr3", pr);
			map.put("pr1", pr_new);
			map.put("pr2", pr_new);
		}else{
			map.put("pr1", pr);
			map.put("pr2", pr_new);
			map.put("pr3", pr_new);
		}	
		map.put("vo", vo);
		map.put("sxFlag", sxFlag);
		map.put("tblx", tblx);
		return "security/staff/staffSynchronization";
	}
	
	/**
	 * 同步人员查询。
	 * 
	 * @param request HttpServletRequest
	 * @param code String
	 * @param name String
	 * @param actionType String
	 * @param sjssjgDm String
	 * 
	 * @return PagedResult<StaffOAVO>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "synchronizeStaffQuery")
	public PagedResult<StaffOAVO> synchronizeStaffQuery(HttpServletRequest request, 
			String code, String name, String actionType, String sjssjgDm) {
		Map<String, String> map = new HashMap<String, String>();
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		if(StringUtil.isStrEmpty(sjssjgDm)) {
			sjssjgDm = this.getUser().getOrgId();
		}
		map.put("code", code);
		map.put("name", name);
		map.put("actionType", actionType);
		map.put("sjssjgDm", sjssjgDm);
		
		//设置我的待办参数
		String sxFlag = request.getParameter("sxFlag");
		if("1".equals(sxFlag)){ 
			map.put("sxFlag", sxFlag);
			map.put("rwjsrwDm", this.getUser().getLoginId());
			if("ADD".equals(actionType)){
				map.put("task_ly", "dbrw_xtgl_dtbtjry");
			}else if("MOD".equals(actionType)){
				map.put("task_ly", "dbrw_xtgl_dtbxgry");
			}else if("MOV".equals(actionType)){
				map.put("task_ly", "dbrw_xtgl_dtbydry");
			}		
		}
		
		PagedResult<StaffOAVO> pr = this.staffService.selectUser(map, pageIndex, pageSize);
		return pr;
	}
	
	/**
	 * 人员同步检查是否在平台中。
	 * 
	 * @param accountName String
	 * @param orgCode String
	 * @param actionType String
	 * 
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "checkOwn")
	public Map<String, Object> checkOwn(String accountName, String orgCode, String actionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		if(!accountName.contains(",")) {
			StaffOAVO vo = new StaffOAVO();
			vo.setAccountName(accountName);
			vo.setOrgCode(orgCode);
			boolean flag = this.staffService.checkOwn(accountName);
			boolean flag2 = this.staffService.selectUserOrg(vo);
			if(flag && flag2) {
				map.put("own", true);
				list.add(accountName);
			} else {
				map.put("own", false);
			}
		} else {
			String[] codes = accountName.split(",");
			String[] orgCodes = orgCode.split(",");
			int i = 0;
			for(String code : codes) {
				StaffOAVO vo = new StaffOAVO();
				vo.setAccountName(code);
				vo.setOrgCode(orgCodes[i]);
				i++;
				boolean flag = this.staffService.checkOwn(code);
				boolean flag2 = this.staffService.selectUserOrg(vo);
				if(flag && flag2) {
					list.add(code);
				}
			}
			map.put("over", false);
			if(list.size() >= codes.length) {
				map.put("over", true);
			}
			map.put("list", list);
		}
		this.staffService.updateUserUum(list, actionType);
		return map;
	}
	
	/**
	 * 同步统一工作平台添加人员信息。
	 * 
	 * @param accountName String
	 * @param actionType String
	 * 
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "saveAddUser")
	public Map<String, Object> saveUser(String accountName, String actionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StaffOAVO> list = new ArrayList<StaffOAVO>();
		List<String> list1 = new ArrayList<String>();
		List<StaffOAVO> Ulist = new ArrayList<StaffOAVO>();
		String xgrDm = this.getUser().getLoginId();
		if(!accountName.contains(",")) {
			boolean flag = this.staffService.checkOwn(accountName);
			StaffOAVO vo = this.staffService.selectUser(accountName, actionType);
			if(!flag){
				if(null != vo) {
					vo.setXgrDm(xgrDm);
					list.add(vo);
				}
			} else {
				if(null != vo) {
					boolean flag1 = this.staffService.selectUserOrg(vo);
					if(!flag1) {
						vo.setXgrDm(xgrDm);
						Ulist.add(vo);
					}
				}
			}
			list1.add(accountName);
		} else {
			String[] codes = accountName.split(",");
			for(String code : codes) {
				boolean flag = this.staffService.checkOwn(code);
				StaffOAVO vo = this.staffService.selectUser(code, actionType);
				if(!flag) {
					if(null != vo) {
						vo.setXgrDm(xgrDm);
						list.add(vo);
					}
				} else {
					if(null != vo) {
						boolean flag1 = this.staffService.selectUserOrg(vo);
						if(!flag1) {
							vo.setXgrDm(xgrDm);
							Ulist.add(vo);
						}
					}
				}
				list1.add(code);
			}
		}
		this.staffService.batchAddUser(list, Ulist, list1, actionType, this.getUser().getStaffId());
		map.put("success", true);
		return map;
	}
	
	/**
	 * 同步统一工作平台修改人员信息。
	 * 
	 * @param accountName String
	 * @param actionType String
	 * 
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "updateModUser")
	public Map<String, Object> updateUser(String accountName, String actionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StaffOAVO> list = new ArrayList<StaffOAVO>();
		List<String> list1 = new ArrayList<String>();
		List<String> oList = new ArrayList<String>();
		String xgrDm = this.getUser().getLoginId();
		if(!accountName.contains(",")) {
			boolean flag = this.staffService.checkOwn(accountName);
			if(flag){
				StaffOAVO vo = this.staffService.selectUser(accountName, actionType);
				if(null != vo) {
					vo.setXgrDm(xgrDm);
					list.add(vo);
				}
				list1.add(accountName);
			} else {
				oList.add(accountName);
			}
		} else {
			String[] codes = accountName.split(",");
			for(String code : codes) {
				boolean flag = this.staffService.checkOwn(code);
				if(flag) {
					StaffOAVO vo = this.staffService.selectUser(code, actionType);
					if(null != vo) {
						vo.setXgrDm(xgrDm);
						list.add(vo);
					}
					list1.add(code);
				} else {
					oList.add(code);
				}
			}
		}
		this.staffService.batchUpdateUser(list, list1, actionType);
		map.put("success", true);
		map.put("list", oList);
		return map;
	}
	
	/**
	 * 同步统一工作平台移动人员信息。
	 * 
	 * @param accountName String
	 * @param actionType String
	 * 
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "updateMovUser")
	public Map<String, Object> updateUserMov(String accountName, String actionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StaffOAVO> list = new ArrayList<StaffOAVO>();
		List<String> list1 = new ArrayList<String>();
		List<String> oList = new ArrayList<String>();
		String xgrDm = this.getUser().getLoginId();
		if(!accountName.contains(",")) {
			boolean flag = this.staffService.checkOwn(accountName);
			if(flag){
				StaffOAVO vo = this.staffService.selectUser(accountName, actionType);
				if(null != vo) {
					vo.setXgrDm(xgrDm);
					list.add(vo);
				}
				list1.add(accountName);
			} else {
				oList.add(accountName);
			}
		} else {
			String[] codes = accountName.split(",");
			for(String code : codes) {
				boolean flag = this.staffService.checkOwn(code);
				if(flag) {
					StaffOAVO vo = this.staffService.selectUser(code, actionType);
					if(null != vo) {
						vo.setXgrDm(xgrDm);
						list.add(vo);
					}
					list1.add(code);
				} else {
					oList.add(code);
				}
			}
		}
		this.staffService.batchUpdateUserMov(list, list1, actionType);
		map.put("success", true);
		map.put("list", oList);
		return map;
	}
	
	/**
	 * 初始化机关树。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "initJGTree")
	public List<Map<String, Object>> initJGTree(HttpServletRequest request) {
		String icon_group = request.getContextPath() +"/resource/tsmp/org/images/group.gif";
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		UserProfileVO user = this.getUser();
		List<String> orgIds = this.getUser().getOrgIds();
		boolean findRoot = false;
		if("supadmin".equals(user.getLoginId()) && orgIds==null){
			orgIds = new ArrayList<String>();
			orgIds.add("24400000000");
		}else{
			if(orgIds!=null){
				for(String orgId : orgIds){
					if("244000000".equals(orgId)){
						findRoot = true;
						break;
					}
				}
			}
			if(findRoot){
				orgIds = new ArrayList<String>();
				orgIds.add("24400000000");
			}
		}
		for(String orgCode : orgIds) {
			List<GyUuvOrgVO> list = this.staffService.findAllOrg(orgCode);
			if(list != null) {
				for(GyUuvOrgVO org : list) {
					Map<String, Object> m = new HashMap<String, Object>();
					if("24400000000".equals(org.getOrgCode())) {
						m.put("id", org.getOrgCode());
						m.put("pId", null);
						m.put("name", "广东地税");
						m.put("isParent", true);
						m.put("open", "true");
						m.put("nocheck", true);
						ret.add(m);
					} else {
						boolean flag = true;
						flag = this.staffService.findChildrenOrgOwn(org.getOrgCode());
						m.put("id", org.getOrgCode());
						m.put("pId", org.getParentCode());
						m.put("name", org.getShortName());
						m.put("isParent", flag);
						if(flag && orgCode.equals(org.getOrgCode())) {
							m.put("open", "true");
						}
						m.put("orgId", org.getId());
						if("2".equals(org.getSwbmBj())){
							m.put("icon", icon_group);
						}
						ret.add(m);
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * 点击机关树事件。
	 * 
	 * @param request HttpServletRequest
	 * @param id String
	 * 
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "clickJGTree")
	public List<Map<String, Object>> clickJGTree(HttpServletRequest request, String id) {
		String icon_group = request.getContextPath() +"/resource/tsmp/org/images/group.gif";
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		List<GyUuvOrgVO> list = this.staffService.findChildrenOrg(id);
		if(list != null) {
			for(GyUuvOrgVO org : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				boolean flag = true;
				flag = this.staffService.findChildrenOrgOwn(org.getOrgCode());
				m.put("id", org.getOrgCode());
				m.put("pId", org.getParentCode());
				m.put("name", org.getShortName());
				m.put("isParent", flag);
				m.put("orgId", org.getId());
				if("2".equals(org.getSwbmBj())){
					m.put("icon", icon_group);
				}
				ret.add(m);
			}
		}
		return ret;
	}
	
	/**
	 * 查询用户权限机关。
	 * 
	 * @param accountName String 
	 * 
	 * @return List<Map<String, String>>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "selectYhqxjg")
	public List<Map<String, String>> selectYhqxjg(String accountName) {
		List<Map<String, String>> list = this.staffService.selectYhqxjg(accountName);
		return list;
	}
	
	/**
	 * 查询用户机关权限。
	 * 
	 * @param ids String
	 * @param accountName String
	 * 
	 * @return List<String>
	 */
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "selectAdminOwn")
	public List<String> selectAdminOwn(String ids, String accountName) {
		List<String> list = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		String[] idArray = ids.split(",");
		List<String> tmp = new ArrayList<String>();
		for(String id : idArray) {
			tmp.add(id);
		}
		for(String id : idArray) {
			if(this.staffService.selectAdminOwn(accountName, id)) {
				list.add(id);
				tmp.remove(id);
			}
		}
		for(String id : tmp) {
			String org = this.staffService.selectMainOrgForGlznbz(id);
			if(map.containsKey(org)) {
				list.add(map.get(org));
			}
			map.put(org, id);
		}
		return list;
	}
	
	/**
	 * 保存税务人员权限信息。
	 * 
	 * @param jsons String
	 * @param accountName String 
	 * 
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "staffManage.do", params = "saveYhqxjg")
	public Map<String, Object> saveYhqxjg(String jsons, String accountName) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();
		try {
			mlist = this.objectMapper.readValue(jsons, List.class);
		} catch (Exception e) {
			throw new FbrpException(e);
		}
		for(Map<String, String> m : mlist) {
			if((!m.containsKey("zswjgbz")) || (!"1".equals(m.get("zswjgbz")))) {
				Map<String, Object> yhqx = new HashMap<String, Object>();
				String org = this.staffService.selectMainOrgForGlznbz(m.get("orgCode"));
				yhqx.put("id", this.keyGen.getUUIDKey());
				yhqx.put("accountName", accountName);
				yhqx.put("orgCode", org);
				yhqx.put("deptCode", m.get("orgCode"));
				list.add(yhqx);
			}
		}
		this.staffService.saveYhqxjg(list, accountName);
		map.put("success", true);
		return map;
	}
	
}