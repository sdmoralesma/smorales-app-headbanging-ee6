package com.metal.data;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.resource.NotSupportedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.metal.ejb.AdminBean;
import com.metal.model.Participant;
import com.metal.model.ScoreMatrix;

public class DummyData {

	private AdminBean bean;

	@Before
	public void setUp() throws Exception {
		bean = new AdminBean();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test()
	public void shouldCreateAdmins() throws NotSupportedException {
		Participant participant = new Participant(31, "myhouse", "FEMALE", "asdf", "asdf", new ArrayList(),
				new ScoreMatrix());
		bean.setParticipant(participant);
		bean.registerParticipant();

	}
}
