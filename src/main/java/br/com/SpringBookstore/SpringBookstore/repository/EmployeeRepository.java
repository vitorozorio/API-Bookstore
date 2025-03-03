package br.com.SpringBookstore.SpringBookstore.repository;

import br.com.SpringBookstore.SpringBookstore.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByName(String name);
}
