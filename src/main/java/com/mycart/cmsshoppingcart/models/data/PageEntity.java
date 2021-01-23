package com.mycart.cmsshoppingcart.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.Size;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageEntity {

    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Size(min=2, message="Title must be atleast 2 characters long")
    private String title;
    
    private String slug;

    @Size(min=2, message="Content must be atleast 2 characters long")
    private String content;
    
    private int sorting;

    
}
