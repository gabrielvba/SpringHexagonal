package com.github.gabrielvba.ms_order_management_react.domain.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "orders") // OU outro nome válido
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    private List<ProductItem> itens;
    private BigDecimal total;
    private String status; // Pendente, Processando, Concluído, Cancelado
    private String enderecoEntrega;
    private String formaPagamento;
	
	
}
