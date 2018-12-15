package com.graduation.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final String RETURN_TYPE = "html"; // 登录失败时，用来判断是返回json数据还是跳转html页面

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
        log.info("登录失败:" + exception.getMessage());
        log.info("username=>" + request.getParameter("username"));

        if (RETURN_TYPE.equals("html")) {
            super.setDefaultFailureUrl("/myerror.html"); // 登录失败，跳转到登录界面
            super.onAuthenticationFailure(request, response, exception);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("code","1002");
            map.put("msg","登录失败");
            map.put("data",exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(map));
        }
    }
}
