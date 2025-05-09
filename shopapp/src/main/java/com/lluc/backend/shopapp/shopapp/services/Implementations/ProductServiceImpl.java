package com.lluc.backend.shopapp.shopapp.services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.entities.Pricing;
import com.lluc.backend.shopapp.shopapp.models.entities.PricingValue;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.entities.ProductTranslation;
import com.lluc.backend.shopapp.shopapp.models.request.ProductRequest;
import com.lluc.backend.shopapp.shopapp.repositories.CategoryRepository;
import com.lluc.backend.shopapp.shopapp.repositories.CompanyRepository;
import com.lluc.backend.shopapp.shopapp.repositories.ProductRepository;
import com.lluc.backend.shopapp.shopapp.repositories.SustainableCategoryRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductService productService;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final SustainableCategoryRepository sustainableCategoryRepository;

    @Autowired
    private final CompanyRepository companyRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, 
                              SustainableCategoryRepository sustainableCategoryRepository, CompanyRepository companyRepository, ProductService productService) {
        this.companyRepository = companyRepository;
        this.sustainableCategoryRepository = sustainableCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product add(ProductRequest productRequest) {
        // Recuperar el usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Usuario autenticado: " + username);

        // Recuperar la empresa asociada al usuario autenticado
        Company company = companyRepository.findByAdministratorUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró una empresa asociada al usuario autenticado"));

        // Crear un nuevo producto
        Product product = new Product();

        // Mapear los datos de ProductRequest a Product
        product.setTranslations(productRequest.getProductName().entrySet().stream()
                .map(entry -> {
                    ProductTranslation translation = new ProductTranslation();
                    translation.setLanguageCode(entry.getKey());
                    translation.setName(entry.getValue());
                    translation.setDescription(productRequest.getDescription().get(entry.getKey()));
                    translation.setProduct(product);
                    return translation;
                }).toList());

        product.setCategory(categoryRepository.findById(productRequest.getCategory())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));

        product.setSustainableCategories(productRequest.getSustainableCategory().stream()
                .map(id -> sustainableCategoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Categoría sostenible no encontrada: " + id)))
                .toList());

        product.setTax(productRequest.getTaxes());
        product.setPhotos(productRequest.getPhotos());
        product.setCompany(company);

        if (productRequest.getPricing() != null) {
            product.setPricing(productRequest.getPricing().stream().map(pricingRequest -> {
                Pricing pricing = new Pricing();
                pricing.setCategoryName(pricingRequest.getCategoryName());
                pricing.setProduct(product);
                pricing.setValues(pricingRequest.getValues().stream().map(valueRequest -> {
                    PricingValue value = new PricingValue();
                    value.setCategoryValue(valueRequest.getCategoryValue());
                    value.setPrice(valueRequest.getPrice());
                    value.setCost(valueRequest.getCost());
                    value.setStock(valueRequest.getStock());
                    value.setPricing(pricing);
                    return value;
                }).toList());
                return pricing;
            }).toList());
        }

    return productRepository.save(product);
}

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCompany(Long companyId) {
        return productRepository.findByCompanyId(companyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

        // Actualizar los campos del producto existente
        existingProduct.setTranslations(product.getTranslations());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setSustainableCategories(product.getSustainableCategories());
        existingProduct.setTax(product.getTax());
        existingProduct.setPhotos(product.getPhotos());

        return productRepository.save(existingProduct);
    }
}