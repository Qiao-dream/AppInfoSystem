package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import cn.appsys.tools.Constants;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value="/manager")
public class UserLoginController {
	private Logger logger = Logger.getLogger(UserLoginController.class);
	
	@Resource
	private BackendUserService backendUserService;
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/login")
	public String login(){
		logger.debug("LoginController welcome AppInfoSystem backend==================");
		return "backendlogin";
	}
	
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,@RequestParam String userPassword,HttpServletRequest request,HttpSession session){
		logger.debug("doLogin====================================");
		//调用service方法，进行用户匹配
		BackendUser user = null;
		try {
			user = backendUserService.login(userCode,userPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != user){//登录成功
			//放入session
			session.setAttribute(Constants.USER_SESSION, user);
			//页面跳转（main.jsp）
			return "redirect:/manager/backend/main";
		}else{
			//页面跳转（login.jsp）带出提示信息--转发
			request.setAttribute("error", "用户名或密码不正确");
			return "backendlogin";
		}
	}
	
	@RequestMapping(value="/backend/main")
	public String main(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "redirect:/manager/login";
		}
		return "backend/main";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		//清除session
		session.removeAttribute(Constants.USER_SESSION);
		return "backendlogin";
	}


	@RequestMapping(value = "/userlist")
	public String getDevUserList(Model model, HttpSession session,
								 @RequestParam(value="devCode",required=false) String devCode,
								 @RequestParam(value="devName",required=false) String devName,
								 @RequestParam(value="devInfo",required=false) String devInfo,
								 @RequestParam(value="creationDate",required=false) Date creationDate,
								 @RequestParam(value="pageIndex",required=false) String pageIndex)throws Exception{

		List<DevUser> devUserList = null;

		//页面容量
		int pageSize = Constants.pageSize;
		//当前页码
		Integer currentPageNo = 1;
		if(pageIndex != null){
			try{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		//总数量（表）
		int totalCount = 0;
		try {
			totalCount = devUserService.getDevUserCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		//控制首页和尾页
		if(currentPageNo < 1){
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;
		}
		try {
			devUserList = devUserService.getDevUserList(devCode,devName,devInfo,creationDate,currentPageNo, pageSize);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("devUserList",devUserList);
		model.addAttribute("pages", pages);
		model.addAttribute("devCode",devCode);
		model.addAttribute("devName",devName);
		model.addAttribute("devInfo",devInfo);
		model.addAttribute("creationDate",creationDate);

		return "backend/usercheck";
	}

	@RequestMapping(value = "/update.json")
	@ResponseBody
	public Object update(@RequestParam("devCode") String devCode) throws Exception {
		HashMap<String, String> result = new HashMap<String, String>();
		if(devCode != null && !"".equals(devCode)){
			Boolean bool = devUserService.update(devCode);
			if(bool){
				result.put("Result", "true"); //成功通过申请
			}else {
				result.put("Result", "false");//操作失败
			}
		}else {
			result.put("Result", "notexist"); //没有该用户
		}

		return result;
	}

	@RequestMapping(value = "/userlist1")
	public String getDevUserList1(Model model, HttpSession session,
								 @RequestParam(value="devCode",required=false) String devCode,
								 @RequestParam(value="devName",required=false) String devName,
								 @RequestParam(value="devInfo",required=false) String devInfo,
								 @RequestParam(value="creationDate",required=false) Date creationDate,
								 @RequestParam(value="pageIndex",required=false) String pageIndex)throws Exception{

		List<DevUser> devUserList = null;


		//页面容量
		int pageSize = Constants.pageSize;
		//当前页码
		Integer currentPageNo = 1;

		if(pageIndex != null){
			try{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		//总数量（表）
		int totalCount = 0;
		try {
			totalCount = devUserService.getDevUserCount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		//控制首页和尾页
		if(currentPageNo < 1){
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;
		}
		try {
			devUserList = devUserService.getDevUserList(devCode,devName,devInfo,creationDate,currentPageNo, pageSize);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("devUserList",devUserList);
		model.addAttribute("pages", pages);
		model.addAttribute("devCode",devCode);
		model.addAttribute("devName",devName);
		model.addAttribute("devInfo",devInfo);
		model.addAttribute("creationDate",creationDate);

		return "backend/usermanager";
	}

	@RequestMapping(value = "/delete.json")
	@ResponseBody
	public Object delete(@RequestParam("devCode") String devCode) throws Exception {
		HashMap<String, String> result = new HashMap<String, String>();
		if(devCode != null && !"".equals(devCode)){
			Boolean bool = devUserService.delete(devCode);
			if(bool){
				result.put("Result", "true"); //成功通过申请
			}else {
				result.put("Result", "false");//操作失败
			}
		}else {
			result.put("Result", "notexist"); //没有该用户
		}

		return result;
	}
}
