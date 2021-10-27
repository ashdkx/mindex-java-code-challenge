package com.mindex.challenge.service.impl;


import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationURL;
    private String compensationIdURL;
    private Employee testEmployee;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        compensationURL = "http://localhost:" + port + "/compensation";
        compensationIdURL = "http://localhost:" + port + "/compensation/{id}";
        testEmployee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @Test
    public void testCreateRead() {
        Compensation expectedCompensation = new Compensation();
        expectedCompensation.setEmployee(testEmployee);
        expectedCompensation.setSalary(10000);
        expectedCompensation.setEffectiveDate("10/27/2021");

        // Create checks
        Compensation createCompensation = restTemplate.postForEntity(compensationURL, expectedCompensation, Compensation.class).getBody();

        assertNotNull(createCompensation);
        assertEmployeeEquivalence(expectedCompensation.getEmployee(), createCompensation.getEmployee());
        assertEquals(expectedCompensation.getSalary(), createCompensation.getSalary());
        assertEquals(expectedCompensation.getEffectiveDate(), createCompensation.getEffectiveDate());

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdURL, Compensation.class, testEmployee.getEmployeeId()).getBody();
        assertNotNull(createCompensation);
        assertEmployeeEquivalence(expectedCompensation.getEmployee(), readCompensation.getEmployee());
        assertEquals(expectedCompensation.getSalary(), readCompensation.getSalary());
        assertEquals(expectedCompensation.getEffectiveDate(), readCompensation.getEffectiveDate());
    }

    @Test
    public void testReadNotExist() {
        // Read checks
        ResponseEntity readCompensation = restTemplate.getForEntity(compensationIdURL, Compensation.class, "1");
        assertEquals(HttpStatus.NOT_FOUND, readCompensation.getStatusCode());

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
