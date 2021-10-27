package com.mindex.challenge.data;


import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Compensation {

    @DBRef
    Employee employee;
    // salary unit is assumed to be USD
    int salary;
    // assuming the input format is a string (ex: "10/22/2021", month/day/year) -> convert Date
    // string here is a rather bad but safe option as the input is not specified
    String effectiveDate;

    //    public Compensation(Employee employee, int salary, String effectiveDate) {
//        this.employee = employee;
//        this.salary = salary;
//        LocalDate localDate = LocalDate.parse(effectiveDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//        this.effectiveDate = Date.from(Instant.ofEpochSecond(localDate.toEpochDay()));
//    }
    public Compensation() {
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

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
//        LocalDate localDate = LocalDate.parse(effectiveDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//        this.effectiveDate = Date.from(Instant.ofEpochSecond(localDate.toEpochDay()));
        this.effectiveDate = effectiveDate;
    }
}
