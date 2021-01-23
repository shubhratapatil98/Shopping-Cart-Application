package com.mycart.cmsshoppingcart.models.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
//import javax.persistence.Size;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String name;

    private String slug;

    private String description;

    private String image;

    @Pattern(regexp="^[0-9]+([.][0-9]{1,2})?", message = "Expected format: 5, 5.99, 15, 15.99")
    private String price;

    @Column(name = "category_id")
    @Pattern(regexp="^[0-9][0-9]*", message = "Please choose a category")
    private String categoryId;
    
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateddAt;

    
}
