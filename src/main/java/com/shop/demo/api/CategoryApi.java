package com.shop.demo.api;

import java.util.Optional;

import com.shop.demo.model.Category;
import com.shop.demo.model.Product;
import com.shop.demo.service.CategoryManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryApi {
    private CategoryManager categoryManager;

    @Autowired
    public CategoryApi(CategoryManager categoryManager) {
        super();
        this.categoryManager = categoryManager;
    }

    @GetMapping("/all")
    public Iterable<Category> getAll() {
        return categoryManager.findAll();
    }

    @GetMapping("/id")
    public Optional<Category> getById(@RequestParam Long index) {
        return categoryManager.findById(index);
    }

    @GetMapping(value = "/{categoryId}")
    public Optional<Category> getId(@PathVariable("categoryId") Long employeeId) {
        return categoryManager.findById(employeeId);
    }

    @PostMapping("/save")
    public Category add(@RequestBody Category category) {
        return categoryManager.save(category);
    }
}
