package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;

import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.Collections;

@RestController
@RequestMapping(value = {"/compensation"})
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping
    public ResponseEntity create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);
        try {
            Compensation createdCompensation = compensationService.create(compensation);
            return new ResponseEntity<>(createdCompensation, HttpStatus.OK);
        } catch (Exception e) {
            // rare case of errors happening in database
            LOG.debug("Fatal error in creating entry for in database");
            return new ResponseEntity(Collections.singletonMap("Error:", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);
        Compensation compensation = null;
        try {
            compensation = compensationService.read(id);
            if (compensation != null) {
                return new ResponseEntity<>(compensation, HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Collections.singletonMap("Error:", "User not found"), HttpStatus.NOT_FOUND);
        }
        // this should never happen as try/catch will handle the errors already
        LOG.debug("Fatal error in reading from the database");
        return new ResponseEntity(Collections.singletonMap("Error:", "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
