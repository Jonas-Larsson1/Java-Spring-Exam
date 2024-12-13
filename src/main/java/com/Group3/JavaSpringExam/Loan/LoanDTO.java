package com.Group3.JavaSpringExam.Loan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private String borrowerName;
    private String borrowerEmail;
    private Long borrowerMemberNumber;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.returnedDate = loan.getReturnedDate();
        if (loan.getUser() != null) {
            this.borrowerName = loan.getUser().getFirstName() + " " + loan.getUser().getLastName();
            this.borrowerEmail = loan.getUser().getEmail();
            this.borrowerMemberNumber = loan.getUser().getMemberNumber();
        }
    }
}
