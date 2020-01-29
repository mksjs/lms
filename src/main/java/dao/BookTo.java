
package dao;

import java.util.List;
import java.lang.Object;
import javax.persistence.ElementCollection;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import javax.persistence.TypedQuery;

import models.Article;
import models.ArticlesDto;
import models.Book;
import models.BookDto;
import models.BookDtoNotLog;
import models.BooksDto;
import models.User;
import models.UsersDto;
import ninja.jpa.UnitOfWork;
import ninja.params.PathParam;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;


public class BookTo {
   
    @Inject
    Provider<EntityManager> entitiyManagerProvider;
    @ElementCollection
    List<Book> books;
    
    @UnitOfWork
    public BooksDto getAllBooks() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x", Book.class);
        List<Book> books = q.getResultList();        
        BooksDto booksDto = new BooksDto();
        booksDto.books = books;
        
        return booksDto;
        
    }
    
    
    @Transactional
    public boolean userLogged(@PathParam ("username") String username) {
    	EntityManager entityManager = entitiyManagerProvider.get();
    	TypedQuery<User> U = entityManager.createQuery("SELECT x FROM User x WHERE x.username = :usernameParam", User.class );
    	User user = U.setParameter("usernameParam", username).getSingleResult();
    	if(user ==null) {
    		return false;
    	}
    	return true;
    }
    //@UnitOfWork
    @Transactional
    public Book getFirstBookForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x ORDER BY x.id DESC", Book.class);
        Book book = q.setMaxResults(1).getSingleResult();      
        
        return book;
        
        
    }
    
   // @UnitOfWork
    @Transactional
    public List<Book> getOlderBooksForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x ORDER BY x.id DESC ", Book.class);
        List<Book> books = q.setFirstResult(1).setMaxResults(10).getResultList();            
        
        return books;
        
        
    }
    
    @Transactional
    public BooksDto getFirstNBook(@PathParam ("NBook") int  NBook) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x ORDER BY x.id DESC ", Book.class);
        List<Book> books = q.setFirstResult(1).setMaxResults(NBook).getResultList();   
        BooksDto booksDto = new BooksDto();
        booksDto.books = books;
        
        
        return booksDto;
        
        
    }
    
    
    
    @Transactional
    public BooksDto getBookByPage(int  page_id, int NBook) {
        
    	
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x ORDER BY x.id DESC ", Book.class);
        List<Book> books = q.setFirstResult(page_id).setMaxResults(NBook).getResultList();   
        BooksDto booksDto = new BooksDto();
        booksDto.books = books;
        
        
        return booksDto;
        
        
    }
    
    @Transactional
    public List<Book> getBookById(int  page_id) {
        
    	int NBook =10;
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x ORDER BY x.id DESC ", Book.class);
        List<Book> books = q.setFirstResult(page_id).setMaxResults(NBook).getResultList();   
        
        return books;
        
        
    }
    
    //@UnitOfWork
    @Transactional
    public Book getBook(Long id) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Book> q = entityManager.createQuery("SELECT x FROM Book x WHERE x.id = :idParam", Book.class);
        Book book = q.setParameter("idParam", id).getSingleResult();        
        
        return book;
        
        
    }
    
    /**
     * Returns false if user cannot be found in database.
     */
    @Transactional
    public boolean postBook(@PathParam("username") String username, BookDto bookDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }else{
//        	TypedQuery<User> query = entityManager.createQuery("SELECT x FROM User x WHERE isAdmin = :isAdminParam", User.class);
//            User isAdmin = query.setParameter("isAdminParam", true).getSingleResult();
            if(user.isAdmin == false) {
            	return false;
            }
            Book book = new Book( bookDto.authorName, bookDto.title);
            entityManager.persist(book);
            entityManager.setFlushMode(FlushModeType.COMMIT);
            
        }
        
        
        
        return true;
        
    }
    
    
    
    @Transactional
    public boolean AddBook(@PathParam("username") String username, @PathParam("title") String title, @PathParam ("authorName") String authorName) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }else{
//        	TypedQuery<User> query = entityManager.createQuery("SELECT x FROM User x WHERE isAdmin = :isAdminParam", User.class);
//            User isAdmin = query.setParameter("isAdminParam", true).getSingleResult();
            if(user.isAdmin == false) {
            	return false;
            }
            Book book = new Book( authorName, title);
            entityManager.persist(book);
            entityManager.setFlushMode(FlushModeType.COMMIT);
            
        }
        
        
        
        return true;
        
    }
   
    @Transactional
    public boolean deleteBook(String username, Long id) {//BookDto bookDto ,
    	EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }else{
//        	TypedQuery<User> query = entityManager.createQuery("SELECT x FROM User x WHERE isAdmin = :isAdminParam", User.class);
//            User isAdmin = query.setParameter("isAdminParam", true).getSingleResult();
            if(user.isAdmin == false) {
            	return false;
            }
            Query delq = entityManager.createQuery("DELETE  FROM Book x WHERE x.id = :idParam ");
            delq.setParameter("idParam", id);
           
//            Query delq = entityManager.createQuery("DELETE  FROM Book x WHERE x.title = :titleParam and x.authorName =:authorParam ");
//            delq.setParameter("titleParam", bookDto.title);
//            delq.setParameter("authorParam", bookDto.authorName);
            //entityManager.getTransaction().commit();
            //entityManager.persist(delq);
            int result = delq.executeUpdate();
            System.out.println(result);
            if(result <=0) {
            	return false;
            }
             
        }
    	return true;
    }
    
    @Transactional
    public boolean updateBookId(@PathParam("username") String username ,@PathParam("id") Long id, @PathParam ("title") String title,@PathParam("authorName") String authorName) {
    	EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }else{

            if(user.isAdmin == false) {
            	return false;
            }
      
            if(title !=null  && authorName !=null ) {
            	Query updateq = entityManager.createQuery("UPDATE Book  x SET x.title = '"+ title +"' , x.authorName = '"+ authorName+"' WHERE x.id = '"+id+"'");
            
            int result = updateq.executeUpdate();
            
            if(result <=0) {
            	return false;
            }
             
        	}else {
        		return false;
        	}
        }
    	return true;
    }
    
    
    
    @Transactional
    public boolean updateBook(String username, BookDto bookDto ,Long id) {
    	EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<User> q = entityManager.createQuery("SELECT x FROM User x WHERE username = :usernameParam", User.class);
        User user = q.setParameter("usernameParam", username).getSingleResult();
        
        if (user == null) {
            return false;
        }else{

            if(user.isAdmin == false) {
            	return false;
            }

            System.out.println(id);
            System.out.println(bookDto.title);
            System.out.println(bookDto.authorName);
            if(bookDto.title !=null  && bookDto.authorName !=null ) {
            	Query updateq = entityManager.createQuery("UPDATE Book  x SET x.title = '"+bookDto.title+"' , x.authorName = '"+bookDto.authorName+"' WHERE x.id = '"+id+"'");
            
           

            int result = updateq.executeUpdate();
            System.out.println(result);
            if(result <=0) {
            	return false;
            }
             
        	}else {
        		return false;
        	}
        }
    	return true;
    }


}
