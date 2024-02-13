package com.gmat.springboot.crud.springbootcrud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.gmat.springboot.crud.springbootcrud.entities.Product;
import com.gmat.springboot.crud.springbootcrud.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> productList() {
        
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdProduct(@PathVariable Long id) {
        // Optional<Product> prodOptional = productService.findById(id).orElseThrow();
        // return productService.findById(id).orElseThrow(()-> new NoSuchElementException("No se encontró el producto con el ID: " + id));
        Optional<Product> prodOptional = productService.findById(id);

        if (prodOptional.isPresent()) {
            return ResponseEntity.ok(prodOptional.get());
        }else{
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    // public ResponseEntity<Product> creaEntity(@Valid @RequestBody Product product, BindingResult result) {
    public ResponseEntity<?> creaEntity(@Valid @RequestBody Product product, BindingResult result) {
        
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        
        Product productNew = productService.save(product);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(productNew);
    }
    
    // comparte metodo "save" del service con create, pero manda id NO NULO!
    @PutMapping("/{id}")
    // public ResponseEntity<Product> updateProduct(@Valid  @RequestBody Product product,BindingResult result, @PathVariable Long id) {
    public ResponseEntity<?> updateProduct(@Valid  @RequestBody Product product,BindingResult result, @PathVariable Long id) {
        
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        
        product.setId(id);

        // productService.save(product);
        Product productUpdated = productService.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        
        Product pr = new Product();
        pr.setId(id);
        Optional<Product> prodOptional = productService.delete(pr);
        
        if (prodOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            // return ResponseEntity.ok(prodOptional.get());
        }else{
            return ResponseEntity.notFound().build();
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    // metodo helper para validation de create y update
    private ResponseEntity<?> validation(BindingResult result) {
        
        Map<String, String> errors = new HashMap<>();

        // rellenar map de errores encontrados
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });

        // return ResponseEntity.badRequest().body(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    

    


}
