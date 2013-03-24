package com.metal.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;


/**
 * The persistent class for the participant database table.
 * 
 */
@Entity
@Table(name="participant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participant.findAll", query = "SELECT p FROM Participant p"),
    @NamedQuery(name = "Participant.findByIdParticipant", query = "SELECT p FROM Participant p WHERE p.idParticipant = :idParticipant"),
    @NamedQuery(name = "Participant.findByName", query = "SELECT p FROM Participant p WHERE p.name = :name"),
    @NamedQuery(name = "Participant.findByPhone", query = "SELECT p FROM Participant p WHERE p.phone = :phone"),
    @NamedQuery(name = "Participant.findByAge", query = "SELECT p FROM Participant p WHERE p.age = :age"),
    @NamedQuery(name = "Participant.findByGender", query = "SELECT p FROM Participant p WHERE p.gender = :gender"),
    @NamedQuery(name = "Participant.findByUsername", query = "SELECT p FROM Participant p WHERE p.username = :username"),
    @NamedQuery(name = "Participant.findByPassword", query = "SELECT p FROM Participant p WHERE p.password = :password")})
public class Participant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_participant", unique=true, nullable=false)
	private int idParticipant;

	@Column(nullable=false)
	private int age;

	@Column(nullable=false, length=50)
	private String gender;

	@Column(nullable=false, length=100)
	private String name;

	@Column(nullable=false, length=50)
	private String password;

	@Column(nullable=false)
	private int phone;

	@Column(nullable=false, length=50)
	private String username;

	//bi-directional many-to-one association to Presentation
	@OneToMany(mappedBy="participant")
	private List<Presentation> presentations;

	//bi-directional many-to-one association to ScoreMatrix
	@OneToMany(mappedBy="participant")
	private List<ScoreMatrix> scoreMatrixs;

	public Participant() {
	}

	public int getIdParticipant() {
		return this.idParticipant;
	}

	public void setIdParticipant(int idParticipant) {
		this.idParticipant = idParticipant;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhone() {
		return this.phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Presentation> getPresentations() {
		return this.presentations;
	}

	public void setPresentations(List<Presentation> presentations) {
		this.presentations = presentations;
	}

	public Presentation addPresentation(Presentation presentation) {
		getPresentations().add(presentation);
		presentation.setParticipant(this);

		return presentation;
	}

	public Presentation removePresentation(Presentation presentation) {
		getPresentations().remove(presentation);
		presentation.setParticipant(null);

		return presentation;
	}

	public List<ScoreMatrix> getScoreMatrixs() {
		return this.scoreMatrixs;
	}

	public void setScoreMatrixs(List<ScoreMatrix> scoreMatrixs) {
		this.scoreMatrixs = scoreMatrixs;
	}

	public ScoreMatrix addScoreMatrix(ScoreMatrix scoreMatrix) {
		getScoreMatrixs().add(scoreMatrix);
		scoreMatrix.setParticipant(this);

		return scoreMatrix;
	}

	public ScoreMatrix removeScoreMatrix(ScoreMatrix scoreMatrix) {
		getScoreMatrixs().remove(scoreMatrix);
		scoreMatrix.setParticipant(null);

		return scoreMatrix;
	}

	@Override
	public String toString() {
		return "Participant [idParticipant=" + idParticipant + ", age=" + age + ", gender=" + gender + ", name=" + name
				+ ", password=" + password + ", phone=" + phone + ", username=" + username + ", presentations="
				+ presentations + ", scoreMatrixs=" + scoreMatrixs + "]";
	}
}