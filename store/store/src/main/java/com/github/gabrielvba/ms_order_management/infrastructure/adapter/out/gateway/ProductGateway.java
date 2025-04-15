package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.gateway;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ProductGateway implements ProductServicePort {

    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${products.url}")
    private String productsUrl;
    
    @PostConstruct
    public void init() {
        log.info("Products URL: {}", productsUrl);
    }
    
    @Override
    public List<ProductDTO> getProducts(List<Long> externalProductIdList) {
        // Monta a query string com os ids separados por vírgula
        String idsQuery = externalProductIdList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String url = productsUrl + "?ids=" + idsQuery;
        log.info(url);
        // Fazendo a chamada e esperando uma lista de ProductDTO
        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(url, ProductDTO[].class);

        return Arrays.asList(response.getBody());
    }

    public String callApi() {
        ResponseEntity<String> response = restTemplate.getForEntity(productsUrl, String.class);
        return response.getBody();
    }
}
