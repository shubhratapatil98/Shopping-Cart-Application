package com.mycart.cmsshoppingcart.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mycart.cmsshoppingcart.models.data.PageEntity;
import com.mycart.cmsshoppingcart.models.data.PageRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
@RequestMapping("/admin/pages")
public class AdminController {

    @Autowired
    private PageRepo pageRepository;

    // public AdminController(PageRepo pageRepository) {
    // this.pageRepository=pageRepository;
    // }

    @GetMapping
    public String index(Model model) {

        //List<PageEntity> pages = pageRepository.findAll();
        List<PageEntity> pages = pageRepository.findAllByOrderBySortingAsc();
        //System.out.println("----------------------------" + pages);
        model.addAttribute("pages", pages); 
        return "/admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute PageEntity page) {

        // model.addAttribute("pages", new PageEntity());
        return "admin/pages/add";
    }

    @PostMapping("/add")
    public String add(@Valid PageEntity page, BindingResult result, RedirectAttributes redirect, Model model) {
        System.out.println(page.getContent() + "***********");
        // System.out.println(page.getContent());
        if (result.hasErrors()) {
            return "admin/pages/add";
        }

        redirect.addFlashAttribute("message", "Page Added");

        redirect.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-")
                : page.getSlug().toLowerCase().replace(" ", "-");

        PageEntity slugExist = pageRepository.findBySlug(slug);

        if (slugExist != null) {
            redirect.addFlashAttribute("message", "Slug exist, choose another");
            redirect.addFlashAttribute("alertClass", "alert-danger");

            System.out.println(page.getContent() + "----------***********-------------");
            redirect.addFlashAttribute("page", page);
            System.out.println(page.getTitle() + "------------------------");

        } else {

            page.setSlug(slug);
            page.setSorting(100);

            pageRepository.save(page);
        }

        return "redirect:/admin/pages/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        // PageEntity page = pageRepository.getOne(id);
        Optional<PageEntity> page1 = pageRepository.findById(id);
        System.out.println("retrieved data start "+page1.get() + "end");
        System.out.println("--------------------------");
        PageEntity page = page1.get();
        model.addAttribute("page", page);
        System.out.println(page+" this is selected apge y user for modification-----------GET-------------");
        return "admin/pages/edit";
    }


    @PostMapping("/edit")
    public String edit(@Valid PageEntity page, BindingResult result, RedirectAttributes redirect, Model model)
    {
        System.out.println(page +" user changes------------POST------------");
        PageEntity currentPage = page;
        //System.out.println(page.getContent());
        if(result.hasErrors())
        {
            model.addAttribute("currPageTitle", currentPage.getTitle());
            return "admin/pages/edit";
        }

        redirect.addFlashAttribute("message", "Page Edited");
        redirect.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ", "-");

        System.out.println(page + " this is selected page");
        //PageEntity slugExist = pageRepository.findBySlug(page.getId(), slug);
        PageEntity slugExist = pageRepository.findBySlugAndIdNot(slug, page.getId());
        System.out.println(slugExist + " match found entry------------------");

        if(slugExist != null){
            redirect.addFlashAttribute("message", "Slug exist, choose another");
            redirect.addFlashAttribute("alertClass", "alert-danger");

            
            redirect.addFlashAttribute("page", page);
            

        }
        else {
            System.out.println(slug+"####");
            page.setSlug(slug);
            System.out.println("-----------1----------");
            //pageRepository.updatePage(page.getId(), page.getTitle();
            pageRepository.save(page);
            System.out.println("-----------2----------");
        }

        return "redirect:/admin/pages/edit/"+page.getId();
    }



    @GetMapping("/delete/{id}")
    public String edit(@PathVariable int id, RedirectAttributes redirectAttributes) {
        // PageEntity page = pageRepository.getOne(id);

        pageRepository.deleteById(id);
        
        redirectAttributes.addFlashAttribute("message", "Page deleted successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
        return "redirect:/admin/pages";
    }


    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
        
        int c = 1;
        PageEntity page;

        for (int pageId : id) 
        {
            page = pageRepository.findById(pageId).get();
            page.setSorting(c);
            pageRepository.save(page);
            c++;

        }
        return "ok";
    }


}
