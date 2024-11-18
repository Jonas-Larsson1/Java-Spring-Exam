package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import lombok.Data;



@Data
public class BookDTO {

    private Book book;
    private Author author;

    public Book returnCompleteBook(){
        book.setAuthor(author);
        return book;
    }
}
