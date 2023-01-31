package com.distribuida.db;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String isbn;
    private String title;
    private BigDecimal price;
    private int author_id;



}
