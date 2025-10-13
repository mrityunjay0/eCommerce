package com.eCommerce.controller;

import com.eCommerce.entity.Category;
import com.eCommerce.entity.Product;
import com.eCommerce.entity.ProductOrder;
import com.eCommerce.entity.User;
import com.eCommerce.service.CategoryService;
import com.eCommerce.service.OrderService;
import com.eCommerce.service.ProductService;
import com.eCommerce.service.UserService;
import com.eCommerce.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private CategoryService categoryService;
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private CommonUtils commonUtils;

    public AdminController(CategoryService categoryService, ProductService productService,
                           UserService userService, OrderService orderService, CommonUtils commonUtils) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.commonUtils = commonUtils;
    }

    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        User user = null;
        if (p != null) {
            user = userService.getUserByEmail(p.getName());
        }
        if (user == null) {
            user = new User();
            user.setName("Guest");
        }
        m.addAttribute("user", user);

        List<Category> categories = categoryService.getAllActiveCategories();
        m.addAttribute("categories", categories);
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


    // Displays the list of all users with the role "ROLE_USER"
    @GetMapping("/viewUsers")
    public String viewUsers(Model m) {
        List<User> users = userService.getAllUsersByRole("ROLE_USER"); // Fetches all users with the role "ROLE_ADMIN"
        m.addAttribute("users", users); // Adds the list of users to the model
        return "admin/users"; // Returns the view name "admin/viewUsers"
    }


    @PostMapping("/updateUserStatus/{id}")
    public String updateUserStatus(@PathVariable Integer id, HttpSession session) {

        User user = userService.getUserById(id);

        if (user != null) {
            if(user.isEnabled()){
                user.setEnabled(false);
            }
            else{
                user.setEnabled(true);
            }
            userService.updateUserStatus(user); // Update the user's status
            String status = user.isEnabled() ? "enabled" : "disabled";
            session.setAttribute("successMsg", "User " + status + " successfully.");
        } else {
            session.setAttribute("errorMsg", "User not found.");
        }

        return "redirect:/admin/viewUsers"; // Redirects to the viewUsers page after updating the status
    }


    // Displays the orders page for the admin
    @GetMapping("/orders")
    public String viewOrders(Model m){

        List<ProductOrder> orders = orderService.getAllOrders();
        if (orders == null) orders = List.of();

        m.addAttribute("orders", orders);
        m.addAttribute("isEmpty", orders.isEmpty());

        return "admin/orders";
    }


    // Updates the status of an order based on admin input
    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer status,
                                    HttpSession session, HttpServletRequest request) {

        try {
            if (id == null || status == null) {
                session.setAttribute("errorMsg", "Invalid request parameters.");
                return "redirect:/admin/orders";
            }

            String newStatus = switch (status) {
                case 1 -> "In Progress";
                case 2 -> "Complete";
                case 3 -> "Order Received";
                case 4 -> "Packed";
                case 5 -> "Shipped";
                case 6 -> "Out for Delivery";
                case 7 -> "Delivered";
                case 8 -> "Cancelled";
                default -> null;
            };

            if (newStatus == null) {
                session.setAttribute("errorMsg", "Invalid status value.");
                return "redirect:/admin/orders";
            }

            // Update in DB
            orderService.updateOrderStatus(id, newStatus);

            // Fetch the updated order (ensure this exists in your service)
            ProductOrder updated = orderService.getOrdersById(id);

            // Fire the email (errors are caught & surfaced in the session)
            try {
                commonUtils.sendOrderStatusUpdateMail(updated, newStatus, request);
            } catch (Exception mailEx) {
                // don't block admin flow if mail fails
                session.setAttribute("errorMsg", "Order updated, but email failed: " + mailEx.getMessage());
            }

            session.setAttribute("successMsg", "Order status updated to '" + newStatus + "'.");
        } catch (Exception ex) {
            session.setAttribute("errorMsg", "Failed to update status: " + ex.getMessage());
        }
        return "redirect:/admin/orders";
    }


    // Searches for orders by Order ID
    @GetMapping("/searchOrdersByOrderId")
    public String searchOrdersByOrderId(@RequestParam(required = false) String orderId, Model m, HttpSession session) {

        if (orderId == null || orderId.trim().isEmpty()) {
            session.setAttribute("errorMsg", "Please enter a valid Order ID.");
            return "redirect:/admin/orders";
        }

        ProductOrder order = orderService.getOrdersByOrderId(orderId.trim());
        if (order == null) {
            m.addAttribute("isEmpty", true);
            m.addAttribute("orders", List.of());
            session.setAttribute("errorMsg", "No orders found with Order ID: " + orderId);
            return "redirect:/admin/orders";
        } else {
            m.addAttribute("isEmpty", false);
            m.addAttribute("orders", List.of(order));
        }
        return "admin/orders";
    }


    // Searches for products by a search string
    @GetMapping("/searchProducts")
    public String searchProducts(@RequestParam(required = false) String ch, Model m, HttpSession session) {

        List<Product> products = productService.searchProducts(ch);
        if (products == null || products.isEmpty()) {
            m.addAttribute("isEmpty", true);
            m.addAttribute("products", List.of());
            session.setAttribute("errorMsg", "No products found matching: " + ch);
            return "redirect:/admin/viewProducts";
        } else {
            m.addAttribute("isEmpty", false);
            m.addAttribute("products", products);
        }
        return "admin/viewProducts";
    }


    @GetMapping("/searchUser")
    public String searchUser(@RequestParam(required = false) String ch, Model m, HttpSession session) {

        List<User> users = userService.searchUsersByNameOrEmail(ch);
        if (users == null || users.isEmpty()) {
            m.addAttribute("isEmpty", true);
            m.addAttribute("users", List.of());
            session.setAttribute("errorMsg", "No users found matching: " + ch);
            return "redirect:/admin/viewUsers";
        } else {
            m.addAttribute("isEmpty", false);
            m.addAttribute("users", users);
        }
        return "admin/users";
    }

}