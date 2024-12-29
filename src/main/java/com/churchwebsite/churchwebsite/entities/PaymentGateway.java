package com.churchwebsite.churchwebsite.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_gateway")
public class PaymentGateway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_gateway_id")
    private int paymentGatewayId;

    @Column(name = "gateway_name", nullable = false)
    private String gatewayName;

    @Column(name = "api_key", nullable = false, columnDefinition = "TEXT")
    private String apiKey;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "PaymentGateway{" +
                "paymentGatewayId=" + paymentGatewayId +
                ", gatewayName='" + gatewayName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
