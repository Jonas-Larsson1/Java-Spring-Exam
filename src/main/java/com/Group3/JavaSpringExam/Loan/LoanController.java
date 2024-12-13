package com.Group3.JavaSpringExam.Loan;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

  private final LoanService loanService;

  @Autowired
  public LoanController(LoanService loanService) {
    this.loanService = loanService;
  }

  @PostMapping
  public ResponseEntity<Loan> createLoan(@RequestBody @Valid Loan loan) {
    Loan createdLoan = loanService.addLoan(loan);
    return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
  }

  @GetMapping("/{memberNumber}")
  public ResponseEntity<List<Loan>> getActiveLoansByMemberNumber (@PathVariable Long memberNumber) {
    return new ResponseEntity<>(loanService.getActiveLoansByMember(memberNumber), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('LIBRARIAN')")
  @GetMapping
  public ResponseEntity<List<Loan>> getAllActiveLoans() {
    return new ResponseEntity<>(loanService.getAllActiveLoans(), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('LIBRARIAN')")
  @PutMapping("/endloan/{loanId}")
  public ResponseEntity<Loan> endLoan(@PathVariable Long loanId) {
    return new ResponseEntity<>(loanService.endLoan(loanId), HttpStatus.OK);
  }

}
