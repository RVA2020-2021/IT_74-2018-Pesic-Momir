package rva.ctrls;

import java.util.Collection;

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
import rva.jpa.Racun;
import rva.jpa.TipRacuna;
import rva.repository.KlijentRepository;
import rva.repository.RacunRepository;
import rva.repository.TipRacunaRepository;

@CrossOrigin
@RestController
@Api(tags = {"Racun CRUD operacije"})
public class RacunRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RacunRepository racunRepository;
	
	@Autowired
	private KlijentRepository klijentRepository;
	
	@Autowired
	private TipRacunaRepository tipRacunaRepository;
	
	@GetMapping("racun")
	@ApiOperation(value= "Vraca kolekciju svih racuna iz baze podataka.")
	public Collection<Racun> getRacuni(){
		return racunRepository.findAll();
	}
	
	@GetMapping("racun/{id}")
	@ApiOperation(value= "Vraca racun iz baze podataka cija je id vrednost prosledjena kao path varijabla.")
	public Racun getRacun(@PathVariable("id") Integer id) {
		return racunRepository.getOne(id);
	}
	
	@GetMapping("racunNaziv/{naziv}")
	@ApiOperation(value= "Vraca kolekciju svih racuna iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla.")
	public Collection<Racun> getRacuniByNaziv(@PathVariable("naziv") String naziv){
		return racunRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@GetMapping("racuniKlijentId/{id}")
	@ApiOperation(value= "Vraca kolekciju svih racuna iz baze podataka koji kao klijenta sadrze id prosledjen kao path varijabla.")
	public Collection<Racun> getRacuniByKlijent(@PathVariable("id") Integer id){
		Klijent k = klijentRepository.getOne(id);
		return racunRepository.findByKlijent(k);
	}
	
	@GetMapping("racuniTipRacunaId/{id}")
	@ApiOperation(value= "Vraca kolekciju svih racuna iz baze podataka koji kao tip racuna sadrze id prosledjen kao path varijabla.")
	public Collection<Racun> getRacuniByTipRacuna(@PathVariable("id") Integer id){
		TipRacuna tr = tipRacunaRepository.getOne(id);
		return racunRepository.findByTipRacuna(tr);
	}
	
	@PostMapping("racun")
	@ApiOperation(value= "Upisuje racun u bazu podataka.")
	public ResponseEntity<Racun> insertRacun(@RequestBody Racun racun){
		if(!racunRepository.existsById(racun.getId())) {
			racunRepository.save(racun);
			return new ResponseEntity<Racun>(HttpStatus.OK);
		}
		return new ResponseEntity<Racun>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("racun")
	@ApiOperation(value= "Modifikuje postojeci racun u bazi podataka.")
	public ResponseEntity<Racun> updateRacun(@RequestBody Racun racun){
		if(!racunRepository.existsById(racun.getId())) {
			return new ResponseEntity<Racun>(HttpStatus.NO_CONTENT);
		}
		racunRepository.save(racun);
		return new ResponseEntity<Racun>(HttpStatus.OK);
	}
	
	@DeleteMapping("racun/{id}")
	@ApiOperation(value= "Brise racun iz baze podataka cije je id vrednost prosledjena kao path varijabla.")
	public ResponseEntity<Racun> deleteRacun(@PathVariable("id") Integer id){
		if(!racunRepository.existsById(id)) {
			return new ResponseEntity<Racun>(HttpStatus.NO_CONTENT);
		}
		
		racunRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"racun\"(\"id\", \"naziv\", \"oznaka\", \"opis\", \"tip_racuna\", \"klijent\") "
					+ "VALUES (-100, 'novi naziv racuna', 'nova oznaka racuna', '-100', '-100')"
			);
		}
		return new ResponseEntity<Racun>(HttpStatus.OK);
	}
  }
