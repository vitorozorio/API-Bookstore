package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.DTO.LoanDTO;
import br.com.SpringBookstore.SpringBookstore.domain.Loan;
import br.com.SpringBookstore.SpringBookstore.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public LoanDTO findById(String id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com o ID: " + id));

        // Converter entidade para DTO
        return new LoanDTO(
                loan.getId(),
                loan.getLoanDate(),
                loan.getExpectedReturnDate(),
                loan.getActualReturnDate(),
                loan.getStatus(),
                loan.getUser().getId(), // Apenas o ID do usuário
                loan.getBook().getId()  // Apenas o ID do livro
        );
    }
}
