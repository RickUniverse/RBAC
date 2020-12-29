package org.atcrowdfunding.service.imp;

import org.atcrowdfunding.bean.Employee;
import org.atcrowdfunding.dao.EmployeeDao;
import org.atcrowdfunding.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijichen
 * @date 2020/12/20 - 20:49
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;


    /*@Transactional*/
    public List<Employee> queryAll() {
        return employeeDao.selectAll();
    }

    public Employee query4Login(Employee emp) {
        return employeeDao.selectEmployeeByEmp(emp);
    }

    public List<Employee> queryByPage(Map<String, Object> map) {
        return employeeDao.selectByLimit(map);
    }

    public Integer queryCountByPage(Map<String, String> map) {
        return employeeDao.selectCountByEmployee(map);
    }

    public void addEmployee(Employee employee) {
        employeeDao.insert(employee);
    }

    public Employee getEmployeeById(Integer id) {
        return employeeDao.selectOneById(id);
    }

    public void updateEmployeeByEmployee(Employee employee) {
        employeeDao.updateEmployeeByEmployee(employee);
    }

    public void deleteEmployeeById(Integer id) {
        employeeDao.deleteEmployeeById(id);
    }

    public void deleteEmployees(HashMap<String, Object[]> employeeIds) {
        employeeDao.deleteEmployees(employeeIds);
    }

    public void addEmployeeRole(Integer eId, HashMap<String, Integer[]> roleids) {
        employeeDao.insertEmployeeRole(eId, roleids);
    }

    public void removeEmployeeRole(Integer eId, HashMap<String, Integer[]> roleids) {
        employeeDao.deleteEmployeeRole(eId, roleids);
    }
}
