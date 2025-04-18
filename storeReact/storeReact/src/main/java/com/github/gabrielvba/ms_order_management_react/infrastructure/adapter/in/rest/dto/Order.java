package com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import com.github.gabrielvba.ms_order_management_react.domain.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Long clienteId;
    private List<Product> itens;
    private BigDecimal total;
    private String status; // Pendente, Processando, Concluído, Cancelado
    private String enderecoEntrega;
    private String formaPagamento;
}
