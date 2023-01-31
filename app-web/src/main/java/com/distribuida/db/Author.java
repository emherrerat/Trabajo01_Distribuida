package com.distribuida.db;

import jakarta.ws.rs.Consumes;
import lombok.Data;

@Data
@Consumes("application/json")
public class Author {
    private Long id;
    private String first_name;
    private String last_name;
}

