package com.sebastian.veterinaria_dolly.veterinaria_dolly.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.data.DataCategories;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.CategoryRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
    }

    @Test
    @DisplayName("Test que SI encuentra Categorías por ID")
    void testCategoryFoundById() {

        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(DataCategories.category1)); //ID: 1L

        // Act
        ResponseWrapper<Category> response = categoryService.findById(1L);

        // Assert
        assertNotNull(response, "La respuesta no puede ser nula");
        assertNotNull(response.getData(), "Debemos tener datos del tipo ResponseWrapper");
        assertEquals("Categoría encontrada por ID correctamente", response.getErrorMessage(), "El mensaje debe indicar que se encontró correctamente");
        assertEquals(1L, response.getData().getId(), "El id debe hacer match con el que se encuentra");

    }

    @Test
    @DisplayName("Test que NO encuentra Categorías por ID")
    void testCategoryNoFoundById() {

        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseWrapper<Category> response = categoryService.findById(1L);

        // Assert
        assertNotNull(response, "El objeto general debe existir");
        assertNull(response.getData(), "La respuesta debe ser nula");
        assertEquals("La categoría no pudo ser encontrado por el ID", response.getErrorMessage(), "La categoría no debió ser encontrada por ID");

    }

}
