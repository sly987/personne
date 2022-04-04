package serialisation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class Serialization implements personnel.Passerelle
{
	private static final String FILE_NAME = "GestionPersonnel.srz";

	@Override
	public GestionPersonnel getGestionPersonnel()
	{
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME)))
		{
			return (GestionPersonnel) ois.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			return null;
		}
	}
	
	/**
	 * Sauvegarde le gestionnaire pour qu'il soit ouvert automatiquement 
	 * lors d'une exécution ultérieure du programme.
	 * @throws SauvegardeImpossible Si le support de sauvegarde est inaccessible.
	 */
	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME)))
		{
			oos.writeObject(gestionPersonnel);
		}
		catch (IOException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible
	{
		return -1;
	}

	@Override
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int insert(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateEmploye(Employe employe, String string) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEmp(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAdmin(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employe getSuperAdmin() throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SetAdmin(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRoot(Employe employe) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAdmin(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		
	}
}
