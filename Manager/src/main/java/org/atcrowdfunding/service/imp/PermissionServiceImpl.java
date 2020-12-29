package org.atcrowdfunding.service.imp;

import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.bean.Permission;
import org.atcrowdfunding.dao.PermissionDao;
import org.atcrowdfunding.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 18:52
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission queryRootPermission() {
        return permissionDao.selectRootPermission();
    }

    @Override
    public List<Permission> queryChildPermission(Integer id) {
        return permissionDao.selectChildPermission(id);
    }

    @Override
    public List<Permission> queryAllPermission() {
        return permissionDao.selectAllPermission();
    }

    @Override
    public void insertPermission(Permission permission) {
        permissionDao.insertPermission(permission);
    }

    @Override
    public Permission queryPermissionById(Integer id) {
        return permissionDao.selectPermissionById(id);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }

    @Override
    public void deletePermission(Integer id) {
        permissionDao.deletePermission(id);
    }

    @Override
    public void addEmployeePermission(HashMap<String, Serializable> hashMap) {
        permissionDao.deletePermissionByEmployeeId(hashMap);
        permissionDao.insertEmployeePermission(hashMap);
    }

    @Override
    public List<Integer> queryPermissionIdsByEmployeeId(Integer employee_id) {
        return permissionDao.selectPermissionIdsByEmployeeId(employee_id);
    }

    @Override
    public List<Permission> queryPermissionByEmployee(Employee emp) {
        return permissionDao.selectPermissionByEmployee(emp);
    }
}
