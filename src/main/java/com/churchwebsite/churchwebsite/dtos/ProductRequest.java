package com.churchwebsite.churchwebsite.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private long amount;
    private Long quantity = 1L;
    private String name;
    private String currency;
}
