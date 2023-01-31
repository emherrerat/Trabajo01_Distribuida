package com.distribuida.services;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ServiceBookImpl implements ServiceBook{
    private final String baseURL = "http://traefik/books";
    @Override
    public List<Book> findAll() {

        Client client = ClientBuilder.newClient();
        Book[] singers= client.target(baseURL)
                .request()
                .header("Accept","application/json")
                .get(Book[].class);

        return Arrays.asList(singers);
    }

    @Override
    public Book findById(int id) {

        Client client = ClientBuilder.newClient();
        Book singer= client.target(baseURL+"/"+id)
                .request(MediaType.APPLICATION_JSON)
                .get(Book.class);
        return singer;
    }

    @Override
    public void deleteBook(int id) {

        Client client = ClientBuilder.newClient();
        client.target(baseURL+"/"+id).request().delete();
    }

    @Override
    public void modBook(int id, Book singer) {

        Client client = ClientBuilder.newClient();
        Entity<Book> entity = Entity.entity(singer, MediaType.APPLICATION_JSON);
        client.target(baseURL+"/"+id).request().put(entity);
    }

    @Override
    public void createBook(Book singer) {

        Client client = ClientBuilder.newClient();
        Entity<Book> entity = Entity.entity(singer, MediaType.APPLICATION_JSON);
        client.target(baseURL).request().post(entity);
    }
}
