package com.Group3.JavaSpringExam.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> findByName(Author newAuthor) {

        List<Author> allAuthors = authorRepository.findAll();
        return allAuthors.stream()
                .filter(author -> newAuthor.getFirstName().equalsIgnoreCase(author.getFirstName())
                        && newAuthor.getLastName().equalsIgnoreCase(author.getLastName()))
                .findFirst();
    }
}
