package com.eCommerce.controller;

import com.eCommerce.entity.Category;
import com.eCommerce.entity.Product;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.ProductService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private CategoryService categoryService;
    private ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    // Displays the admin index page
    @GetMapping("/")
    public String adminIndex() {

        return "admin/index"; // Returns the view name "admin/index"
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


    @GetMapping("/addProduct")
    public String addProduct(Model m){

        List<Category> categories = categoryService.getAllCategories(); // Fetches all categories from the service
        m.addAttribute("categories", categories); // Adds the list of categories to the model
        return "admin/addProduct"; // Returns the view name "admin/addProduct"
    }


    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        String imageName = file!=null ? file.getOriginalFilename() : "No image";
        product.setImageName(imageName);

        product.setDiscountPrice(product.getPrice() - (product.getPrice() * product.getDiscount() / 100.0));

            Product saveProduct = productService.saveProduct(product); // Save the product using the product service

            if(saveProduct==null){
                session.setAttribute("errorMsg", "Something went wrong!! Product not added.");
            }
            else{
                File saveFile = new ClassPathResource("static/img/product_img").getFile();
                File file1 = new File(saveFile.getAbsolutePath()+File.separator+imageName);
                file.transferTo(file1); // Save the uploaded file to the specified path

                session.setAttribute("successMsg", "Product added successfully.");
            }

        return "redirect:/admin/addProduct"; // Redirects to the addProduct page after saving
    }


    // Displays the list of all products
    @GetMapping("/viewProducts")
    public String viewProducts(Model m){
        List<Product> products = productService.getAllProducts(); // Fetches all products from the service
        m.addAttribute("products", products); // Adds the list of products to the model

        return "admin/viewProducts"; // Returns the view name "admin/viewProducts"
    }


    // Handles the deletion of a product by its ID
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session){

        boolean isDeleted = productService.deleteProduct(id); // Attempt to delete the product

        if(isDeleted){
            session.setAttribute("successMsg", "Product deleted successfully.");
        }
        else{
            session.setAttribute("errorMsg", "Something went wrong!! Product not deleted.");
        }

        return "redirect:/admin/viewProducts"; // Redirects to the viewProducts page after deletion
    }


    // Loads the product update form with the existing product details
    @GetMapping("/loadUpdateProduct/{id}")
    public String loadUpdateProduct(@PathVariable int id, Model m){
        m.addAttribute("product", productService.getProductById(id)); // Adds the product to be updated to the model
        m.addAttribute("categories", categoryService.getAllCategories()); // Adds the list of categories to the model
        return "admin/editProduct"; // Returns the view name "admin/editProduct
    }


    // Handles the form submission for updating an existing product
    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        Product oldProduct = productService.getProductById(product.getId()); // Load the existing product
        String imageNameOld = file.isEmpty() ? oldProduct.getImageName() : file.getOriginalFilename();

        if(product.getDiscount()<0 || product.getDiscount()>100){
            session.setAttribute("errorMsg", "Invalid discount value! Please enter a value between 0 and 100.");
            return "redirect:/admin/loadUpdateProduct/"+product.getId();
        }
        else{

            if(oldProduct!=null){
                oldProduct.setTitle(product.getTitle());
                oldProduct.setDescription(product.getDescription());
                oldProduct.setPrice(product.getPrice());
                oldProduct.setCategory(product.getCategory());
                oldProduct.setActive(product.isActive());
                oldProduct.setStock(product.getStock());
                oldProduct.setImageName(imageNameOld);
                oldProduct.setDiscount(product.getDiscount());

                double discountPrice = oldProduct.getPrice() - (oldProduct.getPrice() * oldProduct.getDiscount() / 100);
                oldProduct.setDiscountPrice(discountPrice);

            }
            Product updatedProduct = productService.saveProduct(oldProduct);

            if(updatedProduct==null){
                session.setAttribute("errorMsg", "Something went wrong!! Product not updated.");
            }
            else{
                File saveFile = new ClassPathResource("static/img/product_img").getFile();
                File file1 = new File(saveFile.getAbsolutePath()+File.separator+imageNameOld);
                file.transferTo(file1); // Save the uploaded file to the specified path
                session.setAttribute("successMsg", "Product updated successfully.");
            }

        }

        return "redirect:/admin/viewProducts"; // Redirects to the viewProducts page after updating
    }
}