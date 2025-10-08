
# 🛍️ E-Commerce Web Application (Under Development)

A full-stack **E-Commerce application** built using **Spring Boot, Spring Data JPA, Thymeleaf, Bootstrap, and MySQL**.  
This project is **currently under development** 🚧. New features like cart and checkout are in progress.

---

## **Features**

### 👥 User Features
- **User Registration & Login** with validation (email uniqueness, strong password policy recommended).
- **Secure Authentication** with Spring Security (session-based) and password hashing using **BCrypt**.
- **Browse Products** by category and keyword search.
- **Product Details** page with pricing, description, available stock, and image gallery.
- **Profile Basics**: view profile, change password (when enabled).
- **Forgot Password & Reset Password** using a secure, time-bound token sent via email (Gmail SMTP).
- **Add to Cart**: Add, update, or remove items from the shopping cart.
- **Checkout & Place Orders** Proceed to checkout, confirm order, and process order creation securely.
- **View My Orders**View past orders with details like order ID, total amount, and order date.
- **Cancel Order**Cancel orders (allowed only for specific statuses like pending/confirmed).
- **Graceful Errors** and messages on login, registration, and reset-password pages.


### 🛠️ Admin Features
- **Product Management**: Add, update, delete products; upload/change product images.
- **Category Management**: Create, edit, delete categories; toggle visibility.
- **User Management**: Enable/disable user accounts, view all users.
- **Admin Dashboard**: Quick links to manage products, categories & users (analytics coming soon).
- **Role-Based Access Control**: All `/admin/**` endpoints restricted to users with `ROLE_ADMIN`.

---

## **Tech Stack**
- **Backend:** Spring Boot, Spring MVC, Spring Security, Spring Data JPA (Hibernate)
- **Frontend:** Thymeleaf, HTML5, CSS3, Bootstrap
- **Database:** MySQL (InnoDB)
- **Build Tool:** Maven
- **Java Version:** 17+ (or the version specified in `pom.xml`)

> **Key Spring Starters**: `spring-boot-starter-web`, `spring-boot-starter-security`, `spring-boot-starter-data-jpa`, `spring-boot-starter-thymeleaf`, `spring-boot-starter-mail`, `mysql-connector-j`

---

## **Getting Started**

### 1. Clone the Repository
```bash
git clone https://github.com/mrityunjay0/eCommerce.git
cd eCommerce
```

---

### 2. Configure the Database
- Create a MySQL database, for example: `eCommerce_db`.
- Update the `application.properties` file with your database credentials:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  ```

---

### 3. Configure Mail Service (For Forgot Password)
To enable the Forgot Password feature, configure **Gmail SMTP**:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password  # Use Gmail App Password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
```

> ⚠️ **Note:**
> - You must enable 2-Step Verification on your Gmail account and generate an **App Password**.
> - The email configured above will be used to send password reset links.

---

### 4. Build the Project
```bash
mvn clean install
```

---

### 5. Run the Application
```bash
mvn spring-boot:run
```

---

### 6. Access the Application
- **Frontend:** `http://localhost:8080`
- **Admin Dashboard:** `http://localhost:8080/admin/`

Default admin credentials (if you seeded them manually):
```
Email: Admin Email
Password: Admin Password
```

---

## 📂 Project Structure

