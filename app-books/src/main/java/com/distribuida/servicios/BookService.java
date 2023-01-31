package com.distribuida.servicios;

import com.distribuida.db.Book;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BookService {
    List<Book> findAll() ;
    Book findById(int id);
    boolean insert(Book book);
    boolean update(int id,Book book);
    boolean delete(int id);
}
