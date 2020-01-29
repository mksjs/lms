package models;

import java.util.List;

import javax.persistence.ElementCollection;

import models.BookDto;

public class BookDtoNotLog{
	@ElementCollection
	public List<BookDto> bookDtoNotLog;
}