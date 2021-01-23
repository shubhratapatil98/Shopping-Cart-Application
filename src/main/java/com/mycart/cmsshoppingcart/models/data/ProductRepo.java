package com.mycart.cmsshoppingcart.models.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductEntity, Integer> {

	ProductEntity findBySlug(String slug);


    
}
