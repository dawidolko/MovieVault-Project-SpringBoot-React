package com.movievault.seed;

import com.movievault.model.RoleName;
import com.movievault.model.User;
import com.movievault.repository.RoleRepository;
import com.movievault.repository.UserRepository;
import com.movievault.security.PasswordHasher;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordHasher passwordHasher;

    public UserSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordHasher = passwordHasher;
    }

    public void seed() {
        createUser("Dawid", "Olko", "admin@movievault.com", "admin123", RoleName.ADMIN);
        createUser("Roger", "Ebert", "roger@movievault.com", "critic123", RoleName.CRITIC);
        createUser("Mark", "Kermode", "mark@movievault.com", "critic123", RoleName.CRITIC);
        createUser("Jan", "Kowalski", "jan@movievault.com", "user123", RoleName.USER);
        createUser("Anna", "Nowak", "anna@movievault.com", "user123", RoleName.USER);
        createUser("Piotr", "Wisniewski", "piotr@movievault.com", "user123", RoleName.USER);
        createUser("Maria", "Wojcik", "maria@movievault.com", "user123", RoleName.USER);
        createUser("Tomasz", "Kaminski", "tomasz@movievault.com", "user123", RoleName.USER);
    }

    private void createUser(String firstName, String lastName, String email, String password, RoleName roleName) {
        if (userRepository.findByEmail(email).isPresent()) return;

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordHasher.hashPassword(password));
        user.setRole(roleRepository.findByName(roleName).orElseThrow());
        userRepository.save(user);
        System.out.println("Seeded user: " + email);
    }
}
