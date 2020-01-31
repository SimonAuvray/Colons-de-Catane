/**
 * Ensemble des script Javascript
 */

// ecoute des evenements du serveur
let eventSource = new EventSource('http://172.16.44.108:8080/colonscatane-web/sse');
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

let compteurClickInit = 0;
// enregistre la position dans la base de données (et compter les actions)
const setOccupationEtcompteClick2 = async (event) => {
	event.preventDefault();
	let coordonnees = event.target.id.toString().split(' ');
	let type = coordonnees[0];
	let x = coordonnees[1];
	let y = coordonnees[2];
	setOccupation2(type, x, y);
	compteClick();
}

function setOccupation2(type, x, y){
console.log("http://172.16.44.108:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y);
	
	let positionOccupee = await
			fetch("http://172.16.44.108:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y, {
		method: 'GET'
	}).then(resp => resp.json());
	
	console.log(positionOccupee);
	MiseAJourCouleurPosition(positionOccupee,type);
}

function compteClick(){
	console.log("bzqqzq");
}

var tourJoueur ;
const setOccupationEtcompteClick = async (event) => {
	event.preventDefault();
let coordonnees = event.target.id.toString().split(' ');
	let type = coordonnees[0];
	let x = coordonnees[1];
	let y = coordonnees[2];
	
	console.log("http://172.16.44.108:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y);
	
	let positionOccupee = await
			fetch("http://172.16.44.108:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y, {
		method: 'GET'
	}).then(resp => resp.json());
	
	console.log(positionOccupee);
	MiseAJourCouleurPosition(positionOccupee,type);
	compteurClickInit++;
	console.log(" compteur clicks : " + compteurClickInit)
	
		fetch('http://172.16.44.108:8080/colonscatane-web/api/partie/listeJoueurs')
		.then(resp => resp.json())
		.then(joueurs => {
			if(compteurClickInit <2){
				tourJoueur = joueurs[0];
				document.querySelector('p[name="tourJoueur"]').innerHTML = tourJoueur.username;
				console.log(" 1 er joueur ");
			}
			else if(compteurClickInit >1 && compteurClickInit <4){
				tourJoueur = joueurs[1];
				document.querySelector('p[name="tourJoueur"]').innerHTML = tourJoueur.username;
				console.log(" 2 er joueur ");
			}
			else if(joueurs.length == 3){
				if(compteurClickInit >3 && compteurClickInit <6){
					tourJoueur = joueurs[2];
					document.querySelector('p[name="tourJoueur"]').innerHTML = tourJoueur.username;
				}
			}
			else if(joueurs.length == 4){
				if(compteurClickInit >7 && compteurClickInit <8){
					tourJoueur = joueurs[3];
					document.querySelector('p[name="tourJoueur"]').innerHTML = tourJoueur.username;
				}
			}
		});
}
const setOccupation = async (event) => {
	event.preventDefault();
let coordonnees = event.target.id.toString().split(' ');
	let type = coordonnees[0];
	let x = coordonnees[1];
	let y = coordonnees[2];
	
	console.log("http://172.16.44.108:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y);
	
	let positionOccupee = await
			fetch("http://172.16.44.108:8080/colonscatane-web/api/partie/"+ type+"/"+x +"/"+y, {
		method: 'GET'
	}).then(resp => resp.json());
	
	console.log(positionOccupee);
	MiseAJourCouleurPosition(positionOccupee,type);
}


// enregistrer l'occupation pour une position
const addClickCoordonnee = (position,type) => {
	console.log(type +" "+ position.x + " " + position.y);
	if(document.getElementById(type +" "+ position.x +" "+ position.y) !== null){
		document.getElementById(type +" "+ position.x +" "+ position.y).addEventListener('click', setOccupationEtcompteClick);
	}else{
		console.log("pas d'element correspondant aux coordonnées");
	}
}


fetch('http://172.16.44.108:8080/colonscatane-web/api/partie/listeCoins')
.then(resp => resp.json())
.then(coins => {
	for (let c of coins) {
		addClickCoordonnee(c,"coin");
		if(c.occupation != null){
			MiseAJourCouleurPosition(c, "coin");
		}
	}
});

fetch('http://172.16.44.108:8080/colonscatane-web/api/partie/listeSegments')
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
fetch('http://172.16.44.108:8080/colonscatane-web/api/partie/listeJoueurs')
.then(resp => resp.json())
.then(joueurs => {
	tourJoueur = joueurs[0];
});
