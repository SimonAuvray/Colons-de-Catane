package fr.colonscatane.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import fr.colonscatane.modele.Partie;

@ApplicationScope
@Component
public class PartieContextLoader {
	
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
