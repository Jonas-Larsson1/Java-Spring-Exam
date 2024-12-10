package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.User.Member;
import com.Group3.JavaSpringExam.User.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private LoanService loanService;

  @Test
  void getActiveLoansByMember() {
//    Arrange
//    Mocka en member
    Member member = new Member();
    Long memberNumber = 10000001L;

    member.setMemberNumber(memberNumber);

//    Mocka några lån
    Loan loan1 = new Loan();
    loan1.setReturnedDate(null); // Aktivt lån

    Loan loan2 = new Loan();
    loan2.setReturnedDate(null); // Aktivt lån

    Loan loan3 = new Loan();
    loan3.setReturnedDate(LocalDate.now()); // Återlämnat lån

    List<Loan> loans = new ArrayList<>();
    loans.add(loan1);
    loans.add(loan2);
    loans.add(loan3);

    member.setLoans(loans);

    when(memberRepository.findByMemberNumber(memberNumber)).thenReturn(member);

//    Act
    List<Loan> activeLoans = loanService.getActiveLoansByMember(memberNumber);

//    Assert
    assertEquals(2, activeLoans.size());
    assertEquals(loan1, activeLoans.get(0));
    assertEquals(loan2, activeLoans.get(1));

    verify(memberRepository, times(1)).findByMemberNumber(memberNumber);
  }
}