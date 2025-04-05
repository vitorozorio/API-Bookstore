package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Loan;
import br.com.SpringBookstore.SpringBookstore.domain.enuns.LoanStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
    Optional<Loan> findByBookIdAndStatus(String bookId, LoanStatus status);
    List<Loan> findByUserId(String userId); // Empréstimos de um usuário
    List<Loan> findByBookId(String bookId); // Empréstimos de um livro específico
}
