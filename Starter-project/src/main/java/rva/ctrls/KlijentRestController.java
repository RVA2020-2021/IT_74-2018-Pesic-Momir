package rva.ctrls;

import java.util.Collection;
import javax.annotation.PostConstruct;
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
import rva.jpa.Klijent;
import rva.jpa.Kredit;
import rva.repository.KlijentRepository;
import rva.repository.KreditRepository;
@CrossOrigin
@RestController
@Api(tags = {"Klijent CRUD operacije"})
public class KlijentRestController {
	
	@Autowired
	private KlijentRepository klijentRepository;
	
	@Autowired
	private KreditRepository kreditRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping("klijent")
	@ApiOperation(value= "Vraca kolekciju svih klijenata iz baze podataka.")
	public Collection<Klijent> getKlijenti(){
		return klijentRepository.findAll();
	}
	
	@GetMapping("klijent/{id}")
	@ApiOperation(value= "Vraca klijenta cija je id vrednost prosledjena kao path varijabla.")
	public Klijent getKlijent(@PathVariable("id") Integer id) {
		return klijentRepository.getOne(id);
	}
	
	@GetMapping("klijentIme/{ime}")
	@ApiOperation(value= "Vraca kolekciju svih klijenata iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla.")
	public Collection<Klijent> getKlijentByIme(@PathVariable("ime") String ime) {
		return klijentRepository.findByImeContainingIgnoreCase(ime); 
	}
	
	@GetMapping("klijentiKreditId/{id}")
	@ApiOperation(value= "Vraca kolekciju klijenata iz baze podataka koji u polju kredit sadrze id koji je prosledjen kao path varijabla.")
	public Collection<Klijent> getKlijentByKreditId(@PathVariable("id") Integer id){
		Kredit k = kreditRepository.getOne(id);
		return klijentRepository.findByKredit(k);
	}
	
	
	
	@PostMapping("klijent")
	@ApiOperation(value= "Upisuje klijenta u bazu podataka.")
	public ResponseEntity<Klijent> insertKlijent(@RequestBody Klijent klijent){
		if(!klijentRepository.existsById(klijent.getId())) {
			klijentRepository.save(klijent);
			return new ResponseEntity<Klijent>(HttpStatus.OK);
		}
		return new ResponseEntity<Klijent>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("klijent")
	@ApiOperation(value= "Modifikuje postojeceg klijenta u bazi podataka.")
	public ResponseEntity<Klijent> updateKlijent(@RequestBody Klijent klijent){
		if(!klijentRepository.existsById(klijent.getId())) {
			return new ResponseEntity<Klijent>(HttpStatus.NO_CONTENT);
		}
		klijentRepository.save(klijent);
		return new ResponseEntity<Klijent>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("klijent/{id}")
	@ApiOperation(value= "Brise klijenta iz baze podataka cije je id vrednost prosledjena kao path varijabla.")
	public ResponseEntity<Klijent> deleteKlijent(@PathVariable("id") Integer id){
		if(!klijentRepository.existsById(id)) {
			return new ResponseEntity<Klijent>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("delete from racun where klijent = " +id);
		klijentRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"artikl\" (\"id\", \"ime\", \"prezime\", \"broj_lk\", \"kredit\") "
					+ "VALUES (-100, 'Testa', 'Testic', 778, -100)");
		}
		return new ResponseEntity<Klijent>(HttpStatus.OK);
	}
}
