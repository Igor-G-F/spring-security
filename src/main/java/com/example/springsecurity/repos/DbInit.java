package com.example.springsecurity.repos;

import com.example.springsecurity.entity.Permission;
import com.example.springsecurity.entity.Role;
import com.example.springsecurity.entity.User;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(
            UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Permission manageP = permissionRepository.save(new Permission("MANAGE"));
        Permission administrateP = permissionRepository.save(new Permission("ADMINISTRATE"));

        Role roleAdmin = roleRepository
                .save(new Role("ROLE_ADMIN", new HashSet<>(Collections.singletonList(administrateP))));
        Role roleManager = roleRepository
                .save(new Role("ROLE_MANAGER", new HashSet<>(Collections.singletonList(manageP))));

        userRepository.save(new User("admin", passwordEncoder.encode("secure"),
                                     new HashSet<>(Arrays.asList(roleAdmin, roleManager))
        ));
        userRepository.save(new User("steve", passwordEncoder.encode("pass123"),
                                     new HashSet<>(Collections.singletonList(roleManager))
        ));
    }

}
