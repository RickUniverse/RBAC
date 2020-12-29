package org.atcrowdfunding.controller;

import org.atcrowdfunding.bean.AjaxSuccess;
import org.atcrowdfunding.bean.Permission;
import org.atcrowdfunding.service.PermissionService;
import org.atcrowdfunding.utilities.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lijichen
 * @date 2020/12/25 - 17:55
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //employee 和 permission
    @RequestMapping("/index2")
    public String index2() {
        return "role/index2";
    }

    @RequestMapping("/assign")
    public String assign() {
        return "role/assign";
    }

    @ResponseBody
    @RequestMapping("/loadAssignData")
    public Object loadAssignData(Integer employee_id) {
        List<Permission> permissionRoot = new ArrayList<>();

        // 查询出所有的节点
        List<Permission> permissions = permissionService.queryAllPermission();
        Map<Integer, Permission> permissionMap = new HashMap<>();


        //放入Map中
        for (Permission permission : permissions) {
            permissionMap.put(permission.getId(),permission);
        }

        // 查询该角色拥有的permission
        List<Integer> permissionIds = permissionService.queryPermissionIdsByEmployeeId(employee_id);

        // 找到关系, 并建立关系
        for (Permission p : permissions) {
            // 判断该角色是否拥有这个权限, checked默认是false
            if (permissionIds.contains(p.getId())) {
                p.setChecked(true);
            }
            // 子节点
            Permission child = p;
            if (child.getPid() == 0) {
                permissionRoot.add(p);
            } else {
                Permission parent = permissionMap.get(child.getPid());
                parent.getChildren().add(child);
            }

        }


        return permissionRoot;
    }
    @ResponseBody
    @RequestMapping("/doAssign")
    public Object doAssign(Integer employeeid, Integer[] permissionids) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {
            permissionService.addEmployeePermission(MapUtil.hashMap(
                    MapUtil.kv("employeeid",employeeid),
                    MapUtil.kv("permissionids",permissionids)
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
            permissionService.deletePermission(id);
            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }

    //修改
    @ResponseBody
    @RequestMapping("/update")
    public Object update(Permission permission) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {
            permissionService.updatePermission(permission);
            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }


    @RequestMapping("/edit")
    public Object edit(Integer id) {
        ModelAndView modelAndView= new ModelAndView("permission/edit");
        modelAndView.addObject("permission",permissionService.queryPermissionById(id));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/insert")
    public Object insert(Permission permission) {
        AjaxSuccess ajaxSuccess = new AjaxSuccess();

        try {

            permissionService.insertPermission(permission);

            return ajaxSuccess.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxSuccess.setSuccess(false);
    }

    // 跳转添加节点
    @RequestMapping("/add")
    public String add() {
        return "permission/add";
    }



    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    //使用map 存储, 一定会用上索引, 效率更高
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        List<Permission> permissionRoot = new ArrayList<>();

        // 查询出所有的节点
        List<Permission> permissions = permissionService.queryAllPermission();
        Map<Integer, Permission> permissionMap = new HashMap<>();

        //放入Map中
        for (Permission permission : permissions) {
            permissionMap.put(permission.getId(),permission);
        }
        // 找到关系, 并建立关系
        for (Permission p : permissions) {
            // 子节点
            Permission child = p;
            if (child.getPid() == 0) {
                permissionRoot.add(p);
            } else {
                Permission parent = permissionMap.get(child.getPid());
                parent.getChildren().add(child);
            }

        }


        return permissionRoot;
    }
/*
    //双重循环效率较高
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        List<Permission> permissionRoot = new ArrayList<>();

        // 查询出所有的节点
        List<Permission> permissions = permissionService.queryAllPermission();
        for (Permission p : permissions) {
            // 子节点
            Permission child = p;
            // p.getPid() == 0 : 这个是root 节点,找到就直接添加父节点
            if (p.getPid() == 0) {
                permissionRoot.add(p);
            } else {
                // 这是为了找父节点 使用的for 循环, 找到就建立关系
                for (Permission innerPermission : permissions) {
                    if (child.getPid().equals(innerPermission.getId())) {
                        // 父节点
                        Permission parent = innerPermission;
                        // 结合父子节点关系
                        parent.getChildren().add(child);
                        // 因为一个子节点只能有一个父节点, 所以直接break即可
                        break;
                    }
                }
            }
        }
        return permissionRoot;
    }
*/
    //使用递归, 效率不高
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        Permission root = new Permission();
        root.setId(0);
        queryChildPermission(root);
        //返回root节点所在的集合
        return root.getChildren();
    }*/

    //递归
    public void queryChildPermission(Permission parent){
        List<Permission> permissions = permissionService.queryChildPermission(parent.getId());
        // 这里是有不明显的退出条件的, 因为当permissions 为空, 循环就不能进入了, 自然就退出了
        for (Permission permission : permissions) {
            queryChildPermission(permission);
        }
        //设置子节点
        parent.setChildren(permissions);
    }

    //普通
/*
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        Permission root = permissionService.queryRootPermission();
        List<Permission> permissionList = permissionService.queryChildPermission(root.getId());
        //父节点的子节点的子节点
        for (Permission permission : permissionList) {
            List<Permission> permissions = permissionService.queryChildPermission(permission.getId());
            permission.setChildren(permissions);
        }
        //父节点的子节点
        root.setName("root节点")
                .setChildren(permissionList);
        //返回root节点所在的集合
        return Arrays.asList(root);
    }
*/
    //测试
/*
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        Permission root = new Permission();
        root.setName("root节点")
                .setChildren(Arrays.asList(new Permission("子节点")));
        return Arrays.asList(root);
    }
*/
}
