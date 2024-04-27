package com.app.springbootcrud.repositories;

import com.app.springbootcrud.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
