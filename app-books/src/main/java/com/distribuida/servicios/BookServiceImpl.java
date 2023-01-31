package com.distribuida.servicios;

import com.distribuida.db.Book;
import io.helidon.common.reactive.Single;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@ApplicationScoped
public class BookServiceImpl implements BookService {
    @Inject
    private DbClient dbClient;
    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try {
            String query= "SELECT * FROM books";
            Single<List<DbRow>> s= dbClient.execute(exec -> exec
                    .createQuery(query)
                    .execute()).collectList();
            List<DbRow> rs = s.get();
            if(!rs.isEmpty()){
                for(DbRow r : rs){
                    Book book=new Book();
                    book.setId((int) r.column("id").value());
                    book.setAuthor_id((int) r.column("author_id").value());
                    book.setIsbn((String) r.column("isbn").value());
                    book.setTitle((String) r.column("title").value());
                    book.setPrice((BigDecimal) r.column("price").value());
                    list.add(book);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book findById(int id)  {
        Book book = null;
        try{
            String query="SELECT * FROM books WHERE id =?";
            book = dbClient.execute(exec -> exec
                            .createQuery(query)
                            .params(id)
                            .execute()).first()
                    .map(rs -> Book
                            .builder()
                            .id(rs.column("id").as(Integer.class))
                            .author_id(rs.column("author_id").as(Integer.class))
                            .isbn(rs.column("isbn").as(String.class))
                            .title(rs.column("title").as(String.class))
                            .price(rs.column("price").as(BigDecimal.class))
                            .build())
                    .get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public boolean insert(Book book) {
        boolean resp=false;
        Long filas= Long.valueOf(0);
        try{
            String query=  "INSERT INTO books (author_id,isbn,title,price) VALUES (?, ?, ? ,?) RETURNING id";
          //String query="INSERT INTO books (author_id,isbn,title,price) VALUES ( ? , ?, ? , ? )";
          Single<Long> s=dbClient.execute(exec -> exec
                  .createInsert(query)
                  .params(book.getAuthor_id(),book.getIsbn(),book.getTitle(),book.getPrice())
                  .execute());
          filas=s.get();
          resp=filas.intValue()>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public boolean update(int id,Book book) {
        boolean resp =false;
        Long filas = Long.valueOf(0);
        try{
            String query="UPDATE books SET isbn = ?, author_id = ? , title = ? , price = ? WHERE id = ?";
            Single<Long> s= dbClient.execute(exec->exec.createUpdate(query)
                    .params(book.getIsbn(),book.getAuthor_id() , book.getTitle(), book.getPrice(),id)
                    .execute());
            filas=s.get();
            resp=filas.intValue()>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public boolean delete(int id) {
        try {
            String query="DELETE FROM books WHERE id = ?";
            dbClient.execute(exec -> exec.delete(query, id))
                    .get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
