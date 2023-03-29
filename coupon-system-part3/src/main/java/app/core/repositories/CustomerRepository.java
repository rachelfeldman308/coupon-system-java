package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmailAndPassword(String email, String password);

	Customer findByEmailAndPassword(String email, String password);

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, int id);

}
