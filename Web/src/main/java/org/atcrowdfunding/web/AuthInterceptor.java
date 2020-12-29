package org.atcrowdfunding.web;

import org.atcrowdfunding.bean.Permission;
import org.atcrowdfunding.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lijichen
 * @date 2020/12/26 - 15:23
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        String path = session.getServletContext().getContextPath();

        // 当前请求的URL
        String requestURI = request.getRequestURI();

        List<Permission> permissions = permissionService.queryAllPermission();
        // 所有需要验证的权限集合
        Set<String> uriSet = new HashSet<>();
        for (Permission p : permissions) {
            if (p.getUrl() != null && !"".equals(p.getUrl())) {
                uriSet.add(path + p.getUrl());
            }
        }


        // 如果判断是否是需要权限验证的url
        if (uriSet.contains(requestURI)) {
            // 如果需要
            // 获取当前用户拥有的权限, 并判断该用户是否拥有该权限
            Set<String> empUriSet = (Set<String>) session.getAttribute("uriSet");

            if (empUriSet.contains(requestURI)) {
                return true; // 有权限
            } else {
                response.sendRedirect(path + "/error");
                return false; // 没有权限
            }

        } else { // 不需要做权限验证则直接放行
            return true;
        }
    }
}
