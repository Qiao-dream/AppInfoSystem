package cn.appsys.controller.developer;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.util.Password;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/dev")
public class DevUserController {
    @Resource
    private DevUserService service;
    private Logger logger = Logger.getLogger(DevUserController.class);

    @RequestMapping(value = "/login")
    public String login(){
        logger.debug("LoggerController welcome===========");
        return "devlogin";
    }

    @RequestMapping(value = "/dologin")
    public String dologin(@RequestParam String devCode,
                          @RequestParam String devPassword,
                          HttpSession session,
                          HttpServletRequest request){
        logger.debug("dologin====================");
        DevUser user = null;
        //调用service方法，进行用户匹配
        user = service.login(devCode,devPassword);
        String a = user.getDevInfo();
        logger.info("debug a:"+a);
        if(null != user){
            if(a.equals("通过")) {
                //放入session
                session.setAttribute(Constants.DEV_USER_SESSION, user);
                //页面跳转（main.jsp）
                return "redirect:/dev/flatform/main";
            }else{
                request.setAttribute("error","您的用户暂未通过审核，请等管理员审核通过后在登陆");
                return "devlogin";
            }
        }else{
            //保留在devlogin.jsp，带出一些提示信息
            request.setAttribute("error","用户名或密码不正确");
            return "devlogin";
        }
    }

    @RequestMapping(value = "/flatform/main")
    public String main(HttpSession session){
        if(session.getAttribute(Constants.DEV_USER_SESSION)== null){
            return "redirect:/dev/login";
        }
        return "developer/main";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.removeAttribute(Constants.DEV_USER_SESSION);
        return "devlogin";
    }

    @RequestMapping(value = "/user")
    public String user(){
        return "developer/devuser";
    }
    @RequestMapping(value = "/add")
    public String devUser(DevUser devUser ) throws Exception {

        System.out.println(devUser.getDevCode());
        System.out.println(devUser.getDevName());
        System.out.println(devUser.getDevPassword());

        devUser.setCreationDate(new Date());//系统当前时间
        service.add(devUser);
        return "developer/main";
    }

    @RequestMapping(value = "/user1")
    public String user1(Model model, HttpSession session,
                        @RequestParam(value="devCode",required=false) String devCode,
                        @RequestParam(value="devName",required=false) String devName,DevUser devUser){
        System.out.println(devUser.getDevCode());
        System.out.println(devUser.getDevName());
        model.addAttribute("devCode",devCode);
        model.addAttribute("devName",devName);
        return "developer/devusermodify";
    }
    @RequestMapping(value = "/modify")
    public String modify(DevUser devUser) throws Exception {

        System.out.println(devUser.getDevCode());
        System.out.println(devUser.getDevName());
        System.out.println(devUser.getDevPassword());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        devUser.setCreationDate(new Date());//系统当前时间

        service.modify(devUser);
        return "developer/main";
    }


}
