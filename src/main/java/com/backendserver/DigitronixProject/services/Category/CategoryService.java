package com.backendserver.DigitronixProject.services.Category;

import com.backendserver.DigitronixProject.dtos.CategoryDTO;
import com.backendserver.DigitronixProject.models.Category;
import com.backendserver.DigitronixProject.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category create(CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if(existingCategory.isPresent()) {
            throw new RuntimeException("A category with the same name already exists.");
        }
        Category newCategory = Category.builder()
                .name(categoryDTO.getName()).build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category update(long categoryId, CategoryDTO categoryDTO) throws Exception{
        Category existingCategory = getById(categoryId);

        // Check the new category has existed before
        Optional<Category> existingCategoryWithName = categoryRepository.findByName(categoryDTO.getName());
        if (existingCategoryWithName.isPresent() && existingCategoryWithName.get().getId() != categoryId) {
            throw new RuntimeException("A category with the same name already exists.");
        }

        existingCategory.setName(categoryDTO.getName());
        return categoryRepository.save(existingCategory);
    }


    @Override
    @Transactional
    public void delete(long id) {
        //xóa cứng
        categoryRepository.deleteById(id);
    }
}
