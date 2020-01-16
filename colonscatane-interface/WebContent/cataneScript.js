/**
 * Navigation
 */

const hideForms = () => {

document.querySelectorAll('form')
	.forEach((section) => {
		section.style.display = 'none';
	});
}

hideForms();

document.querySelector('#connexion').style.display = 'block';

document.querySelector('#connexionBtn').addEventListener('click', () => {
	event.preventDefault();
	document.querySelector('#connexion').style.display = 'none';
	document.querySelector('#userCompte').style.display = 'block';
});

document.querySelector('#creationCompteBtn1').addEventListener('click', () => {
	event.preventDefault();
	document.querySelector('#connexion').style.display = 'none';
	document.querySelector('#userCreator').style.display = 'block';
});

