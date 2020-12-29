package org.atcrowdfunding.service;

import org.atcrowdfunding.bean.Role;

import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 13:51
 */
public interface RoleService {

    List<Role> queryAll();

    List<Integer> queryRoleIdsByEmployeeId(Integer employeeid);
}
