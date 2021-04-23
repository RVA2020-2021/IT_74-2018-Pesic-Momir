package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the racun database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitiallizer", "handler"})
@Entity
@NamedQuery(name="Racun.findAll", query="SELECT r FROM Racun r")
public class Racun implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RACUN_ID_GENERATOR", sequenceName="RACUN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RACUN_ID_GENERATOR")
	private Integer id;

	private String naziv;

	private String opis;

	private String oznaka;

	//bi-directional many-to-one association to Klijent
	@ManyToOne
	@JoinColumn(name="klijent")
	private Klijent klijent;

	//bi-directional many-to-one association to TipRacuna
	@ManyToOne
	@JoinColumn(name="tip_racuna")
	private TipRacuna tipRacuna;

	public Racun() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getOznaka() {
		return this.oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public Klijent getKlijent() {
		return this.klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public TipRacuna getTipRacuna() {
		return this.tipRacuna;
	}

	public void setTipRacuna(TipRacuna tipRacuna) {
		this.tipRacuna = tipRacuna;
	}

}