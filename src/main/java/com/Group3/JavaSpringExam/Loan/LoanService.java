package com.Group3.JavaSpringExam.Loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

  private final LoanRepository loanRepository;

  @Autowired
  public LoanService(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
  }

  public ResponseEntity<Loan> addLoan(Loan loan) {
    Loan newLoan = loanRepository.save(loan);
    return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
  }

}
