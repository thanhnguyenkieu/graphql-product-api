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
        return products.findByCategoriesId(categoryId);
    }
    @QueryMapping public List<Category> categories() { return categories.findAll(); }
    @QueryMapping public List<User> users() { return users.findAll(); }

    @MutationMapping public Product createProduct(@Argument String title, @Argument Integer quantity,
                                                   @Argument String desc, @Argument BigDecimal price,
                                                   @Argument Long userId, @Argument List<Long> categoryIds) {
        Product product = new Product(title, quantity, desc, price, userId);
        attachCategories(product, categoryIds);
        return products.save(product);
    }
    @MutationMapping public Product updateProduct(@Argument Long id, @Argument String title,
                                                   @Argument Integer quantity, @Argument String desc,
                                                   @Argument BigDecimal price, @Argument Long userId,
                                                   @Argument List<Long> categoryIds) {
        Product product = products.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setTitle(title); product.setQuantity(quantity); product.setDesc(desc);
        product.setPrice(price); product.setUserId(userId); product.getCategories().clear();
        attachCategories(product, categoryIds);
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
    private void attachCategories(Product product, List<Long> ids) {
        if (ids != null) ids.forEach(id -> categories.findById(id).ifPresent(product.getCategories()::add));
    }
}
