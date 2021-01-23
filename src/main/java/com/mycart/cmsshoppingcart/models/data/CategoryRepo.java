package com.mycart.cmsshoppingcart.models.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
    
    CategoryEntity findByName(String name);

    List<CategoryEntity> findAllByOrderBySortingAsc();
}
