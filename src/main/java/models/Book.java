
package models;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import controllers.BookApiController.View;


@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	/*
//	 * public Long id;
//	 * 
//	 * public String title;
//	 * 
//	 * public String authorName;
//	 * 
//	 * public boolean availablity;
//	 */
	
	@JsonView(View.Public.class)
	public Long id;
	@JsonView(View.Public.class)
	public String title;
	@JsonView(View.Public.class)
	public String authorName;
	@JsonView(View.Private.class)
	public Boolean availablity;
	
//	public List<BookDto> books;
//	
//
//	public List<BookDto> getBooks() {
//		return books;
//	}
//
//	public void setBooks(List<BookDto> books) {
//		this.books = books;
//	}

	public Book() {
	}

	public Book(String authorName, String title) {
		this.authorName = authorName;
		this.title = title;
		this.availablity = true;
	}

}