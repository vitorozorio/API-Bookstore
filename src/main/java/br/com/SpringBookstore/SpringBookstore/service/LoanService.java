package br.com.SpringBookstore.SpringBookstore.service;

import br.com.SpringBookstore.SpringBookstore.domain.Book;
import br.com.SpringBookstore.SpringBookstore.domain.Loan;
import br.com.SpringBookstore.SpringBookstore.domain.User;
import br.com.SpringBookstore.SpringBookstore.domain.DTO.LoanDTO;
import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import br.com.SpringBookstore.SpringBookstore.exception.BusinessLogicException;
import br.com.SpringBookstore.SpringBookstore.exception.ConflictException;
import br.com.SpringBookstore.SpringBookstore.repository.BookRepository;
import br.com.SpringBookstore.SpringBookstore.repository.LoanRepository;
import br.com.SpringBookstore.SpringBookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Valida os campos obrigatórios do empréstimo
    protected void validateLoanFields(LoanDTO dto) {
        if (dto.getUserId() == null || dto.getUserId().trim().isEmpty()) {
            throw new BusinessLogicException("O ID do usuário é obrigatório.");
        }
        if (dto.getBookId() == null || dto.getBookId().trim().isEmpty()) {
            throw new BusinessLogicException("O ID do livro é obrigatório.");
        }
    }

    // Converte entidade Loan para DTO
    private LoanDTO toDTO(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getLoanDate(),
                loan.getExpectedReturnDate(),
                loan.getActualReturnDate(),
                loan.getStatus(),
                loan.getUser().getId(),
                loan.getBook().getId()
        );
    }

    // Lista todos os empréstimos
    public List<LoanDTO> findAll() {
        return loanRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca um empréstimo pelo ID
    public LoanDTO findById(String id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));
        updateLoanStatus(loan); // Atualiza status caso esteja atrasado
        return toDTO(loan);
    }

    // Cria um novo empréstimo (aluguel de livro)
    public LoanDTO insert(LoanDTO dto) {
        validateLoanFields(dto);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessLogicException("Usuário não encontrado."));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado."));

        Optional<Loan> existingLoan = loanRepository.findByBookIdAndStatus(dto.getBookId(), LoanStatus.ACTIVE);
        if (existingLoan.isPresent()) {
            throw new ConflictException("O livro já está emprestado.");
        }

        Loan loan = new Loan(
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                LoanStatus.ACTIVE,
                user,
                book
        );

        Loan savedLoan = loanRepository.save(loan);
        return toDTO(savedLoan);
    }

    // Finaliza um empréstimo (devolução do livro)
    public LoanDTO update(String id, LoanDTO dto) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new BusinessLogicException("O livro já foi devolvido.");
        }

        loan.setActualReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        Loan updatedLoan = loanRepository.save(loan);
        return toDTO(updatedLoan);
    }

    // Remove um empréstimo pelo ID
    public void delete(String id) {
        loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));
        loanRepository.deleteById(id);
    }

    // Atualiza o status do empréstimo caso esteja atrasado
    private void updateLoanStatus(Loan loan) {
        if (loan.getStatus() == LoanStatus.ACTIVE && LocalDateTime.now().isAfter(loan.getExpectedReturnDate())) {
            loan.setStatus(LoanStatus.OVERDUE);
            loanRepository.save(loan);
        }
    }
}
