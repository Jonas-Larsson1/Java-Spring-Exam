package com.Group3.JavaSpringExam.Loan;

import com.Group3.JavaSpringExam.Member.Member;
import com.Group3.JavaSpringExam.Member.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoanControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MemberRepository memberRepository;

  private long memberNumber;

  @BeforeEach
  void setUp() {
    seedDatabase();
  }

  void seedDatabase() {
//    Arrange
    Member member = new Member();
    member.setFirstName("Ian");

    Loan activeLoan1 = new Loan();
    activeLoan1.setLoanDate(LocalDate.now());
    activeLoan1.setDueDate(LocalDate.now().plusDays(14));
    activeLoan1.setReturnedDate(null);
    activeLoan1.setMember(member);

    Loan activeLoan2 = new Loan();
    activeLoan2.setLoanDate(LocalDate.now());
    activeLoan2.setDueDate(LocalDate.now().plusDays(2));
    activeLoan2.setReturnedDate(null);
    activeLoan2.setMember(member);

    Loan returnedLoan = new Loan();
    returnedLoan.setLoanDate(LocalDate.now().minusDays(30));
    returnedLoan.setDueDate(LocalDate.now().minusDays(15));
    returnedLoan.setReturnedDate(LocalDate.now().minusDays(10));
    returnedLoan.setMember(member);

    List<Loan> loans = new ArrayList<>();
    loans.add(activeLoan1);
    loans.add(activeLoan2);
    loans.add(returnedLoan);

    member.setLoans(loans);

    Member savedMember = memberRepository.save(member);

    this.memberNumber = savedMember.getMemberNumber();
  }

  @Test
  void getActiveLoansByMemberNumber() throws Exception {
//    Act
    mockMvc.perform(get("/loan/" + memberNumber))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].dueDate").value(LocalDate.now().plusDays(14).toString()))
        .andExpect(jsonPath("$[0].returnedDate").isEmpty());

  }
}
