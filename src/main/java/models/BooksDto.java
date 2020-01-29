
package models;

import java.util.List;

import javax.persistence.ElementCollection;

import models.Book;

public class BooksDto {
	@ElementCollection
    public List<Book> books;

}

