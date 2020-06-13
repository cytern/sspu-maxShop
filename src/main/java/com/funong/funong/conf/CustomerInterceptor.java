package com.funong.funong.conf;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: cytern
 * @Date: 2020/5/25 21:17
 */
@Component
public class CustomerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")){
            return true;
        }else {
            try {
                HttpSession session = request.getSession();
                String token1 = (String) session.getAttribute("User-Token");
                if (token1 != null){
                    return true;
                }else {
                    request.getRequestDispatcher("/page/account_login").forward(request,response);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.getRequestDispatcher("/page/account_login").forward(request,response);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
