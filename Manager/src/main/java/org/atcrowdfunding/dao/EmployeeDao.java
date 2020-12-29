package org.atcrowdfunding.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.atcrowdfunding.bean.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EmployeeDao {

    @Select("select * from tb_employee")
    List<Employee> selectAll();

    @Select(("select * from tb_employee where last_name = #{lastName} and email = #{email} limit 1"))
    Employee selectEmployeeByEmp(Employee emp);

    List<Employee> selectByLimit(Map<String, Object> map);

    Integer selectCountByEmployee(Map<String, String> map);

    void insert(Employee employee);

    Employee selectOneById(Integer id);

    void updateEmployeeByEmployee(Employee employee);

    void deleteEmployeeById(Integer id);

    void deleteEmployees(HashMap<String, Object[]> employeeIds);

    void insertEmployeeRole(@Param("eid") Integer eId, @Param("roleids") HashMap<String, Integer[]> roleids);

    void deleteEmployeeRole(@Param("eid") Integer eId, @Param("roleids") HashMap<String, Integer[]> roleids);
}
