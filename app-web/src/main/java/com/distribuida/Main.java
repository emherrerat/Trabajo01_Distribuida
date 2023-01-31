package com.distribuida;

import com.distribuida.db.Author;
import com.distribuida.db.Book;
import com.distribuida.services.ServiceAuthor;
import com.distribuida.services.ServiceBook;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;

public class Main {
    static SeContainer container;

    public static void main(String[] args) {
        container = SeContainerInitializer.newInstance().initialize();
        rutasAuthor();
        rutasBooks();

    }

    public static void rutasAuthor(){
        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
        Instance<ServiceAuthor> obje = container.select(ServiceAuthor.class);
        ServiceAuthor servicioAuthor = obje.get();

        // Html list authors
        get("/authors", (req, res) -> {
                    List<Author> authors = servicioAuthor.findAll();
                    Map<String, Object> model = new HashMap<>();
                    model.put("authors", authors);
                    return engine.render(new ModelAndView(model, "Authors"));
                }
        );

        // CRUD delete author
        get("/authors/delete", (req, res) -> {
                    Long id = (long) Integer.parseInt(req.queryParams("id"));
                    servicioAuthor.deleteAuthor(id);
                    res.redirect("/authors");
                    return null;
                }
        );

        get("/authors/formularioInsertar", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    return engine.render(new ModelAndView(model, "FormInsertarAuthor"));
                }
        );

        post("/authors/add", (req, res) -> {
                    Author author = new Author();

                    String body = req.body();
                    String[] cadena = body.split("&");

                    System.out.println(body);

                    String[] datos = cadena[0].split("=");
                    author.setFirst_name(datos[1]);

                    datos = cadena[1].split("=");
                    author.setLast_name(datos[1]);




                    servicioAuthor.createAuthor(author);
                    res.redirect("/authors");
                    return null;
                }
        );

        get("/authors/formularioModificar", (req, res) -> {
                    Author author = new Author();
                    Long id = (long) Integer.parseInt(req.queryParams("id"));
                    System.out.println(id);
                    author = servicioAuthor.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("author", author);
                    return engine.render(new ModelAndView(model, "FormModificarAuthor"));
                }
        );


        post("/authors/modificar", (req, res) -> {
                    Author author = new Author();

                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");
                    author.setId(Long.valueOf(datos[1]));

                    datos = cadena[1].split("=");
                    author.setFirst_name(datos[1]);

                    datos = cadena[2].split("=");
                    author.setLast_name(datos[1]);

                    System.out.println(author.getFirst_name());
                    System.out.println(author.getLast_name());

                    servicioAuthor.updateAuthor(author.getId(),author);
                    res.redirect("/authors");
                    return null;
                }
        );
        get("/author", (req, res) -> {
                    long id = Integer.parseInt(req.queryParams("id"));
                    Author author = servicioAuthor.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("author", author);
                    return engine.render(new ModelAndView(model, "Author"));
                }
        );

    }

    public static void rutasBooks() {

        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
        Instance<ServiceBook> obj = container.select(ServiceBook.class);
        ServiceBook servicioBook = obj.get();


        get("/books", (req, res) -> {
                    List<Book> books = servicioBook.findAll();
                    Map<String, Object> model = new HashMap<>();
                    model.put("books", books);
                    return engine.render(new ModelAndView(model, "Books"));
                }
        );

        get("/books/delete", (req, res) -> {
                    int id = Integer.parseInt(req.queryParams("id"));
                    servicioBook.deleteBook(id);
                    res.redirect("/books");
                    return null;
                }
        );

        get("/books/formularioInsertar", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    return engine.render(new ModelAndView(model, "FormInsertarBook"));
                }
        );


        post("/books/add", (req, res) -> {
                    Book b = new Book();
                    int ano, mes, dia;
                    String body = req.body();
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");
                    b.setIsbn(datos[1]);

                    datos = cadena[1].split("=");
                    b.setTitle(datos[1]);

                    datos = cadena[2].split("=");
                    b.setPrice(BigDecimal.valueOf(Long.parseLong(datos[1])));


                    datos = cadena[3].split("=");
                    b.setAuthor_id(Long.valueOf(datos[1]));

                    servicioBook.createBook(b);
                    res.redirect("/books");
                    return null;
                }
        );

        get("/books/formularioModificar", (req, res) -> {
                    Book b = new Book();
                    int id = Integer.parseInt(req.queryParams("id"));
                    b = servicioBook.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("book", b);
                    return engine.render(new ModelAndView(model, "FormModificarBook"));
                }
        );


        post("book/modificar", (req, res) -> {
                    Book b = new Book();

                    String body = req.body();
                    String[] cadena = body.split("&");

                    System.out.println(body);


                    String[] datos = cadena[0].split("=");
                    b.setId(Integer.parseInt(datos[1]));

                    datos = cadena[1].split("=");
                    b.setIsbn(datos[1]);

                    datos = cadena[2].split("=");
                    b.setTitle(datos[1]);

                    datos = cadena[3].split("=");
                    b.setPrice(BigDecimal.valueOf(Long.parseLong(datos[1])));


                    datos = cadena[4].split("=");
                    b.setAuthor_id(Long.valueOf(datos[1]));

                    servicioBook.modBook(b.getId(), b);
                    res.redirect("/books");
                    return null;
                }
        );
    }
}
