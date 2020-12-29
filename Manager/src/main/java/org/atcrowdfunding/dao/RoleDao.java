package org.atcrowdfunding.dao;

import org.apache.ibatis.annotations.Select;
import org.atcrowdfunding.bean.Role;

import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 13:53
 */
public interface RoleDao {
    List<Role> selectAll();

    @Select("select rid from tb_employee_role where eid = #{employeeid}")
    List<Integer> selectRoleIdsByEmployeeId(Integer employeeid);
}
