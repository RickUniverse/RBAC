package org.atcrowdfunding.controller;

import org.atcrowdfunding.bean.AjaxSuccess;
import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.bean.Page;
import org.atcrowdfunding.bean.Role;
import org.atcrowdfunding.service.EmployeeService;
import org.atcrowdfunding.service.RoleService;
import org.atcrowdfunding.utilities.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author lijichen
 * @date 2020/12/22 - 21:14
 */
@Controller
//将键为 : roles, 的值放入到session域中, 同时也会放入request域中
@SessionAttributes(value = {"roles","unassignRoles","assignRoles"}, types = {Role.class})
@RequestMapping("/user")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RoleService roleService;

    //分配角色

    /**
     *
     * @param userId : 需要分配的用户id
     * @param unassignroleids : 选中的未分配的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/doAssign")
    public Object doAssign(@RequestParam("userId") Integer userId, Integer[] unassignroleids) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {

            employeeService.addEmployeeRole(userId,
                    MapUtil.hashMap(
                            MapUtil.kv("roleids", unassignroleids)
                    ));

            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }

    /**
     *
     * @param userId : 需要分配的用户id
     * @param assignroleids : 已经分配的角色, 待删除的角色id
     * @return
     */
    @ResponseBody
    @RequestMapping("/dounAssign")
    public Object dounAssign(Integer userId, Integer[] assignroleids) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {

            employeeService.removeEmployeeRole(userId,
                    MapUtil.hashMap(
                            MapUtil.kv("roleids", assignroleids)
                    ));

            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }

    //跳转到赋权页面
    @RequestMapping("/assign")
    public String assign(Integer employeeid, Map<String, Object> map) {
        List<Role> roleList = roleService.queryAll();
        final List<Integer> roleIds = roleService.queryRoleIdsByEmployeeId(employeeid);

        //交集
        List<Role> assignRoles = roleList.stream().filter(new Predicate<Role>() {
            public boolean test(Role role) {
                for (Integer role1 : roleIds) {
                    if (role1 == role.getId()) {
                        return true;
                    }
                }
                return false;
            }
        }).collect(Collectors.toList());
        //差集
        List<Role> unassignRoles = roleList.stream().filter(new Predicate<Role>() {
            public boolean test(Role role) {
                for (Integer role1 : roleIds) {
                    if (role1 == role.getId()) {
                        return false;
                    }
                }
                return true;
            }
        }).collect(Collectors.toList());
        map.put("unassignRoles",unassignRoles);
        map.put("assignRoles",assignRoles);
        return "user/assign";
    }


    //批量删除
    @ResponseBody
    @RequestMapping("/deletes")
    public Object deletes(Integer[] employeeIds) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {
            employeeService.deleteEmployees(MapUtil.hashMap(
                    MapUtil.<String, Object[]>kv("employeeIds", employeeIds)
            ));
            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }
    //删除
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {
            employeeService.deleteEmployeeById(id);
            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }

    //修改
    @RequestMapping("/edit")
    public Object edit(Integer id) {
        ModelAndView modelAndView = new ModelAndView("user/edit")
                .addObject("employee", employeeService.getEmployeeById(id));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/update")
    public Object update(Employee employee) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {
            employeeService.updateEmployeeByEmployee(employee);
            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }



    //新增员工
    @RequestMapping("add")
    public String add() {
        return "user/add";
    }

    @ResponseBody
    @RequestMapping("addEmp")
    public Object addEmp(Employee employee) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            employee.setCreatetime(sdf.format(new Date()));
            employeeService.addEmployee(employee);
            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }





    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "4") Integer pageSize) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", (pageNo - 1) * pageSize);
        map.put("size", pageSize);

        List<Employee> employeeList = employeeService.queryByPage(map);
        Integer count = employeeService.queryCountByPage(new HashMap<String, String>());

        //计算总页数
        Integer totalPage = (count + pageSize - 1) / pageSize;

        ModelAndView modelAndView = new ModelAndView("user/index");

        HashMap<String, Object> employeeMap = new HashMap<String, Object>();
        employeeMap.put("employees",employeeList);
        employeeMap.put("totalPage",totalPage);
        employeeMap.put("pageNo",pageNo);

        modelAndView.addObject("employeeMap", employeeMap);
        return modelAndView;
    }


    //跳转页面
    @RequestMapping("/index2")
    public String index2() {
        return "user/index2";
    }
    //使用Ajax分页
    @ResponseBody
    @RequestMapping("/ajaxPage")
    public Object AjaxPage(Integer pageNo, Integer pageSize, String lastName) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();
        Page<Employee> page = new Page<Employee>();

        try {
            //需要先设置总页数
            page.setTotalCount(employeeService.queryCountByPage(MapUtil.hashMap(
                    MapUtil.kv("lastName", lastName)
            ))).setPageSize(pageSize)
                    .setPageNo(pageNo)
                    .setDatas(employeeService.queryByPage(
                            MapUtil.hashMap(
                                    MapUtil.<String, Object>kv("start", (pageNo - 1) * pageSize),
                                    MapUtil.<String, Object>kv("size", pageSize),
                                    MapUtil.<String, Object>kv("lastName", lastName)
                            )
                    ));

            return ajaxSuccess.setSuccess(true)
                    .setData(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ajaxSuccess.setSuccess(false);
        }
    }
}
