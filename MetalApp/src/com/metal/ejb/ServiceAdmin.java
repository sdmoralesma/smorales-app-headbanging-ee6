package com.metal.ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.metal.model.Jury;
import com.metal.model.Participant;

/**
 * Registra Usuarios y Administradores en el sistema
 */
@Stateless
@LocalBean
public class ServiceAdmin {

	@PersistenceContext(unitName = "MetalApp")
	private EntityManager em;

	/** Default constructor. */
	public ServiceAdmin() {

	}

	// Participant Methods
	public List<Participant> findAllParticipants() {
		TypedQuery<Participant> query = em.createNamedQuery(
				"Participant.findAll", Participant.class);
		return query.getResultList();
	}

	public Participant findParticipantById(Long id) {
		return em.find(Participant.class, id);
	}

	public Participant createParticipant(Participant participant) {
		em.persist(participant);
		return participant;
	}

	public void deleteParticipant(Participant participant) {
		em.remove(em.merge(participant));
	}

	public Participant updateParticipant(Participant participant) {
		return em.merge(participant);
	}

	// Jury Methods
	public List<Jury> findAllJuries() {
		TypedQuery<Jury> query = em.createNamedQuery("Jury.findAll", Jury.class);
		return query.getResultList();
	}

	public Jury findJuryById(Long id) {
		return em.find(Jury.class, id);
	}

	public Jury createJury(Jury jury) {
		em.persist(jury);
		return jury;
	}

	public void deleteJury(Jury jury) {
		em.remove(em.merge(jury));
	}

	public Jury updateJury(Jury jury) {
		return em.merge(jury);
	}

}
