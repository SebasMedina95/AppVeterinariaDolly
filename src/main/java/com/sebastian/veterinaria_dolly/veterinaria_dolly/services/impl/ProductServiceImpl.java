package com.sebastian.veterinaria_dolly.veterinaria_dolly.services.impl;

import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Category;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Product;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.Supplier;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.entities.dtos.create.CreateProductDto;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.uploads.CloudinaryService;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.helpers.utils.ResponseWrapper;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.CategoryRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.ProductRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.repositories.SupplierRepository;
import com.sebastian.veterinaria_dolly.veterinaria_dolly.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    static String dummiesUser = "usuario123";
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CloudinaryService cloudinaryService,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public ResponseWrapper<Product> create(CreateProductDto product) throws IOException {

        //Ajustamos Slug y validamos que no se repita
        String name = product.getName().toUpperCase();
        String slug = generateSlug(product.getName());
        Optional<Product> getProductValid = productRepository.getProductByNameAndBySlug(name, slug);

        if( getProductValid.isPresent() ){
            return new ResponseWrapper<>(null, "El producto ya se encuentra registrado");
        }

        //Validemos categoría y también proveedor
        Optional<Category> category = categoryRepository.findById(product.getCategoryId());
        Optional<Supplier> supplier = supplierRepository.findById(product.getSupplierId());

        if( category.isEmpty() || supplier.isEmpty()){
            return new ResponseWrapper<>(null, "La categoría y/o proveedor no fueron encontrados para asociar al producto");
        }

        Category getCategory = category.orElseThrow();
        Supplier getSupplier = supplier.orElseThrow();

        //Ajustamos imágenes a registrar
        List<String> imageUrls = new ArrayList<>();
        if(product.getImages() != null){
            for (MultipartFile image : product.getImages()) {
                String imageUrl = cloudinaryService.uploadFile(image);
                imageUrls.add(imageUrl);
            }
        }else{
            imageUrls.add("'default'");
        }

        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setSlug(slug);
        newProduct.setDescription(product.getDescription());
        newProduct.setStock(product.getStock());
        newProduct.setSizes(product.getSizes());
        newProduct.setPrice(product.getPrice());
        newProduct.setImages(String.join(",", imageUrls));
        newProduct.setCategory(getCategory);
        newProduct.setSupplier(getSupplier);
        newProduct.setStatus(true);
        newProduct.setUserCreated(dummiesUser); //! Ajustar cuando se implemente Security
        newProduct.setDateCreated(new Date()); //! Ajustar cuando se implemente Security
        newProduct.setUserUpdated(dummiesUser); //! Ajustar cuando se implemente Security
        newProduct.setDateUpdated(new Date()); //! Ajustar cuando se implemente Security

        return new ResponseWrapper<>(productRepository.save(newProduct), "Producto guardado correctamente");

    }

    @Override
    public ResponseWrapper<List<Product>> findAll() {
        return new ResponseWrapper<>(List.of(), "findAll desde el servicio Product de implementación");
    }

    @Override
    public ResponseWrapper<Product> findById(Long id) {
        return new ResponseWrapper<>(null, "findById desde el servicio Product de implementación");
    }

    @Override
    public ResponseWrapper<Product> update(Long id, Product product) {
        return new ResponseWrapper<>(null, "update desde el servicio Product de implementación");
    }

    @Override
    public ResponseWrapper<Product> delete(Long id) {
        return new ResponseWrapper<>(null, "delete desde el servicio Product de implementación");
    }

    public static String generateSlug(String input) {
        // Eliminar acentos y caracteres especiales
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        // Reemplazar caracteres especiales y espacios con guiones
        return normalized.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Remover caracteres no alfanuméricos excepto espacios y guiones
                .replaceAll("\\s+", " ")         // Remover espacios múltiples
                .trim()                                            // Eliminar espacios al inicio y al final
                .replaceAll("\\s", "-");
    }

}
