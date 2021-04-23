package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the kredit database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitiallizer", "handler"})
@Entity
@NamedQuery(name="Kredit.findAll", query="SELECT k FROM Kredit k")
public class Kredit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KREDIT_ID_GENERATOR", sequenceName="KREDIT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KREDIT_ID_GENERATOR")
	private Integer id;

	private String naziv;

	private String opis;

	private String oznaka;

	//bi-directional many-to-one association to Klijent
	@JsonIgnore
	@OneToMany(mappedBy="kredit")
	private List<Klijent> klijents;

	public Kredit() {
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

	public List<Klijent> getKlijents() {
		return this.klijents;
	}

	public void setKlijents(List<Klijent> klijents) {
		this.klijents = klijents;
	}

	public Klijent addKlijent(Klijent klijent) {
		getKlijents().add(klijent);
		klijent.setKredit(this);

		return klijent;
	}

	public Klijent removeKlijent(Klijent klijent) {
		getKlijents().remove(klijent);
		klijent.setKredit(null);

		return klijent;
	}

}