/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.repository;

import com.myapp.domain.Product;
import com.myapp.domain.Subcategory;
import com.myapp.dto.PageParams;
import com.myapp.dto.ProductDTO;
import java.util.List;

/**
 *
 * @author Kamil
 */
public interface ProductCustomRepository {
      List<Product> findBySubcategory(Subcategory subcategory, PageParams pageParams);
}