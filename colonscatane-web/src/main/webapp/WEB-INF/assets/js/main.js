/**
 * Ensemble des script Javascript
 */

// ecoute des evenements du serveur
let eventSource = new EventSource('http://localhost:8080/colonscatane-web/sse');
eventSource.addEventListener('message', (event) => {
	let msg = event.data;
	alert(msg);
});
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
	console.log(document.getElementById(type +" "+ position.x +" "+ position.y));
	document.getElementById(type +" "+ position.x +" "+ position.y).addEventListener('click', setOccupation);
}


fetch('http://localhost:8080/colonscatane-web/api/partie/listeCoins')
.then(resp => resp.json())
.then(coins => {
	for (let c of coins) {
		addClickCoordonnee(c,"coin");
	}
});

fetch('http://localhost:8080/colonscatane-web/api/partie/listeSegments')
.then(resp => resp.json())
.then(segments => {
	for (let s of segments) {
		addClickCoordonnee(s,"segment");
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

