package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	boolean existsByEmailAndPassword(String email, String password);

	Company findByEmailAndPassword(String email, String password);

	boolean existsByNameAndEmail(String name, String email);

	boolean existsByName(String name);

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, int id);

}
