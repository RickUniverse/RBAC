package org.atcrowdfunding.service;

import org.atcrowdfunding.bean.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijichen
 * @date 2020/12/20 - 20:48
 */
public interface EmployeeService {
    List<Employee> queryAll();

    Employee query4Login(Employee emp);

    List<Employee> queryByPage(Map<String, Object> map);

    Integer queryCountByPage(Map<String, String> map);

    void addEmployee(Employee employee);

    Employee getEmployeeById(Integer id);

    void updateEmployeeByEmployee(Employee employee);

    void deleteEmployeeById(Integer id);

    void deleteEmployees(HashMap<String, Object[]> employeeIds);

    void addEmployeeRole(Integer userId, HashMap<String, Integer[]> roleids);

    void removeEmployeeRole(Integer userId, HashMap<String, Integer[]> roleids);
}
