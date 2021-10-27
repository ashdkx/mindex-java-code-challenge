package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
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
public class ReportingStructureServiceImplTest {
    private String reportingStructureURL;
    private Employee testEmployee;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        reportingStructureURL = "http://localhost:" + port + "/reportingStructure/{id}";
        testEmployee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @Test
    public void testReadSuccess() {
        // Reporting Structure
        ReportingStructure expectedReportingStructure = new ReportingStructure(testEmployee, 4);

        // Read checks
        ReportingStructure actualReportingStructure = restTemplate.getForEntity(reportingStructureURL, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();
        assertNotNull(actualReportingStructure);
        assertEmployeeEquivalence(actualReportingStructure.getEmployee(), expectedReportingStructure.getEmployee());
        assertEquals(actualReportingStructure.getNumberOfReports(), expectedReportingStructure.getNumberOfReports());
    }

    @Test
    public void testReadNotExist() {
        // Read checks
        ResponseEntity actualReportingStructure = restTemplate.getForEntity(reportingStructureURL, ReportingStructure.class, "1");
        assertEquals(HttpStatus.NOT_FOUND, actualReportingStructure.getStatusCode());

    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
