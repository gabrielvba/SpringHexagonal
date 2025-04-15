package com.github.gabrielvba.ms_product_service.util;

import java.util.List;
import java.util.stream.Collectors;

import com.github.gabrielvba.ms_product_service.dto.ProductDTO;
import com.github.gabrielvba.ms_product_service.model.Product;

public class ProductConverter {

    // Converte ProductDTO para Product
    public static Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(dto.productId());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setUnitPrice(dto.unitPrice());
        product.setBarcode(dto.barcode());
        product.setWeight(dto.weight());
        product.setDimensions(dto.dimensions());
        product.setCategory(dto.category());
        product.setBrand(dto.brand());
        product.setManufactureDate(dto.manufactureDate());
        product.setExpiryDate(dto.expiryDate());
        product.setSupplier(dto.supplier());
//        product.setImages(dto.images());
//        product.setReviews(dto.reviews());
        return product;
    }

    // Converte Product para ProductDTO
    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDTO(
            product.getProductId(),
            product.getName(),
            product.getDescription(),
            product.getUnitPrice(),
            product.getBarcode(),
            product.getWeight(),
            product.getDimensions(),
            product.getCategory(),
            product.getBrand(),
            product.getManufactureDate(),
            product.getExpiryDate(),
//            product.getImages(),
//            product.getReviews(),
            product.getSupplier()
        );
    }

    // Converte uma lista de ProductDTO para uma lista de Product
    public static List<Product> toEntityList(List<ProductDTO> dtoList) {
        return dtoList.stream()
                      .map(ProductConverter::toEntity)
                      .collect(Collectors.toList());
    }

    // Converte uma lista de Product para uma lista de ProductDTO
    public static List<ProductDTO> toDTOList(List<Product> productList) {
        return productList.stream()
                          .map(ProductConverter::toDTO)
                          .collect(Collectors.toList());
    }
}
