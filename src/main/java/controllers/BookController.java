package controllers;

import models.Book;
import models.User;
import models.BookDto;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.BookTo;
import dao.UserDao;
import etc.LoggedInUser;
import freemarker.core.InvalidReferenceException;

@Singleton
public class BookController {
    
    @Inject
    BookTo bookTo;
    UserDao userDao;

    ///////////////////////////////////////////////////////////////////////////
    // Show Book
    ///////////////////////////////////////////////////////////////////////////
    public Result bookShow(@PathParam("id") Long id,@LoggedInUser String username) throws InvalidReferenceException {

        Book book= null;

        if (id != null) {

            book = bookTo.getBook(id);

        }
//        User user = null;
//        if(username !=null) {
//        	user = userDao.getUser(username);
//        	System.out.println(user);
//        }
       Result result = Results.html();
       
        result.render("book",book);
       // result.render("user",user);
        return result;
        
    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    // add New Book
    ///////////////////////////////////////////////////////////////////////////
    @FilterWith(SecureFilter.class)
    public Result addNewBook() {

        return Results.html();

    }

    @FilterWith(SecureFilter.class)
    public Result bookNewPost(@LoggedInUser String username,
                                 Context context,
                                 @JSR303Validation BookDto bookDto,
                                 Validation validation) {

        if (validation.hasViolations()) {

            context.getFlashScope().error("Please fill correct field.");
            context.getFlashScope().put("title", bookDto.title);
            context.getFlashScope().put("content", bookDto.authorName);

            return Results.redirect("/book/new");

        } else {
            
            boolean isDone = bookTo.postBook(username, bookDto);
            if(isDone)
            	context.getFlashScope().success("New book added.");
            else {
            	context.getFlashScope().error("Sorry! Book not Added Reason : either you are not logged In or not Admin ");
            }
            return Results.redirect("/");

        }

    }
///////////////////////////////////////////////////////////////////////////
// delete Book
///////////////////////////////////////////////////////////////////////////
    @FilterWith(SecureFilter.class)
    public Result deleteBook() {

        return Results.html().template("views/BookController/deleteBook.ftl.html");

    }
    
    @FilterWith(SecureFilter.class)
    public Result deleteBookById(@LoggedInUser String username,
                                 Context context,
                                 @JSR303Validation BookDto bookDto,
                                 @PathParam("id") Long id,
                                 Validation validation) {

        if (validation.hasViolations()) {

            context.getFlashScope().error("Please fill correct field.");
            context.getFlashScope().put("title", bookDto.title);
            context.getFlashScope().put("content", bookDto.authorName);

            return Results.redirect("/book/delete");

        } else {
            
            boolean isDone = bookTo.deleteBook(username, id);
            if(isDone) {
            	context.getFlashScope().success("Book deleted");
            	return Results.redirect("/");
            }
            else { 
            	context.getFlashScope().error("Sorry! Book not deleted Reason: either You are not Logged In or not Admin");
            	return Results.redirect("/");
            }
            

        }

    }
///////////////////////////////////////////////////////////////////////////
//update Book
///////////////////////////////////////////////////////////////////////////
    public Result updateBook(@PathParam("id") Long id) {

        Book book= null;

        if (id != null) {

            book = bookTo.getBook(id);
            
        }
        return Results.html().render("book", book);

        

    }
   
    
    @FilterWith(SecureFilter.class)
    public Result updateBookById(@LoggedInUser String username,
                                 Context context,
                                 @JSR303Validation BookDto bookDto,@PathParam("id") Long id,
                                 Validation validation) {

        
    	 if (validation.hasViolations()) {

             context.getFlashScope().error("Please fill correct field.");
             context.getFlashScope().put("title", bookDto.title);
             context.getFlashScope().put("content", bookDto.authorName);

             return Results.redirect("/");

         } else {
      
            boolean isDone = bookTo.updateBook(username, bookDto,id);
            if(isDone) {
            	context.getFlashScope().success("Book updated");
            	return Results.redirect("/");
            }
            else { 
            	context.getFlashScope().error("Sorry! Book not updated Reason: either You are not Logged In or not Admin");
            	return Results.redirect("/");
            }
         }
    }
}