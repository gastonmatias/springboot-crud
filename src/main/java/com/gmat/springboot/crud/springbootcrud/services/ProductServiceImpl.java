//? nombre alternativo podria ser ProductServiceJPA
package com.gmat.springboot.crud.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmat.springboot.crud.springbootcrud.entities.Product;
import com.gmat.springboot.crud.springbootcrud.repositories.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    //? --LISTAR ----------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll(); // retorna Iterable, por eso parsear a List<Product>
    }

    //? -- BUSCAR x ID ----------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
        // return productRepository.sa(id);
    }

    //? -- GUARDAR ----------------------------------------------------------
    //? NOTA: sirve para "create" & "update", dependerá de si el id enviado es null o no
    @Override
    @Transactional
    @SuppressWarnings("null")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    @SuppressWarnings("null")
    public Optional<Product> delete(Product product) {
        Optional<Product> productoptionalToDelete = productRepository.findById(product.getId());
        
        // productoptionalToDelete.ifPresent(prod -> productRepository.delete(prod));
        productoptionalToDelete.ifPresent(productRepository::delete);

        return productoptionalToDelete;
    }
}
