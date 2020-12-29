package org.atcrowdfunding.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.bean.Permission;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 18:53
 */
public interface PermissionDao {

    @Select("select * from t_permission where pid is null")
    Permission selectRootPermission();

    @Select("select * from t_permission where pid = #{rootId}")
    List<Permission> selectChildPermission(Integer rootId);

    @Select("select * from t_permission")
    List<Permission> selectAllPermission();

    void insertPermission(Permission permission);

    @Select("select * from t_permission where id = #{id}")
    Permission selectPermissionById(Integer id);

    void updatePermission(Permission permission);

    @Delete("delete from t_permission where id = #{id}")
    void deletePermission(Integer id);

    @Delete("delete from t_employee_permission where employee_id = #{employeeid}")
    void deletePermissionByEmployeeId(HashMap<String, Serializable> hashMap);

    void insertEmployeePermission(HashMap<String, Serializable> hashMap);

    @Select("select permission_id from t_employee_permission where employee_id = #{employee_id}")
    List<Integer> selectPermissionIdsByEmployeeId(Integer employee_id);

    List<Permission> selectPermissionByEmployee(Employee emp);
}
