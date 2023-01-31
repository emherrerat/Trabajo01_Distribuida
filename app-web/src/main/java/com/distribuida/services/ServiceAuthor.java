package com.distribuida.services;

import com.distribuida.db.Author;
import java.util.List;

public interface ServiceAuthor {

    List<Author> findAll();
    Author findById(Long id);
    void deleteAuthor(Long id);
    void updateAuthor(Long id, Author author);
    void createAuthor( Author author);

}

