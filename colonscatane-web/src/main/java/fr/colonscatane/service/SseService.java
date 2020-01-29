package fr.colonscatane.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class SseService {
	
	private List<SseEmitter> emitters = new ArrayList<SseEmitter>();
	
	@GetMapping("/sse")
	public SseEmitter gestionEmitters(){
		SseEmitter emitter = new SseEmitter();
		
		//action a faire quand l'event est complet
		// runnable : fonction qui n'attend rien et qui ne retourne rien
		emitter.onCompletion(() -> {
			// synchronized permet de signaler qu'il faut attendre que la liste soit disponible avant d'agirr dessus.
			// en effet, elle peut etre utilisee par plusieurs thread. Il faut qu'elle soit disponible avant d'y supprimer
			// un element
			synchronized (this.emitters) {
			this.emitters.remove(emitter);
			}
		});
		
		// action � faire quand l'event est en timeout
		// la fonction complete d�clenche onCompletion
		emitter.onTimeout(() -> {
			emitter.complete();
		});
		
		this.emitters.add(emitter);
		return emitter;
		
	}
	
	// Fonction pour transmettre un message au client 
	// le message peut etre un string ou un objet.
	// avec Jackson, c'est objet devrait etre automatiquement transcrit au format json
	public void emissionObjet(Object o) {
		this.emitters.forEach(emitter -> {
			try {
				emitter.send(o);
			}catch(Exception ex) {
				emitter.completeWithError(ex);
			}
		});
	}

}
