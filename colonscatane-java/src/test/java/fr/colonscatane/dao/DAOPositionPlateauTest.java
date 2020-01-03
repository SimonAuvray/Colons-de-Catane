package fr.colonscatane.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.colonscatane.Application;
import fr.colonscatane.modele.PositionPlateau;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class DAOPositionPlateauTest {
	
	@BeforeClass
	public static void test() {
		System.out.println("test de DAOPositionPlateau");
	}
	
	@Autowired(required = false)
	private IDAOPositionPlateau<PositionPlateau> daoPositionPlateau;
	
	@Test
	public void testDAOPositionPlateauExist() {
		assertNotNull(daoPositionPlateau);
	}
	
	@Test
	public void findById() {
		try {
			Optional<PositionPlateau> optionalPos = daoPositionPlateau.findById(1);
			assertNotNull(optionalPos);
			assertTrue(optionalPos.isPresent());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSupprimerPosition() {
		try {
			daoPositionPlateau.deleteById(1);
			assertNotNull(daoPositionPlateau.findById(1));
			assertFalse(daoPositionPlateau.findById(1).isPresent());
		} catch(Exception e) {
			fail(e.getMessage());
		}
		
	}
	
}
