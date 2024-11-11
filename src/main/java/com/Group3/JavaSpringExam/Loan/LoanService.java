package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.Member.Member;
import com.Group3.JavaSpringExam.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

  private final LoanRepository loanRepository;
  private final MemberRepository memberRepository;


  @Autowired
  public LoanService(LoanRepository loanRepository, MemberRepository memberRepository) {
    this.loanRepository = loanRepository;
      this.memberRepository = memberRepository;
  }

  public Loan addLoan(Loan loan) {
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

}
