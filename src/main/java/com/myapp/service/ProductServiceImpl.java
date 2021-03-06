/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.service;

import com.myapp.Utils;
import com.myapp.domain.Category;
import com.myapp.domain.Product;
import com.myapp.domain.QProduct;
import com.myapp.domain.Subcategory;
import com.myapp.domain.User;
import com.myapp.dto.PageParams;
import com.myapp.dto.ProductDTO;
import com.myapp.dto.ProductParams;
import com.myapp.repository.CategoryRepository;
import com.myapp.repository.ProductCustomRepository;
import com.myapp.repository.ProductRepository;
import com.myapp.repository.RelationshipRepository;
import com.myapp.repository.SubcategoryRepository;
import com.myapp.repository.UserRepository;
import com.myapp.service.exceptions.NotPermittedException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kamil
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SecurityContextService securityContextService;
    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCustomRepository productCustomRepository;


  

  
    @Autowired
     public ProductServiceImpl(ProductRepository productRepository, SecurityContextService securityContextService, UserRepository userRepository, RelationshipRepository relationshipRepository, SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository, ProductCustomRepository productCustomRepository) {
        this.productRepository = productRepository;
        this.securityContextService = securityContextService;
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.productCustomRepository = productCustomRepository;
    }

      @Override
    public void delete(Long id) throws NotPermittedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    
    @Override
    public List<Product> findByUser(Long userId, PageParams pageParams) {
        User user = userRepository.findOne(userId);
        return productRepository.findByUser(user).stream().collect(Collectors.toList());

    }

    @Override
    public List<Product> findMyProduct(PageParams pageParams) {
        return securityContextService.currentUser().
                map(u -> findByUser(u.getId(), pageParams))
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Product saveMyProduct(ProductParams productParams) {
        Product product = new Product();

        return securityContextService.currentUser()
                .map(u -> {
                    product.setUser(u);
                    product.setName(productParams.getName());
                    product.setDescription(productParams.getDescription());
                    product.setPieces(productParams.getPieces());
                    product.setPrice(productParams.getPrice());
                    product.setBarcode(productParams.getBarcode());
                    product.setSubcategory(subcategoryRepository.findOne(productParams.getIdSubcategory()));
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new AccessDeniedException(""));

    }

    @Override
    public Page<Product> findAll(PageRequest pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateMyProduct(Product product) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public List<Product> findBySubcategory(Long subCategoryId, PageParams pageParams) {
        Subcategory subcategory=subcategoryRepository.findOne(subCategoryId);
        return productCustomRepository.findBySubcategory(subcategory,pageParams);
    }

    @Override
    public List<Product> findByCategory(Long categoryId, PageParams pageParams) {
       Category category=categoryRepository.findOne(categoryId);
        return productCustomRepository.findByCategory(category,pageParams);
    }

}
