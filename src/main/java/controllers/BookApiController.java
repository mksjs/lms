
package controllers;

import models.BookDto;
import models.BookDtoNotLog;
import models.BooksDto;
import models.Book;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.PathParam;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.text.View;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.inject.Inject;
import dao.BookTo;
import etc.LoggedInUser;

public class BookApiController {

    @Inject
    BookTo bookTo;
    
    public Result getBooksJson() {

        BooksDto booksDto = bookTo.getAllBooks();
        
//        List<BookDto> booksDto2 = new ArrayList<>();
//        for (Book bookDto : booksDto.books) {
//			BookDto bookDto2 = new BookDto();
//			bookDto2.id = bookDto.id;
//			bookDto2.title = bookDto.title;
//			bookDto2.authorName = bookDto.authorName;
//			booksDto2.add(bookDto2);
//		}
        
        return Results.json().jsonView(View.Public.class).render(booksDto);
        
    }
    public Result getAllBooksLoggedInJson(@PathParam ("username") String username ) {
    	
    	boolean user = bookTo.userLogged(username);
    	BooksDto booksDto = bookTo.getAllBooks();
    	if(user) {
	    	
	    	
	    	return Results.json().render(booksDto);
    	}else {
    		
	    	
	    	return Results.json().jsonView(View.Public.class).render(booksDto);
    	}
    	
    }
    public Result getAllBookJson(@PathParam("id") Long id) {
        
        Book book = bookTo.getBook(id);
//        BookDto bookDto = new BookDto();
//        bookDto.id = book.id;
//        bookDto.title = book.title;
//        bookDto.authorName = book.authorName;
        return Results.json().render(book);
    
    }
    public Result getNBookJson(@PathParam ("NBook") int NBook) {
    	BooksDto  booksDto = bookTo.getFirstNBook(NBook);
    	 return Results.json().jsonView(View.Public.class).render(booksDto);
    	
    }
    public Result pagination(@PathParam ("page_id") int page_id, @PathParam ("nBook") int nBook) {
    	
    	if(page_id ==1) {
    		//do nothing
    	}else {
    		page_id = (page_id-1) * nBook +1;
    	}
    	
    	BooksDto booksDto =bookTo.getBookByPage(page_id,nBook);
    	return Results.json().jsonView(View.Public.class).render(booksDto);
    }
    
    public Result AddBookJson(@PathParam ("username") String username , @PathParam ("title") String title, @PathParam ("authorName")String authorName ) {
    	boolean success = bookTo.AddBook(username, title, authorName);
    	
    	if(success) {
    		Book book = bookTo.getFirstBookForFrontPage();
    		return Results.json().jsonView(View.Public.class).render(book);
    	}else {
    		return Results.json().render("Book Not added");
    	}
    	
    }
    public Result updataBookJson(@PathParam ("username") String username ,@PathParam ("id") Long id, @PathParam("title") String title, @PathParam ("authorName") String authorName) {
    	boolean success =bookTo.updateBookId(username, id, title, authorName);
    	if(success) {
    		Book book = bookTo.getBook(id);
    		return Results.json().jsonView(View.Public.class).render(book);
    	}else {
    		return Results.json().render("Not Updated");
    	}
    }
    
    public Result deleteBookIdJson(@PathParam ("username") String username, @PathParam ("id") Long id) {
    	boolean success = bookTo.deleteBook(username, id);
    	if(success) {
    		boolean user = bookTo.userLogged(username);
    		BooksDto booksDto = bookTo.getAllBooks();
        	if(user) {
    	    	
    	    	
    	    	return Results.json().render(booksDto);
        	}else {
        		
    	    	
    	    	return Results.json().jsonView(View.Public.class).render(booksDto);
        	}
    	}else {
    		return Results.json().render("Not Deleted");
    	}
    }
    public static class View {
    	public static class Public {};
    	public static class Private {};
    }
    
    
    
    
	/*
	 * public class Book{
	 * 
	 * @JsonView(View.Private.class) public Long id;
	 * 
	 * @JsonView(View.Public.class) public String title;
	 * 
	 * @JsonView(View.Public.class) public String authorName;
	 * 
	 * @JsonView(View.Private.class) public Boolean availablity; }
	 * 
	 */
    public Result getBooksXml() {

        BooksDto booksDto = bookTo.getAllBooks();

        return Results.xml().render(booksDto);

    }
   
    
    public Result getBookJson(@PathParam("id") Long id) {
    
        Book book = bookTo.getBook(id);
//        BookDto bookDto = new BookDto();
//        bookDto.id = book.id;
//        bookDto.title = book.title;
//        bookDto.authorName = book.authorName;
        return Results.json().jsonView(View.Public.class).render(book);
    
    }

    @FilterWith(SecureFilter.class)
    public Result postBookJson(@LoggedInUser String username,
                                  BookDto bookDto) {

        boolean succeeded = bookTo.postBook(username, bookDto);

        if (!succeeded) {
            return Results.notFound();
        } else {
            return Results.json();
        }

    }

    @FilterWith(SecureFilter.class)
    public Result postBookXml(@LoggedInUser String username,
                                 BookDto bookDto) {

        boolean succeeded = bookTo.postBook(username, bookDto);

        if (!succeeded) {
            return Results.notFound();
        } else {
            return Results.xml();
        }

    }
    @FilterWith(SecureFilter.class)
    public Result deleteBookJson(@LoggedInUser String username,
                                  BookDto bookDto,
                                  @PathParam("id") Long id) {

        boolean succeeded = bookTo.deleteBook(username, id);

        if (!succeeded) {
            return Results.notFound();
        } else {
            return Results.json();
        }

    }

    @FilterWith(SecureFilter.class)
    public Result deleteBookXml(@LoggedInUser String username,
                                 BookDto bookDto,
                                 @PathParam("id") Long id) {

        boolean succeeded = bookTo.deleteBook(username, id);

        if (!succeeded) {
            return Results.notFound();
        } else {
            return Results.xml();
        }

    }

}
