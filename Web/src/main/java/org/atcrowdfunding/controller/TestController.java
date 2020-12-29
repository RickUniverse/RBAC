package org.atcrowdfunding.controller;

import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/20 - 20:16
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/viewResolver")
    public String testViewResolver() {
        return "test/test";
    }

    @ResponseBody
    @RequestMapping("/json")
    public Object testJSON() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("123","sdf");
        return map;
    }

    @ResponseBody
    @RequestMapping("/service")
    public Object testService() {

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//        applicationContext.getBean(EmployeeController.class);

        List<Employee> employeeList = employeeService.queryAll();
        return employeeList;
    }


}
