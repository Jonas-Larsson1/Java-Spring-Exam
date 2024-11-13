package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.Book.Book;
import com.Group3.JavaSpringExam.Book.BookRepository;
import com.Group3.JavaSpringExam.Member.Member;
import com.Group3.JavaSpringExam.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanService {

  private final LoanRepository loanRepository;
  private final MemberRepository memberRepository;
  private final BookRepository bookRepository;


  @Autowired
  public LoanService(LoanRepository loanRepository, MemberRepository memberRepository, BookRepository bookRepository) {
    this.loanRepository = loanRepository;
      this.memberRepository = memberRepository;
      this.bookRepository = bookRepository;
  }

  public Loan addLoan(Loan loan) {
    Long bookId = loan.getBook().getId();
    Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found."));
    if (!book.isAvailable()) {
      throw new NoSuchElementException("Book is not available");
    }
    book.setAvailable(false);
    bookRepository.save(book);
    loan.setLoanDate(LocalDate.now());
    return loanRepository.save(loan);
  }

  public List<Loan> getActiveLoansByMember(Long memberNumber) {
    Member member = memberRepository.findByMemberNumber(memberNumber);
    List<Loan> loans = member.getLoans();
    loans.removeIf(loan -> loan.getReturnedDate() != null);
    return loans;
  }

  public List<Loan> getAllActiveLoans() {
    List<Loan> loans = loanRepository.findAll();
    loans.removeIf(loan -> loan.getReturnedDate() != null);
    return loans;
  }

  public Loan endLoan(Long loanId) {
    Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new NoSuchElementException("Loan not found."));
    Book book = bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new NoSuchElementException("Book not found."));
    loan.setReturnedDate(LocalDate.now());
    book.setAvailable(true);
    bookRepository.save(book);
    return loanRepository.save(loan);
  }
}