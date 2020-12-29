package org.atcrowdfunding.service;

import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.bean.Permission;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 18:51
 */
public interface PermissionService {
    Permission queryRootPermission();

    List<Permission> queryChildPermission(Integer id);

    List<Permission> queryAllPermission();

    void insertPermission(Permission permission);

    Permission queryPermissionById(Integer id);

    void updatePermission(Permission permission);

    void deletePermission(Integer id);

    void addEmployeePermission(HashMap<String, Serializable> hashMap);

    List<Integer> queryPermissionIdsByEmployeeId(Integer employee_id);

    List<Permission> queryPermissionByEmployee(Employee emp);
}
