package com.mycart.cmsshoppingcart.models;

import java.util.List;

import com.mycart.cmsshoppingcart.models.data.PageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface PageRepo_old extends JpaRepository<PageEntity, Integer> {
    

    // @Override
    //  List<PageEntity> findAll(); //if you are usig CurdRepo
    
}
