package com.Group3.JavaSpringExam.Loan;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  // At present this returns full details of the member in each loan.
  @GetMapping("/{memberNumber}")
  public ResponseEntity<List<Loan>> getActiveLoansByMemberNumber (@PathVariable Long memberNumber) {
    return new ResponseEntity<>(loanService.getActiveLoansByMember(memberNumber), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Loan>> getAllActiveLoans() {
    return new ResponseEntity<>(loanService.getAllActiveLoans(), HttpStatus.OK);
  }

}
