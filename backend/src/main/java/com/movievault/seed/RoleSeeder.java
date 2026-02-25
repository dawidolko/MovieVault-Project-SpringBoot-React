package com.movievault.seed;

import com.movievault.model.Role;
import com.movievault.model.RoleName;
import com.movievault.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void seed() {
        for (RoleName roleName : RoleName.values()) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(new Role(roleName));
                System.out.println("Seeded role: " + roleName);
            }
        }
    }
}
