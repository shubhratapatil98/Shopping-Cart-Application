package com.mycart.cmsshoppingcart.models.data;

import java.util.List;

import com.mycart.cmsshoppingcart.models.data.PageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PageRepo extends JpaRepository<PageEntity, Integer> {
    

    // @Override
    //  List<PageEntity> findAll(); //if you are usig CurdRepo
    

    PageEntity findBySlug(String slug);


    
    @Query("SELECT p FROM PageEntity p WHERE p.id != :id and p.slug = :slug")
    PageEntity findBySlug(int id, String slug);

    
    PageEntity findBySlugAndIdNot(String slug, int id);

    List<PageEntity> findAllByOrderBySortingAsc();
    
}