```
com.eCommerce
 ┣ 📂 config
 ┃ ┣ 📄 SecurityConfig.java           # Spring Security configuration
 ┃ ┣ 📄 CustomUser.java                # Custom UserDetails implementation
 ┃ ┣ 📄 UserDetailsServiceImpl.java    # Loads user-specific data for authentication
 ┃ ┣ 📄 AuthFailureHandlerImpl.java    # Custom authentication failure handler
 ┃ ┗ 📄 AuthSuccessHandlerImpl.java    # Custom authentication success handler
 ┃
 ┣ 📂 controller
 ┃ ┣ 📄 AdminController.java           # Admin actions and routes
 ┃ ┣ 📄 UserController.java            # User actions and routes
 ┃ ┗ 📄 HomeController.java            # Public & user-facing pages, Forgot/Reset Password
 ┃
 ┣ 📂 entity
 ┃ ┣ 📄 Cart.java                    # Shopping cart
 ┃ ┣ 📄 Category.java                  # Product categories
 ┃ ┣ 📄 OrderAddress.java            # Order address details
 ┃ ┣ 📄 OrderRequest.java            # Order request details
 ┃ ┣ 📄 Product.java                   # Products
 ┃ ┣ 📄 ProductOrder.java              # Product orders
 ┃ ┗ 📄 User.java                      # Users
 ┃
 ┣ 📂 repository
 ┃ ┣ 📄 CartRepository.java            # JPA repository for Cart
 ┃ ┣ 📄 CategoryRepository.java        # JPA repository for Category
 ┃ ┣ 📄 ProductOrderRepository.java     # JPA repository for ProductOrder
 ┃ ┣ 📄 ProductRepository.java         # JPA repository for Product
 ┃ ┗ 📄 UserRepository.java            # JPA repository for User
 ┃
 ┣ 📂 service
 ┃ ┣ 📄 CartService.java               # Cart business logic
 ┃ ┣ 📄 CategoryService.java           # Category business logic
 ┃ ┣ 📄 CommonService.java             # Common utility methods
 ┃ ┣ 📄 OrderService.java              # Order processing logic
 ┃ ┣ 📄 ProductService.java            # Product business logic
 ┃ ┗ 📄 UserService.java               # User management logic
 ┃
 ┣ 📂 serviceImpl
 ┃ ┣ 📄 CartServiceImpl.java          # Implementation of CartService
 ┃ ┣ 📄 CategoryServiceImpl.java       # Implementation of CategoryService
 ┃ ┣ 📄 CommonServiceImpl.java         # Implementation of CommonService
 ┃ ┣ 📄 OrderServiceImpl.java         # Implementation of OrderService
 ┃ ┣ 📄 ProductServiceImpl.java        # Implementation of ProductService
 ┃ ┗ 📄 UserServiceImpl.java           # Implementation of UserService
 ┃
 ┣ 📂 utils
 ┃ ┣ 📄 AppConstant.java               # App contant variables
 ┃ ┗ 📄 CommonUtils.java               # Utility class for email sending, URL generation
 ┃ ┗ 📄 OrderStatus.java                # Enum for order status
 ┃
 ┣ 📄 ECommerceApplication.java        # Spring Boot starter class
 ┃
 ┣ 📂 resources
 ┃ ┣ 📂 static                          # Static files
 ┃ │   ┣ 📂 css
 ┃ │   ┣ 📂 js
 ┃ │   ┗ 📂 img
 ┃ │       ├── product_img/
 ┃ │       ├── profile_img/
 ┃ │       └── category_img/
 ┃ │
 ┃ │
 ┃ ┗ 📂 templates
 ┃     ┣ 📂 admin                       # Admin pages
 ┃     │   ┣ 📄 addCategory.html
 ┃     │   ┣ 📄 addProduct.html
 ┃     │   ┣ 📄 editCategory.html
 ┃     │   ┣ 📄 editProduct.html
 ┃     │   ┣ 📄 index.html
 ┃     │   ┣ 📄 users.html
 ┃     │   ┗ 📄 viewProducts.html
 ┃     │
 ┃     ┣ 📂 user                        # User pages
 ┃     │   ┣ 📄 cart.html
 ┃     │   ┣ 📄 checkout.html
 ┃     │   ┣ 📄 home.html
 ┃     │   ┣ 📄 myOrders.html
 ┃     │   ┗ 📄 success.html
 ┃     │
 ┃     ┣ 📄 base.html                   # Common layout
 ┃     ┣ 📄 errorMessage.html           # Shows error messages while resetting password
 ┃     ┣ 📄 forgotPassword.html         # Forgot password page
 ┃     ┣ 📄 home.html                   # Homepage
 ┃     ┣ 📄 login.html                  # Login page
 ┃     ┣ 📄 products.html               # Product listing
 ┃     ┣ 📄 register.html               # Registration page
 ┃     ┣ 📄 resetPassword.html          # Reset password page (with token)
 ┃     ┗ 📄 viewProduct.html            # Product details
```

---

## **New Forgot Password Flow**

