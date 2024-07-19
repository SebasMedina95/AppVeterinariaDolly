package com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.data;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateCategoryDto;

import java.util.Date;

public class DataCategories {

    private DataCategories(){}

    static String user = "123456789";

    public static final Category category1 =
            new Category(1L, "[Mock] Comida para Perro", true, user, new Date(), user, new Date());

    public static final Category category2 =
            new Category(2L, "[Mock] Comida para Gato", true, user, new Date(), user, new Date());

    public static final Category category3 =
            new Category(3L, "[Mock] Juegueteria Multigenero", true, user, new Date(), user, new Date());

    public static final Category category4 =
            new Category(4L, "[Mock] Ropa Multigenero", true, user, new Date(), user, new Date());


}
