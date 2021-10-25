package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.Collections;

@RestController
@RequestMapping(value = {"/reportingStructure"})
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable String id) {
        LOG.debug("Received reporting structure read request for id [{}]", id);
        ReportingStructure reportingStructure = null;
        try {
            reportingStructure = reportingStructureService.read(id);
            if (reportingStructure != null) {
                return new ResponseEntity<>(reportingStructure, HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Collections.singletonMap("Error:", "User not found"), HttpStatus.NOT_FOUND);
        }
        // this should never happen as the errors should be handle in the try/catch already
        LOG.debug("Fatal error in reading from database");
        return new ResponseEntity(Collections.singletonMap("Error:", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
