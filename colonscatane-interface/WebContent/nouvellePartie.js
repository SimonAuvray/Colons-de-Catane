/**
 * 
 */
let numJoueur = 0;
document.querySelector('.saisie_joueur').addEventListener('click', () => {
	try {
		event.preventDefault();
		document.querySelector(`td[name="nomJoueur${numJoueur}"]`).innerHTML = document.querySelector('input[id="saisieUsername"]').value;
		numJoueur++;
	}
	catch(err){
		alert("vous ne pouvez pas inscrire plus de 4 joueurs");
		console.error("vous ne pouvez pas inscrire plus de 4 joueurs");
	}
});
