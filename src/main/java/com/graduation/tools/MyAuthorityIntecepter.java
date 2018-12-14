package com.graduation.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.graduation.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class MyAuthorityIntecepter implements HandlerInterceptor {

	//在业务请求之前做拦截,处理权限
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("My intercept");
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null) {
			if(user.getName()!=null) {
				System.out.println(user.toString());
				return true;
			}else {
				//用户信息不完整，完善用户信息
				response.sendRedirect(request.getContextPath()+"/html/mine.jsp");
			}
		}
		//用户没登陆，跳转到登陆页面
		System.out.println("用户没登陆");
		response.sendRedirect(request.getContextPath()+"/html/login.jsp");
		return false;
		
	}
	
	//业务请求处理之后，生成视图之前，执行的动作
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
