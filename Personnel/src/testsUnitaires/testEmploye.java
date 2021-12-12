package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import personnel.*;

public class testEmploye {
	
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void estAdminTrue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		ligue.setAdministrateur(employe);
		assertEquals(true , employe.estAdmin(ligue) );
	}
	
	@Test
	void estAdminFalse() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(false , employe.estAdmin(ligue) );
	}
	@Test
	void estRootFalse() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(false, employe.estRoot());
	}
	
	@Test
	void estRootTrue() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		assertEquals(true, root.estRoot());
	}
	
	@Test 
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		employe.setNom("jambon");
		assertEquals("jambon", employe.getNom());
	}
	
	@Test 
	void setPrenom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		employe.setPrenom("Depardieu");
		assertEquals("Depardieu", employe.getPrenom());
	}
	
	@Test
	void setMail() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		employe.setMail("G.Bouchard@outlook.fr");
		assertEquals("G.Bouchard@outlook.fr", employe.getMail());
	}
	
	@Test
	void setPassword() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		employe.setPassword("password");
		assertEquals(true, employe.checkPassword("password"));
	}

	@Test
	void remove() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		employe.remove();
		assertEquals(false, ligue.getEmployes().contains(employe));
	}
	
	@Test
	void removeAdmin() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		ligue.setAdministrateur(employe);
		employe.remove();
		assertEquals(false, ligue.getEmployes().contains(employe));
	}
	
	@Test
	void removeRoot() throws SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () ->root.remove());
	}
}
