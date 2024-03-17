package com.backendserver.DigitronixProject.controllers;


import com.backendserver.DigitronixProject.dtos.DataAccessDTO;
import com.backendserver.DigitronixProject.models.DataAccess;
import com.backendserver.DigitronixProject.services.DataAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/data_accesses")
@RequiredArgsConstructor
public class DataAccessController {

    private final DataAccessService dataAccessService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<DataAccess> createDataAccess(@RequestBody DataAccessDTO dataAccessDTO) {
        DataAccess newDataAccess = dataAccessService.createDataAccess(dataAccessDTO);
        return new ResponseEntity<>(newDataAccess, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<DataAccess> getDataAccessById(@PathVariable("id") Long id) {
        DataAccess dataAccess = dataAccessService.getById(id);
        return new ResponseEntity<>(dataAccess, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<List<DataAccess>> getAllDataAccesses() {
        List<DataAccess> dataAccesses = dataAccessService.getAll();
        return new ResponseEntity<>(dataAccesses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<String> deleteAllDataAccesses() {
        String message = dataAccessService.deleteAll();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
