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
	document.querySelectorAll('form > div').forEach((div) => {
		div.style.display = 'none';
	});
	document.querySelector('#userCompte').style.display = 'block';
});

document.querySelector('#creationCompteBtn1').addEventListener('click', () => {
	event.preventDefault();
	document.querySelector('#connexion').style.display = 'none';
	document.querySelector('#userCreator').style.display = 'block';
});

document.querySelector('.rules').addEventListener('click', () => {
	event.preventDefault();
	document.querySelectorAll('form > div').forEach((div) => {
		div.style.display = 'none';
	});
	document.querySelector('div[name = "divRules"]').style.display = 'block';
});

document.querySelector('.profile').addEventListener('click', () => {
	event.preventDefault();
	document.querySelectorAll('form > div').forEach((div) => {
		div.style.display = 'none';
	});
	document.querySelector('div[name = "divProfile"]').style.display = 'block';
});

document.querySelector('.copains').addEventListener('click', () => {
	event.preventDefault();
	document.querySelectorAll('form > div').forEach((div) => {
		div.style.display = 'none';
	});
	document.querySelector('div[name = "divCopains"]').style.display = 'block';
});

document.querySelector('.params').addEventListener('click', () => {
	event.preventDefault();
	document.querySelectorAll('form > div').forEach((div) => {
		div.style.display = 'none';
	});
	document.querySelector('div[name = "divParams"]').style.display = 'block';
});

