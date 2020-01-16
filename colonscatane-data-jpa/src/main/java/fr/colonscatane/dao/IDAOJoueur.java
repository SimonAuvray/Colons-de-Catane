package fr.colonscatane.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.colonscatane.modele.Joueur;

public interface IDAOJoueur extends JpaRepository<Joueur, Integer>{

}
