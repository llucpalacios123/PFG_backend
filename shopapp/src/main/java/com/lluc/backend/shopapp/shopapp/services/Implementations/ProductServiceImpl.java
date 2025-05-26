package com.lluc.backend.shopapp.shopapp.services.Implementations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.lluc.backend.shopapp.shopapp.models.dto.ProductDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.ProductMapper;
import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.entities.Pricing;
import com.lluc.backend.shopapp.shopapp.models.entities.PricingValue;
import com.lluc.backend.shopapp.shopapp.models.entities.Product;
import com.lluc.backend.shopapp.shopapp.models.entities.ProductTranslation;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.request.ProductRequest;
import com.lluc.backend.shopapp.shopapp.repositories.CategoryRepository;
import com.lluc.backend.shopapp.shopapp.repositories.CompanyRepository;
import com.lluc.backend.shopapp.shopapp.repositories.ProductRepository;
import com.lluc.backend.shopapp.shopapp.repositories.SustainableCategoryRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.ProductService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final SustainableCategoryRepository sustainableCategoryRepository;

    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private final UserService userService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, 
                              SustainableCategoryRepository sustainableCategoryRepository, CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.sustainableCategoryRepository = sustainableCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public ProductDTO add(ProductRequest productRequest) {
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

        return ProductMapper.INSTANCE.toProductDTO(productRepository.save(product)); // Usar MapStruct
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
        return ProductMapper.INSTANCE.toProductDTO(product); // Usar MapStruct
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCompany(String username) {
        // Recuperar el usuario por su username
        Optional<UserDTO> user = userService.findByUsernameDTO(username);

        if (user.isEmpty()) {
            throw new RuntimeException("User with username " + username + " not found");
        }

        // Obtener el ID de la compañía desde el usuario
        Long companyId = user.get().getCompanyId();

        // Buscar los productos de la compañía y convertirlos a DTO
        List<Product> products = productRepository.findByCompanyId(companyId);
        return ProductMapper.INSTANCE.toProductDTOList(products); // Usar MapStruct
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ProductMapper.INSTANCE.toProductDTOList(products); // Usar MapStruct
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

    @Override
    public Page<ProductDTO> searchProducts(String query, List<Long> sustainableCategories, List<Long> categories, Pageable pageable) {
        boolean sustainableEmpty = CollectionUtils.isEmpty(sustainableCategories);
        boolean categoriesEmpty = CollectionUtils.isEmpty(categories);

        // Si están vacías, igual pásalas como listas vacías pero controladas por flags
        if (sustainableEmpty) sustainableCategories = List.of();
        if (categoriesEmpty) categories = List.of();

        Page<Product> products = productRepository.findProductFiltereds(
            query,
            sustainableCategories,
            sustainableEmpty,
            categories,
            categoriesEmpty,
            pageable
        );

        return products.map(ProductMapper.INSTANCE::toProductDTO); // Usar MapStruct
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategoryAndSustainableCategories(Long categoryId, List<Long> sustainableCategoryIds) {
        List<Product> products = productRepository.findByCategoryAndSustainableCategories(categoryId, sustainableCategoryIds);
        return ProductMapper.INSTANCE.toProductDTOList(products); // Usar MapStruct
    }

    @Override
    @Transactional
    public void updateProductStock(Long productId, Map<String, Integer> updatedStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        updatedStock.forEach((category, quantity) -> {
            PricingValue pricingValue = product.getPricing().stream()
                    .flatMap(pricing -> pricing.getValues().stream())
                    .filter(value -> value.getCategoryValue().equals(category))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + category));

            pricingValue.setStock(quantity);
        });

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateStock(Product product, String category, int quantity) {
        PricingValue pricingValue = product.getPricing().stream()
                .flatMap(pricing -> pricing.getValues().stream())
                .filter(value -> value.getCategoryValue().equals(category))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada para el producto con ID: " + product.getId()));

        if (pricingValue.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente para el producto con ID: " + product.getId() +
                    " en la categoría: " + category);
        }

        pricingValue.setStock(pricingValue.getStock() - quantity);
    }

    @Override
    @Transactional
    public void setProductActiveStatus(Long productId, boolean isActive) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));
    
        product.setActive(isActive); // Actualizar el estado activo del producto
        productRepository.save(product);
    }
}