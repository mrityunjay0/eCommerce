package com.eCommerce.controller;

import com.eCommerce.entity.Category;
import com.eCommerce.service.CategoryService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public CategoryService categoryService;

    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String adminIndex() {

        return "admin/index"; // Returns the view name "admin/index"
    }

    @GetMapping("/addProduct")
    public String addProduct(){

        return "admin/addProduct"; // Returns the view name "admin/addProduct"
    }

    // Displays the form to add a new category and lists existing categories
    @GetMapping("/addCategory")
    public String addCategory(Model m){

        m.addAttribute("categories", categoryService.getAllCategories()); // Adds the list of categories to the model
        return "admin/addCategory"; // Returns the view name "admin/addCategory"
    }


    // Handles the form submission for adding a new category
    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        String imageName = file!=null ? file.getOriginalFilename() : "No image";
        category.setImageName(imageName);

        boolean isExist = categoryService.existsByName(category.getName()); // Check if category with the same name exists

        if(isExist){
            session.setAttribute("errorMsg", "Category already exists");
        }
        else{
            Category saveCategory = categoryService.saveCategory(category);

            if(saveCategory==null){
                session.setAttribute("errorMsg", "Something went wrong!! Category not added.");
            }
            else{
                File saveFile = new ClassPathResource("static/img/category_img").getFile();
                File file1 = new File(saveFile.getAbsolutePath()+File.separator+imageName);
                file.transferTo(file1); // Save the uploaded file to the specified path

                session.setAttribute("successMsg", "Category added successfully.");
            }
        }

        return "redirect:/admin/addCategory"; // Redirects to the addCategory page after saving
    }


    // Handles the deletion of a category by its ID
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session){

        boolean isDeleted = categoryService.deleteCategory(id); // Attempt to delete the category

        if(isDeleted){
            session.setAttribute("successMsg", "Category deleted successfully.");
        }
        else{
            session.setAttribute("errorMsg", "Something went wrong!! Category not deleted.");
        }

        return "redirect:/admin/addCategory"; // Redirects to the addCategory page after deletion
    }


    // Loads the category update form with the existing category details
    @GetMapping("/loadUpdateCategory/{id}")
    public String loadUpdateCategory(@PathVariable int id, Model m){
        m.addAttribute("category", categoryService.getCategoryById(id)); // Adds the category to be updated to the model
        return "admin/editCategory"; // Returns the view name "admin/ediCategory"
    }


    // Handles the form submission for updating an existing category
    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        Category oldCategory = categoryService.getCategoryById(category.getId()); // Save the updated category
        String imageNameOld = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

        if(oldCategory!=null){
            oldCategory.setName(category.getName());
            oldCategory.setActive(category.isActive());
            oldCategory.setImageName(imageNameOld);
        }
        Category updatedCategory = categoryService.saveCategory(oldCategory);

        if(updatedCategory==null){
            session.setAttribute("errorMsg", "Something went wrong!! Category not updated.");
        }
        else{
            File saveFile = new ClassPathResource("static/img/category_img").getFile();
            File file1 = new File(saveFile.getAbsolutePath()+File.separator+imageNameOld);
            file.transferTo(file1); // Save the uploaded file to the specified path
            session.setAttribute("successMsg", "Category updated successfully.");
        }

        return "redirect:/admin/addCategory"; // Redirects to the addCategory page after updating
    }
}