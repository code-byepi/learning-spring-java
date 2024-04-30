package com.app.springbootcrud.controllers;

import com.app.springbootcrud.ProductValidation;
import com.app.springbootcrud.entities.Product;
import com.app.springbootcrud.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "*") // ruta exacta
@RestController
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    ProductService productService;

    /*@Autowired
    ProductValidation productValidation;*/

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    /*@GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id).get();
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result ) {
        //productValidation.validate(product,result);
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Product productNew = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, BindingResult result,@Valid @RequestBody Product product) {
        //productValidation.validate(product,result);
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Optional<Product> productOptional = productService.update(id, product);
        if (productOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        Optional<Product> productOptional = productService.delete(product);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
     }


    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}



