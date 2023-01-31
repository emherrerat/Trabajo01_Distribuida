package com.distribuida.services;

import com.distribuida.db.Author;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
@ApplicationScoped
public class ServiceAuthorsImpl implements ServiceAuthor {
    private final String baseURL = "http://traefik/authors";

    @Override
    public List<Author> findAll() {

        Client client = ClientBuilder.newClient();
        Author[] authors= client.target(baseURL)
                .request()
                .header("Accept","application/json")
                .get(Author[].class);

        return Arrays.asList(authors);
    }


    @Override
    public void deleteAuthor(Long id) {

        Client client = ClientBuilder.newClient();
        client.target(baseURL+"/"+id).request().delete();
    }

    @Override
    public void createAuthor(Author author) {

        Client client = ClientBuilder.newClient();
        Entity<Author> entity = Entity.entity(author, MediaType.APPLICATION_JSON);
        client.target(baseURL).request().post(entity);
    }

    @Override
    public Author findById(Long id) {

        Client client = ClientBuilder.newClient();
        Author author = client.target(baseURL+"/"+id)
                .request(MediaType.APPLICATION_JSON)
                .get(Author.class);

        return author;
    }


    @Override
    public void updateAuthor(Long id, Author album) {

        Client client = ClientBuilder.newClient();
        Entity<Author> entity = Entity.entity(album, MediaType.APPLICATION_JSON);
        client.target(baseURL+"/"+id).request().put(entity);
    }

}
