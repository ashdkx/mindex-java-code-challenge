package com.mindex.challenge.data;


import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Compensation {

    @DBRef
    Employee employee;
    int salary;
    // assuming the input format is a string (ex: "10/22/2021", month/day/year) -> convert to long and save in Date
    LocalDate effectiveDate;

    public Compensation(Employee employee, int salary, String effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = LocalDate.parse(effectiveDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = LocalDate.parse(effectiveDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }
}
