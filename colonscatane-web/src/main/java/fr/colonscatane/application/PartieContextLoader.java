package fr.colonscatane.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import fr.colonscatane.modele.Partie;

@Component
@ApplicationScope
public class PartieContextLoader implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<Partie> parties;

	public List<Partie> getParties() {
		if(this.parties == null) {
			this.parties = new ArrayList<Partie>();
		}
		return parties;
	}

	public void setParties(List<Partie> parties) {
		this.parties = parties;
	}

	
}
