package com.Group3.JavaSpringExam.Book;

import com.Group3.JavaSpringExam.Loan.LoanDTO;
import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
public class BookAdminDTO {
    private long id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private List<String> genres;
    private Year publicationYear;
    private List<LoanDTO> loans;
    private boolean available;
}
