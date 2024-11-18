package com.Group3.JavaSpringExam.Book;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMemberDTO {
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private List<String> genre;
    private Year publicationYear;
    private boolean available;

}
