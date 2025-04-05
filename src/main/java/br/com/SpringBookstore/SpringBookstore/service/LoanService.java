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

    // Método de validação de campos obrigatórios
    protected void validateLoanFields(LoanDTO dto) {
        if (dto.getUserId() == null || dto.getUserId().trim().isEmpty()) {
            throw new BusinessLogicException("O ID do usuário é obrigatório.");
        }
        if (dto.getBookId() == null || dto.getBookId().trim().isEmpty()) {
            throw new BusinessLogicException("O ID do livro é obrigatório.");
        }
    }

    // Método auxiliar para converter entidade para DTO
    private LoanDTO toDTO(Loan loan) {
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

    // Buscar todos os empréstimos e retornar como DTO
    public List<LoanDTO> findAll() {
        return loanRepository.findAll().stream()
                .map(this::toDTO) // Converte cada entidade para DTO
                .collect(Collectors.toList());
    }

    // Buscar um empréstimo pelo ID e retornar como DTO
    public LoanDTO findById(String id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));
        updateLoanStatus(loan); // Atualiza o status do empréstimo (verifica atraso)
        return toDTO(loan);
    }

    // Inserir um novo empréstimo (alugar um livro)
    public LoanDTO insert(LoanDTO dto) {
        // Validação dos campos obrigatórios
        validateLoanFields(dto);

        // Buscar o usuário no banco pelo ID informado no DTO
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessLogicException("Usuário não encontrado."));

        // Buscar o livro no banco pelo ID informado no DTO
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BusinessLogicException("Livro não encontrado."));

        // Verificar se o livro já está emprestado
        Optional<Loan> existingLoan = loanRepository.findByBookIdAndStatus(dto.getBookId(), LoanStatus.ACTIVE);
        if (existingLoan.isPresent()) {
            throw new ConflictException("O livro já está emprestado.");
        }

        // Criar a entidade Loan
        Loan loan = new Loan(
                LocalDateTime.now(), // Data do empréstimo é a data atual
                LocalDateTime.now().plusDays(7), // Data de devolução esperada (7 dias após o empréstimo)
                LoanStatus.ACTIVE, // Status inicial: ACTIVE
                user,
                book
        );

        // Salvar no banco
        Loan savedLoan = loanRepository.save(loan);

        // Retornar o DTO do empréstimo salvo
        return toDTO(savedLoan);
    }

    // Atualizar um empréstimo existente (devolver um livro)
    public LoanDTO update(String id, LoanDTO dto) {
        // Buscar o empréstimo pelo ID
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));

        // Verificar se o livro já foi devolvido
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new BusinessLogicException("O livro já foi devolvido.");
        }

        // Atualizar a data de devolução real e o status
        loan.setActualReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        // Salvar as alterações no banco de dados
        Loan updatedLoan = loanRepository.save(loan);

        // Retornar o empréstimo como DTO
        return toDTO(updatedLoan);
    }

    // Deletar um empréstimo pelo ID
    public void delete(String id) {
        loanRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empréstimo não encontrado com o ID: " + id));
        loanRepository.deleteById(id);
    }

    // Método para atualizar o status do empréstimo (verifica atraso)
    private void updateLoanStatus(Loan loan) {
        if (loan.getStatus() == LoanStatus.ACTIVE && LocalDateTime.now().isAfter(loan.getExpectedReturnDate())) {
            loan.setStatus(LoanStatus.OVERDUE); // Altera o status para OVERDUE se estiver atrasado
            loanRepository.save(loan); // Salva a alteração no banco de dados
        }
    }
}