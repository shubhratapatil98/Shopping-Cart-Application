package com.mycart.cmsshoppingcart.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mycart.cmsshoppingcart.models.data.CategoryEntity;
import com.mycart.cmsshoppingcart.models.data.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping
    public String index(Model model) {
        //List<CategoryEntity> categories = categoryRepo.findAll();
        List<CategoryEntity> categories = categoryRepo.findAllByOrderBySortingAsc();
        model.addAttribute("categories", categories);
        return "admin/categories/index";
    }

    // @ModelAttribute("category")----2
    // public CategoryEntity getCategory() {
    //     return new CategoryEntity();

    // }

    @GetMapping("/add")
    public String add(CategoryEntity category) {
    //public String add() {--2
        return "admin/categories/add";
    }


    @PostMapping("/add")
    public String add(@Valid CategoryEntity category, BindingResult result, RedirectAttributes redirect, Model model) {
        
        if (result.hasErrors()) {
            return "admin/categories/add";
        }

        redirect.addFlashAttribute("message", "Category Added");

        redirect.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");
        CategoryEntity categoryExist = categoryRepo.findByName(category.getName());

        if (categoryExist != null) {
            redirect.addFlashAttribute("message", "CategoryExist exist, choose another");
            redirect.addFlashAttribute("alertClass", "alert-danger");

            redirect.addFlashAttribute("categoryInfo", category);
            //System.out.println(page.getTitle() + "------------------------");

        } else {

            category.setSlug(slug);
            category.setSorting(100);

            categoryRepo.save(category);
        }

        return "redirect:/admin/categories/add";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        // PageEntity page = pageRepository.getOne(id);
        Optional<CategoryEntity> category1 = categoryRepo.findById(id);
        
        CategoryEntity category = category1.get();
        model.addAttribute("category", category);
        
        return "admin/categories/edit";
    }


    @PostMapping("/edit")
    public String edit(@Valid CategoryEntity category, BindingResult result, RedirectAttributes redirect, Model model)
    {
        
        CategoryEntity currentCategory = category;
        //System.out.println(page.getContent());
        if(result.hasErrors())
        {
            model.addAttribute("currCategoryName", currentCategory.getName());
            return "admin/categories/edit";
        }

        redirect.addFlashAttribute("message", "Page Edited");
        redirect.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-") ;

        
        CategoryEntity categoryExist = categoryRepo.findByName(category.getName());
        
        if (categoryExist != null) {
            redirect.addFlashAttribute("message", "CategoryExist exist, choose another");
            redirect.addFlashAttribute("alertClass", "alert-danger");

            redirect.addFlashAttribute("categoryInfo", category);
           

        } else {

            category.setSlug(slug);
            //category.setSorting(100);
            categoryRepo.save(category);
        }


        return "redirect:/admin/categories/edit/"+category.getId();
    }


    @GetMapping("/delete/{id}")
    public String edit(@PathVariable int id, RedirectAttributes redirectAttributes) {
        
        categoryRepo.deleteById(id);
        
        redirectAttributes.addFlashAttribute("message", "Category deleted successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
        return "redirect:/admin/categories";
    }

    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
        
        int c = 1;
        CategoryEntity category;

        for (int categoryId : id) 
        {
            category = categoryRepo.findById(categoryId).get();
            category.setSorting(c);
            categoryRepo.save(category);
            c++;

        }
        return "ok";
    }

}
