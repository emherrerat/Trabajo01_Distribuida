package com.distribuida.rest;

import com.distribuida.db.Authors;
import com.distribuida.servicios.AuthorRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AuthorRest {
    @Inject
    AuthorRepository repository;
    @GET
    public List<Authors> getAll() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Authors get(@PathParam("id") Long id) {
        return repository.findById(id);
    }
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
    @POST
    @Path("/add")
    public void add(Authors authors) {
        authors.isPersistent();
        repository.persist(authors);
    }
    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") Long id, Authors authors) {
        authors.isPersistent();
        Authors existing = repository.findById(id);
        existing.setFirst_name(authors.getFirst_name());
        existing.setLast_name(authors.getLast_name());
        repository.persist(existing);
    }
}
