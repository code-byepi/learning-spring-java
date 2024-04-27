package com.app.springbootcrud.services;

import com.app.springbootcrud.entities.Role;
import com.app.springbootcrud.entities.User;
import com.app.springbootcrud.repositories.RoleRepository;
import com.app.springbootcrud.repositories.UserRepository;
import com.app.springbootcrud.security.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;  // busco rol por el nombre
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        /*Codificamos el password y asignarle al usuario el rol*/


        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) { // con el flag
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles); //le paso los roles al usuario

        // codigo el password de user
        /*String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);*/

        user.setPassword(passwordEncoder.encode(user.getPassword())); // simplifico

        return userRepository.save(user); // guardo  usuario
    }

}
