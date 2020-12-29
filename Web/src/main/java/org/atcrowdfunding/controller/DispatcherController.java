package org.atcrowdfunding.controller;

import org.atcrowdfunding.bean.AjaxSuccess;
import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.bean.Permission;
import org.atcrowdfunding.service.EmployeeService;
import org.atcrowdfunding.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author lijichen
 * @date 2020/12/20 - 21:10
 */
@Controller
public class DispatcherController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PermissionService permissionService;

    //登录
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    //错误页面
    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    //跳转到主页
    @RequestMapping("/main")
    public String main() {
        return "main";
    }


    //跳转到菜单
    @RequestMapping("/menu")
    public String menu() {
        return "common/menu";
    }

    //登出
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:login";
    }

    //登录验证ajax
    @ResponseBody
    @RequestMapping("/doAJAXLogin")
    public Object doAjayLogin(Employee employee, HttpSession httpSession) {

        Employee emp = employeeService.query4Login(employee);
        if (emp != null) {

            Permission rootPermission = null;

            List<Permission> permissionList = permissionService.queryPermissionByEmployee(emp);

            Map<Integer, Permission> permissionMap = new HashMap<>();
            for (Permission permission : permissionList) {
                permissionMap.put(permission.getId(), permission);
            }

            for (Permission permission : permissionList) {
                Permission child = permission;
                if (permission.getPid() == 0) {
                    rootPermission = permission;
                } else {
                    Permission parent = permissionMap.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }

            httpSession.setAttribute("loginEmployee", emp);
            httpSession.setAttribute("rootPermission", rootPermission);

            // 所有的permission
            // 保存该员工拥有的所有权限集合
            String path = httpSession.getServletContext().getContextPath();
            Set<String> uriSet = new HashSet<>();
            for (Permission p : permissionList) {
                if (p.getUrl() != null && !"".equals(p.getUrl())) {
                    //因为做权限时, 当前请求的url中包含web 路径所以需要加上 : path
                    uriSet.add(path + p.getUrl());
                }
            }
            httpSession.setAttribute("uriSet", uriSet);
            return new AjaxSuccess(true);
        }
        return new AjaxSuccess(false);
    }

    //第一版, 登录
    @RequestMapping("/doLogin")
    public Object doLogin(Employee employee) throws UnsupportedEncodingException {

        //如果有乱码问题, 使用过滤器解决最好
//        byte[] bytes = employee.getLastName().getBytes("ISO8859-1");
//        String lastName = new String(bytes, "UTF-8");
//        System.out.println(lastName);
        Permission rootPermission = null;

        Employee emp = employeeService.query4Login(employee);
        if (emp != null) {

            List<Permission> permissionList = permissionService.queryPermissionByEmployee(emp);
            Map<Integer, Permission> permissionMap = new HashMap<>();
            for (Permission permission : permissionList) {
                permissionMap.put(permission.getId(), permission);
            }

            for (Permission permission : permissionList) {
                Permission child = permission;
                if (permission.getPid() == 0) {
                    rootPermission = permission;
                } else {
                    Permission parent = permissionMap.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }

            return new ModelAndView("menu2").
                    addObject("rootPermission", rootPermission);
        }else {

            ModelAndView modelAndView = new ModelAndView("redirect:login");
            modelAndView.addObject("errorMsg","没有找到该员工!");
            return modelAndView;//重定向到login控制器方法上
        }

    }
}
