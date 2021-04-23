package rva.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Klijent;
import rva.jpa.Kredit;


public interface KlijentRepository extends JpaRepository<Klijent, Integer> {
	
	Collection<Klijent> findByImeContainingIgnoreCase(String ime);
	Collection<Klijent> findByKredit(Kredit kredit);
	
}
