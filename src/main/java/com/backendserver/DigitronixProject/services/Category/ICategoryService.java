package com.backendserver.DigitronixProject.services.Category;

import com.backendserver.DigitronixProject.dtos.CategoryDTO;
import com.backendserver.DigitronixProject.models.Category;

import java.util.List;

public interface ICategoryService {
    Category create(CategoryDTO category);
    Category getById(long id);
    List<Category> getAll();
    Category update(long categoryId, CategoryDTO category) throws Exception;
    void delete(long id);
}
