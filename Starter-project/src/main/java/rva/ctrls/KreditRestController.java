package rva.ctrls;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Kredit;
import rva.repository.KreditRepository;
@CrossOrigin
@RestController
@Api(tags = {"Kredit CRUD operacije"})
public class KreditRestController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KreditRepository kreditRepository;
	
	@GetMapping("kredit")
	@ApiOperation(value= "Vraca kolekciju svih kredita iz baze podataka.")
	public Collection<Kredit> getKrediti(){
		return kreditRepository.findAll();
	}
	
	@GetMapping("kredit/{id}")
	@ApiOperation(value= "Vraca kredit iz baze podataka cija je id vrednost prosledjena kao path varijabla.")
	public Kredit getKredit(@PathVariable("id") Integer id) {
		return kreditRepository.getOne(id);
	}
	
	@GetMapping("kreditNaziv/{naziv}")
	@ApiOperation(value= "Vraca kolekciju svih kredita iz baze podataka koji u nazivu sadrze string prosledjen kao path varijablu.")
	public Collection<Kredit> getKreditByNaziv(@PathVariable("naziv") String naziv){
		return kreditRepository.findByNazivContainingIgnoreCase(naziv);
	}

	@PostMapping("kredit")
	@ApiOperation(value= "Upisuje kredit u bazu podataka.")
	public ResponseEntity<Kredit> insertKredit(@RequestBody Kredit kredit){
		if(!kreditRepository.existsById(kredit.getId())) {
			kreditRepository.save(kredit);
			return new ResponseEntity<Kredit>(HttpStatus.OK);
		}
		return new ResponseEntity<Kredit>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("kredit")
	@ApiOperation(value= "Modifikuje postojeci kredit u bazi podataka.")
	public ResponseEntity<Kredit> updateKredit(@RequestBody Kredit kredit){
		if(!kreditRepository.existsById(kredit.getId())) {
			return new ResponseEntity<Kredit>(HttpStatus.NO_CONTENT);
		}
		kreditRepository.save(kredit);
		return new ResponseEntity<Kredit>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("kredit/{id}")
	@ApiOperation(value= "Brise kredit iz baze podataka cija je id vrednost prosledjena kao path varijabla.")
	public ResponseEntity<Kredit> deleteKredit(@PathVariable("id") Integer id){
		if(!kreditRepository.existsById(id)) {
			return new ResponseEntity<Kredit>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("delete from klijent where kredit = " +id);
		kreditRepository.deleteById(id);
		if (id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"kredit\" (\"id\", \"naziv\", \"oznaka\", \"opis\") "
					+ "VALUES (-100, 'Izbrisan', 'UspesnoIzbrisan', 'Kredit je uspesno izbrisan')");
		}
		return new ResponseEntity<Kredit>(HttpStatus.OK);
	}
}
