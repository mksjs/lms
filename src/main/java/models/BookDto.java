package models;

import javax.validation.constraints.Size;

public class BookDto {

	public Long id;
    @Size(min = 2)
    public String title;
    
    @Size(min = 2)
    public String authorName;
    
    public BookDto() {}

}
