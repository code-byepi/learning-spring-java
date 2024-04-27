package com.app.springbootcrud.repositories;

import com.app.springbootcrud.entities.Role;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {

        Optional<Role> findByName(String name); // obtengo rol
}
