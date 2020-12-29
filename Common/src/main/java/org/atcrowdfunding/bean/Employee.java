package org.atcrowdfunding.bean;

/**
 * @author lijichen
 * @date 2020/12/20 - 20:53
 */
public class Employee {
    private Integer id;
    private String lastName;
    private Integer gender;
    private String email;
    private String createtime;

    public Integer getId() {
        return id;
    }

    public Employee setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public Employee setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCreatetime() {
        return createtime;
    }

    public Employee setCreatetime(String createtime) {
        this.createtime = createtime;
        return this;
    }


    public Employee() {
    }

    public Employee(Integer id, String lastName, Integer gender, String email, String createtime) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
