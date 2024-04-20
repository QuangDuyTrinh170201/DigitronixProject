package com.backendserver.DigitronixProject.controllers;

import com.backendserver.DigitronixProject.models.Role;
import com.backendserver.DigitronixProject.services.Role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("${api.prefix}/roles")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
