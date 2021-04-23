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
import rva.jpa.TipRacuna;
import rva.repository.TipRacunaRepository;

@CrossOrigin
@RestController
@Api(tags = {"Tip racuna CRUD operacije"})
public class TipRacunaRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TipRacunaRepository tipRacunaRepository;
	
	@GetMapping("tipRacuna")
	@ApiOperation(value= "Vraca kolekciju svih tipova racuna iz baze podataka.")
	public Collection<TipRacuna> getTipoviRacuna(){
		return tipRacunaRepository.findAll();
	}
	
	@GetMapping("tipRacuna/{id}")
	@ApiOperation(value= "Vraca tip racuna iz baze podataka cija je id vrednost prosledjena kao path varijabla.")
	public TipRacuna getTipRacunaByID(@PathVariable("id") Integer id) {
		return tipRacunaRepository.getOne(id);
	}
	
	@GetMapping("tipRacunaNaziv/{naziv}")
	@ApiOperation(value= "Vraca kolekciju svih tipova racuna iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla.")
	public Collection<TipRacuna> getTipRacunaByNaziv(@PathVariable("naziv") String naziv){
		return tipRacunaRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("tipRacuna")
	@ApiOperation(value= "Upisuje tip racuna u bazu podataka.")
	public ResponseEntity<TipRacuna> insertTipRacuna(@RequestBody TipRacuna tipRacuna){
		if(!tipRacunaRepository.existsById(tipRacuna.getId())) {
			tipRacunaRepository.save(tipRacuna);
			return new ResponseEntity<TipRacuna>(HttpStatus.OK);
		}
		return new ResponseEntity<TipRacuna>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("tipRacuna")
	@ApiOperation(value= "Modifikuje postojeci tip racuna u bazi podataka.")
	public ResponseEntity<TipRacuna> updateTipRacuna(@RequestBody TipRacuna tipRacuna){
		if(!tipRacunaRepository.existsById(tipRacuna.getId())) {
			return new ResponseEntity<TipRacuna>(HttpStatus.NO_CONTENT);
		}
		tipRacunaRepository.save(tipRacuna);
		return new ResponseEntity<TipRacuna>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("tipRacuna/{id}")
	@ApiOperation(value= "Brise tip racuna iz baze podataka cija je id vrednost prosledjena kao path varijabla.")
	public ResponseEntity<TipRacuna> deleteTipRacuna(@PathVariable("id") Integer id){
		if(!tipRacunaRepository.existsById(id)){
			return new ResponseEntity<TipRacuna>(HttpStatus.NO_CONTENT);
		}
		
		jdbcTemplate.execute("delete from racun where tip_racuna = " +id);
		tipRacunaRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"tip_racuna\"(\"id\", \"naziv\", \"oznaka\", \"opis\") "
					+ "VALUES (-100, 'novi naziv tipa racuna', 'nova oznaka tipa racuna', 'novi opis tip racuna')"
			);
		}
		return new ResponseEntity<TipRacuna>(HttpStatus.OK);
	}
}
