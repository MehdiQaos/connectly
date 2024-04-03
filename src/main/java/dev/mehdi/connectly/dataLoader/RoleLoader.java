package dev.mehdi.connectly.dataLoader;

import dev.mehdi.connectly.config.DataProperties;
import dev.mehdi.connectly.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class RoleLoader implements CommandLineRunner {
    private final RoleService roleService;
    private final DataProperties dataProperties;

    @Override
    public void run(String... args) {
        if (!dataProperties.getInit())
            return;
        roleService.createRoles();
    }
}
