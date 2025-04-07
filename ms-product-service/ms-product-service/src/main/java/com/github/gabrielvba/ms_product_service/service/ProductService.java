package com.github.gabrielvba.ms_product_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.gabrielvba.ms_product_service.dto.ProductDTO;
import com.github.gabrielvba.ms_product_service.model.Product;
import com.github.gabrielvba.ms_product_service.repository.ProductRepository;
import com.github.gabrielvba.ms_product_service.util.ProductConverter;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

    // Injeção de dependência via construtor
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductConverter.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductConverter.toDTO(savedProduct);
    }

    public List<ProductDTO> listProducts() {
        List<Product> products = productRepository.findAll();
        return ProductConverter.toDTOList(products);
    }

    public Optional<ProductDTO> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return Optional.ofNullable(ProductConverter.toDTO(product.get()));
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) throws Exception {
        if (!productRepository.existsById(id)) {
            throw new Exception("Produto com ID " + id + " não encontrado.");
        }
        Product product = ProductConverter.toEntity(productDTO);
        Product updatedProduct = productRepository.save(product);
        return ProductConverter.toDTO(updatedProduct);
    }

    public boolean deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }

}
