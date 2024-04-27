package com.app.springbootcrud.repositories;

import com.app.springbootcrud.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {


}
