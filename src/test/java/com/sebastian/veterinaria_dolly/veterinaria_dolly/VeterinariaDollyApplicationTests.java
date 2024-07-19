package com.sebastian.veterinaria_dolly.veterinaria_dolly;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.data.DataCategories;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.CategoryRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.CategoryService;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class VeterinariaDollyApplicationTests {

	CategoryRepository categoryRepository;
	CategoryService categoryService;

	@BeforeEach
	void setup(){
		categoryRepository = mock(CategoryRepository.class);
		categoryService = new CategoryServiceImpl(categoryRepository);
	}

	@Test
	@DisplayName("Prueba de buscar por ID en Categorías")
	void contextLoads() {

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(DataCategories.category1));
		when(categoryRepository.findById(2L)).thenReturn(Optional.of(DataCategories.category2));
		when(categoryRepository.findById(3L)).thenReturn(Optional.of(DataCategories.category3));
		when(categoryRepository.findById(4L)).thenReturn(Optional.of(DataCategories.category4));

		ResponseWrapper<Category> category1 = categoryService.findById(1L);

		assertEquals("Categoría encontrada por ID correctamente", category1.getErrorMessage());
		assertNotNull(category1.getData());

	}

}
