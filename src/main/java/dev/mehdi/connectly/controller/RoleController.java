package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.exception.ResourceNotFoundException;
import dev.mehdi.connectly.model.Role;
import dev.mehdi.connectly.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role not found")
        ));
    }
}
