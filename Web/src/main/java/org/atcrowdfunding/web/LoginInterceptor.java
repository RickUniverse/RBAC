package org.atcrowdfunding.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lijichen
 * @date 2020/12/26 - 14:29
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在控制器方法执行前执行该方法
     * @param request
     * @param response
     * @param handler
     * @return
     *      false : 表示不继续向下执行
     *      true : 继续向下执行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginEmployee = session.getAttribute("loginEmployee");
        if (loginEmployee == null) {
            response.sendRedirect(session.getServletContext().getContextPath() + "/login");
            return false;
        }

        return true;
    }

    //在控制器方法执行后执行该方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle..");
    }

    //视图解析完成之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion ..");
    }
}
