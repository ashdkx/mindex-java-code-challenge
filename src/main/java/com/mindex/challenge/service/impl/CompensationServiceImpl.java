package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        Employee employee = employeeService.read(compensation.getEmployee().getEmployeeId());
        compensation.setEmployee(employee);
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Reading compensation with employee [{}]", employeeId);
        Compensation compensation = compensationRepository.findByEmployee(employeeService.read(employeeId));
        if (compensation == null) {
            LOG.debug("No compensation record for employeeId: [{}]", employeeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compensation record for employeeId: " + employeeId);
        }
        return compensation;
    }
}
