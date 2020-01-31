/**
 * Ensemble des script Javascript
 */

// ecoute des evenements du serveur
let eventSource = new EventSource('http://localhost:8081/colonscatane-web/sse');
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


// enregistre la position dans la base de données 
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

//let tableauJoueurs = document.querySelector('#form_produit');
//fetch('http://localhost:8080/colonscatane-web/api/partie/listeJoueurs')
//.then(resp => resp.json())
//.then(joueurs => {
//	for (let j of joueurs){
//
//	}
//});

