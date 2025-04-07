package com.github.gabrielvba.ms_order_management.domain.model;

public enum PaymentStatus {
	PENDING,      // Aguardando pagamento
    COMPLETED,    // Pagamento confirmado
    FAILED,       // Falha no pagamento
    CANCELED,     // Pagamento cancelado
    REFUNDED;     // Pagamento estornado

}
