package org.informatics.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Employee implements Serializable {
    private long id;
    private String name;
    private BigDecimal salary;
    private static long nextId = 0;

    public Employee(String name, BigDecimal salary) {
        this.id = ++nextId;
        this.name = name;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}