1. **User clicks "Forgot Password"** on the login page.
2. **Enter Registered Email** → submit the form.
3. **Generate Token**: Application creates a cryptographically secure, random token and stores it with:
    - the user reference,
    - **expiry time** (e.g., 15–30 minutes),
    - a **used** flag set to false.
4. **Send Email**: User receives an email containing a **reset link** such as:  
   `http://localhost:8080/reset-password?token=<token>`
5. **Open Reset Page**: Clicking the link loads **Reset Password** form (token passed as query param).
6. **Validate Token**: Backend checks:
    - token exists and is linked to the same email,
    - token not expired and not already used,
    - token signature or storage is intact.
7. **Set New Password**: User enters and confirms a new password.
8. **Update & Invalidate**: On success, password is hashed and saved. The token’s **used** flag is set true (or deleted), preventing reuse.
9. **Redirect & Feedback**: User is redirected to login with a success message.

> **Security Best Practices**
> - Use **BCrypt** (strength 10–12) for passwords.
> - Store tokens as **random strings**; optionally persist only a **hash** of the token.
> - Enforce token **expiry** and single-use.
> - Rate-limit the forgot-password endpoint to mitigate abuse.


## 🛒 **Cart & Checkout Flow**

### **Cart**
1. User clicks **Add to Cart** on any product.
2. The product is added to their cart in the database (associated with user ID).
3. The **Cart Page** (`/cart`) displays items, quantities, and total price.
4. Users can **update quantities** or **remove** products.
5. The cart persists across sessions for logged-in users.

### **Checkout**
1. User reviews cart contents and total amount.
2. Fills in address & payment details (currently simulated).
3. On confirming checkout:
    - Order summary is generated.
    - Cart is cleared for that user.
4. The system redirects to an **order confirmation page** with summary details.

> **Future Enhancements:**
> - Integrate payment gateway (Razorpay/Stripe).
> - Add order tracking and invoice download (PDF).

---

## 🗺️ **Roadmap (Updated)**

### 👥 **User Features**
✅ **User Registration & Login** (with email validation & strong password policy)  
✅ **Secure Authentication** using Spring Security & BCrypt  
✅ **Forgot & Reset Password** (via Gmail SMTP)  
✅ **Browse Products** by category & keyword search  
✅ **Product Details Page** with price, description, stock, and images  
✅ **Add to Cart / Update / Remove Items**  
✅ **Checkout & Place Orders** (complete order flow)  
✅ **View My Orders** (order history & details)  
✅ **Cancel Order** (available for pending/confirmed orders)  
✅ **Profile Basics** (view profile, change password)  
✅ **Graceful Error Handling** with clear messages on login, registration & password reset

---

### 🛠️ **Admin Features**
✅ **Product Management** (add, update, delete, upload/change product images)  
✅ **Category Management** (create, edit, delete, toggle visibility)  
✅ **User Management** (view users, enable/disable accounts)  
✅ **Admin Dashboard** (quick links to products, categories & users)  
✅ **Role-Based Access Control** – `/admin/**` routes restricted to users with `ROLE_ADMIN`

---

### ⚙️ **Technical Features**
✅ **Spring Boot Backend** (Spring MVC, Security, Data JPA, Thymeleaf)  
✅ **MySQL Integration** (InnoDB, schema-based)  
✅ **Email Service Integration** (Gmail SMTP for password reset)  
✅ **Transaction Management** (for checkout & order cancellation)  
✅ **Session Management & CSRF Protection**  
✅ **Graceful Exception Handling** (custom error pages)

---

### 🚀 **Upcoming Enhancements**
⬜ **Payment Gateway Integration** (Stripe / Razorpay)  
⬜ **Product Reviews & Ratings**  
⬜ **Order Tracking System**  
⬜ **Pagination, Sorting & Filtering** for product listings  
⬜ **Wishlist & Recently Viewed Items**  
⬜ **Address Book & Advanced Profile Management**  
⬜ **Admin Analytics & Product Performance Reports**  
⬜ **Unit / Integration Tests** (JUnit + MockMvc)  
⬜ **API Documentation** (Swagger / OpenAPI)

---

## 📧 Contact
**Author:** Mrityunjay Kumar
- 🌐 [GitHub](https://github.com/mrityunjay0)
- 💼 [LinkedIn](https://www.linkedin.com/in/mrityunjay555/)  
