/**
 * Ensemble des script Javascript
 */

// ecoute des evenements du serveur
let eventSource = new EventSource('http://localhost:8080/colonscatane-web/sse');
eventSource.addEventListener('message', (event) => {
	let msg = event.data;
	alert(msg);
});

// Met la bonne couleur sur les emplacement
const MiseAJourCouleurPosition = (position,type) => {
	let element = document.getElementById(type +" "+ position.x +" "+ position.y);
	let couleur = 'green';
	switch (position.occupation.couleur) {
	  case 'BLEU':
		  couleur='blue';
	    break;
	  case 'ROUGE':
		  couleur='red';
	    break;
	  case 'NOIR':
		  couleur='black';
	    break;
	  case 'JAUNE':
		  couleur='yellow';
	    break;
	}
	element.style.backgroundColor = couleur;
}

let compteurActionInit = 0;
// enregistre la position dans la base de données (et compter les actions)
const setOccupation = async (event) => {
	event.preventDefault();
let coordonnees = event.target.id.toString().split(' ');
	let type = coordonnees[0];
	let x = coordonnees[1];
	let y = coordonnees[2];
	
	console.log("http://localhost:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y);
	
	let positionOccupee = await
			fetch("http://localhost:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y, {
		method: 'GET'
	}).then(resp => resp.json());
	
	console.log(positionOccupee);
	MiseAJourCouleurPosition(positionOccupee,type);
	compteurActionInit++;
}


// enregistrer l'occupation pour une position
const addClickCoordonnee = (position,type) => {
	console.log(type +" "+ position.x + " " + position.y);
	if(document.getElementById(type +" "+ position.x +" "+ position.y) !== null){
		document.getElementById(type +" "+ position.x +" "+ position.y).addEventListener('click', setOccupation);
	}else{
		console.log("pas d'element correspondant aux coordonnées");
	}
}


fetch('http://localhost:8080/colonscatane-web/api/partie/listeCoins')
.then(resp => resp.json())
.then(coins => {
	for (let c of coins) {
		addClickCoordonnee(c,"coin");
		if(c.occupation != null){
			MiseAJourCouleurPosition(c, "coin");
		}
	}
});

fetch('http://localhost:8080/colonscatane-web/api/partie/listeSegments')
.then(resp => resp.json())
.then(segments => {
	for (let s of segments) {
		addClickCoordonnee(s,"segment");
		if(s.occupation != null){
			MiseAJourCouleurPosition(s, "segment");
		}
	}
});

//recuperation de la liste de joueurs
let nombreJoueur = 0;
let mesJoueurs = [];
fetch('http://localhost:8080/colonscatane-web/api/partie/listeJoueurs')
.then(resp => resp.json())
.then(joueurs => {
	
	if(compteurActionInit <= joueurs.length * 2){
		appelTourJoueurInit(joueurs);
	}
	
});

function appelTourJoueurInit(joueurs){
	
	console.log("tour d'initialisation");
	if(compteurActionInit < 2){
		tourJoueur = joueurs[0];
	}
	if(compteurActionInit > 1 && compteurActionInit < 4){
		tourJoueur = joueurs[1];
	}
	else if(mesJoueurs.length == 3){
		if(compteurActionInit > 3 && compteurActionInit < 6){
			tourJoueur = joueurs[2];
		}
	}
	else if(mesJoueurs.length == 4){
		if(compteurActionInit > 5 && compteurActionInit < 8){
			tourJoueur = joueurs[3];
		}
	}
	document.querySelector('p[name="tourJoueur"]').innerHTML = tourJoueur.username;
}