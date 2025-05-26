package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Loan;
import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;


    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findById(String id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));
    }

    public Loan insert(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan update(String id, Loan loan) {
        loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));

        loan.setActualReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        return loanRepository.save(loan);
    }

    public void delete(String id) {
        loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));
        loanRepository.deleteById(id);
    }

    private void updateLoanStatus(Loan loan) {
        if (loan.getStatus() == LoanStatus.ACTIVE && LocalDateTime.now().isAfter(loan.getExpectedReturnDate())) {
            loan.setStatus(LoanStatus.OVERDUE);
            loanRepository.save(loan);
        }
    }
}
