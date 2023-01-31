package com.distribuida.db;

import jakarta.ws.rs.Consumes;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Consumes("application/json")
public class Book {
    private int id;
    private Long author_id;
    private String isbn;
    private String title;
    private BigDecimal price;
}

