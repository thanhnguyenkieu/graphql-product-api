package com.example.graphqlproductapi.graphql;

import com.example.graphqlproductapi.category.*;
import com.example.graphqlproductapi.product.*;
import com.example.graphqlproductapi.user.*;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ProductGraphQlController {
    private final ProductRepository products;
    private final CategoryRepository categories;
    private final UserRepository users;

    public ProductGraphQlController(ProductRepository products, CategoryRepository categories, UserRepository users) {
        this.products = products; this.categories = categories; this.users = users;
    }

    @QueryMapping public List<Product> products() { return products.findAllByOrderByPriceAsc(); }
    @QueryMapping public List<Product> productsByCategory(@Argument Long categoryId) {
        return products.findByCategoryId(categoryId);
    }
    @QueryMapping public List<Category> categories() { return categories.findAll(); }
    @QueryMapping public List<User> users() { return users.findAll(); }

    @MutationMapping public Product createProduct(@Argument String title, @Argument Integer quantity,
                                                   @Argument String desc, @Argument BigDecimal price,
                                                   @Argument Long userId, @Argument Long categoryId) {
        Product product = new Product(title, quantity, desc, price, userId);
        product.setCategory(findCategory(categoryId));
        return products.save(product);
    }
    @MutationMapping public Product updateProduct(@Argument Long id, @Argument String title,
                                                   @Argument Integer quantity, @Argument String desc,
                                                   @Argument BigDecimal price, @Argument Long userId,
                                                   @Argument Long categoryId) {
        Product product = products.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setTitle(title); product.setQuantity(quantity); product.setDesc(desc);
        product.setPrice(price); product.setUserId(userId);
        product.setCategory(findCategory(categoryId));
        return products.save(product);
    }
    @MutationMapping public Boolean deleteProduct(@Argument Long id) {
        if (!products.existsById(id)) return false;
        products.deleteById(id); return true;
    }
    @MutationMapping public Category createCategory(@Argument String name, @Argument String images) {
        return categories.save(new Category(name, images));
    }
    @MutationMapping public Category updateCategory(@Argument Long id, @Argument String name, @Argument String images) {
        Category category = categories.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setName(name); category.setImages(images); return categories.save(category);
    }
    @MutationMapping public Boolean deleteCategory(@Argument Long id) {
        if (!categories.existsById(id)) return false;
        categories.deleteById(id); return true;
    }
    @MutationMapping public User createUser(@Argument String fullname, @Argument String email,
                                            @Argument String password, @Argument String phone,
                                            @Argument List<Long> categoryIds) {
        User user = new User(fullname, email, password, phone);
        attachUserCategories(user, categoryIds);
        return users.save(user);
    }
    @MutationMapping public User updateUser(@Argument Long id, @Argument String fullname,
                                            @Argument String email, @Argument String password,
                                            @Argument String phone, @Argument List<Long> categoryIds) {
        User user = users.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullname(fullname); user.setEmail(email); user.setPassword(password); user.setPhone(phone);
        user.getCategories().clear();
        attachUserCategories(user, categoryIds);
        return users.save(user);
    }
    @MutationMapping public Boolean deleteUser(@Argument Long id) {
        if (!users.existsById(id)) return false;
        users.deleteById(id);
        return true;
    }
    private Category findCategory(Long id) {
        return id == null ? null : categories.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }
    private void attachUserCategories(User user, List<Long> ids) {
        if (ids != null) ids.forEach(id -> user.getCategories().add(findCategory(id)));
    }
}
