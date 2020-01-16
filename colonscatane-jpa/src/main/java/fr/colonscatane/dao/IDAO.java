package fr.colonscatane.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IDAO <T, Id> {
	
	public List<T> findAll();

}
