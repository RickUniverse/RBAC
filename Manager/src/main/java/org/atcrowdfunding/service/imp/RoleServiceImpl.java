package org.atcrowdfunding.service.imp;

import org.atcrowdfunding.bean.Role;
import org.atcrowdfunding.dao.RoleDao;
import org.atcrowdfunding.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijichen
 * @date 2020/12/25 - 13:52
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> queryAll() {
        return roleDao.selectAll();
    }

    public List<Integer> queryRoleIdsByEmployeeId(Integer employeeid) {
        return roleDao.selectRoleIdsByEmployeeId(employeeid);
    }
}
