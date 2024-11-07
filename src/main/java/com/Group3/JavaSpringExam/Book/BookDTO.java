package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Author.Author;
import com.Group3.JavaSpringExam.Genre.Genre;
import lombok.Data;

import java.util.List;

@Data
public class BookDTO {

    private Book book;
    private Author author;
    private List<Genre> genres;

    public Book returnCompleteBook(){
        book.setAuthor(author);
        book.setGenres(genres);
        return book;
    }
}
