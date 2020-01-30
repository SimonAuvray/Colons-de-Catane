package fr.colonscatane.application;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Component
public class SessionContextLoader {
	
	private List<HttpSession> sessions;
	
	public List<HttpSession> getSessions() {
		if(this.sessions == null) {
			this.sessions = new ArrayList<HttpSession>();
		}
		return sessions;
	}

	public void setSessions(List<HttpSession> sessions) {
		this.sessions = sessions;
	}
	
	public void addSession(HttpSession session) {
		
		if(this.sessions == null) {
			this.sessions = new ArrayList<HttpSession>();
		}
		sessions.add(session);
	}

}
