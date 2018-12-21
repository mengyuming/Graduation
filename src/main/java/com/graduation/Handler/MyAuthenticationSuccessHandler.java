package com.graduation.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//自定义登陆成功与失败处理
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String RETURN_TYPE = "html"; // 登录成功时，用来判断是返回json数据还是跳转html页面

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //创建一个SecurityContext对象，并且设置上一步得到的Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();

        //将上一部得到的SecurityContext对象放入session中。到此，自定义用户信息的处理已经完成
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        System.out.println("--------------结束认证");
        System.out.println("将用户信息存储在session中");





        Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("登录成功");
        log.info("username=>" + request.getParameter("username"));
        if(RETURN_TYPE.equals("html")) {
            super.setDefaultTargetUrl("/pages/index.html"); // 设置默认登陆成功的跳转地址
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("code","0");
            map.put("msg","登录成功");
            map.put("data",authentication);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        }
    }

}
