package com.mycart.cmsshoppingcart.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import com.mycart.cmsshoppingcart.models.data.ProductRepo;
import com.mycart.cmsshoppingcart.models.data.CategoryEntity;
import com.mycart.cmsshoppingcart.models.data.CategoryRepo;
import com.mycart.cmsshoppingcart.models.data.ProductEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping
    public String index(Model model) {

        List<ProductEntity> products = productRepo.findAll();
        model.addAttribute("products", products);

        return "admin/products/index";

    }

    @GetMapping("/add")
    public String add(ProductEntity product, Model model) {

        List<CategoryEntity> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid ProductEntity product, BindingResult result, RedirectAttributes redirect, Model model, MultipartFile file) throws IOException {
        
        if (result.hasErrors()) {
            return "admin/products/add";
        }

        boolean isFileValid=false;
        byte[] bytes = file.getBytes();
        String filename=file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/"+filename);

        if(filename.endsWith("jpg") || filename.endsWith("png")) {
            isFileValid=true;
        }
        
        redirect.addFlashAttribute("message", "Product Added");
        redirect.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");
                

        ProductEntity productExist = productRepo.findBySlug(slug);

        if(!isFileValid) {
            redirect.addFlashAttribute("message", "File must be of type .jpg or .png");
            redirect.addFlashAttribute("alertClass", "alert-danger");

        }
        else if (productExist != null) {
            redirect.addFlashAttribute("message", "Product exist, choose another");
            redirect.addFlashAttribute("alertClass", "alert-danger");

            
            //redirect.addFlashAttribute("product", product);
            

        } else {

            product.setSlug(slug);
            product.setImage(filename);
            productRepo.save(product);
            Files.write(path, bytes);
        }

        return "redirect:/admin/products/add";
    }

}
