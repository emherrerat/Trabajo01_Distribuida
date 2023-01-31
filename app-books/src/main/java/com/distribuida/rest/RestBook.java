package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/books")
public class RestBook {
    @Inject
    private BookService bookService;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> finAll(){
        return bookService.findAll();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(Book book){
        boolean resp= bookService.insert(book);
        if(resp){
            return Response.status(Response.Status.CREATED).entity("Creado").build();
        }
        return Response.status(Response.Status.CREATED).entity("No creado").build();
    }
    @DELETE
    @Path("/{id}")
    public Response  delete(@PathParam("id") int id) {
        bookService.delete(id);
        return Response.status((Response.Status.OK)).build();
    }
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update( @PathParam("id") int id,Book book) {
        bookService.update(id,book);
        return Response.status((Response.Status.OK)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Book finById(@PathParam("id") int id){
        return bookService.findById(id);
    }

}
