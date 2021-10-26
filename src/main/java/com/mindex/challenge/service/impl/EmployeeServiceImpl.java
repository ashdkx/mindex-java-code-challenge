package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            LOG.debug("Employee not found [{}]", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public int getListOfReports(String id) {
        Employee employee = read(id);
        int totalDirectReports = 0;
        List<Employee> employees = employee.getDirectReports();
        if (employees != null) {
            for (Employee employee1 : employees) {
                totalDirectReports++;
                totalDirectReports += getListOfReports(employee1.getEmployeeId());
            }
        }
        return totalDirectReports;
    }
}
