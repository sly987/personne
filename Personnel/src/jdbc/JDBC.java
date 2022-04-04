package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (Nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible 
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE ligue SET Nom = ? WHERE Id = ?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	
	@Override
	public void updateEmp(Employe employe) throws SauvegardeImpossible 
	{
		try
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET NomEmploye = ?, PrenomEmploye = ?, Mail = ? , mpd = ? WHERE IdEmploye = ?");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setInt(5, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible 
	{	
		try
		{
			PreparedStatement listLigue;
			listLigue = connection.prepareStatement("DELETE FROM ligue WHERE Id = ?");
			listLigue.setInt(1, ligue.getId());
			listLigue.executeUpdate();
			System.out.println("Ligue " + ligue.getNom() + " supprimé ");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}
	
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
		try {
			
			PreparedStatement instruction2;
			instruction2 = connection.prepareStatement("insert into employe (NomEmploye, PrenomEmploye, Mail, mdp, dateArrive) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction2.setString(1, employe.getNom());		
			instruction2.setString(2, employe.getPrenom());	
			instruction2.setString(3, employe.getMail());
			instruction2.setString(4, employe.getPassword());
			instruction2.setString(5, employe.getDateA() == null ? null :  String.valueOf(employe.getDateA()));
			instruction2.executeUpdate();
			ResultSet id = instruction2.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		}
		catch (SQLException exception)
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
	}
	
	public void updateEmploye(Employe employe, String dataList) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
	        instruction = connection.prepareStatement("UPDATE employe SET " + dataList + "= ? WHERE IdEmploye = ?");
	
			Map <String, String> map = new HashMap<>();
					map.put("NomEmploye", employe.getNom());
					map.put("PrenomEmploye", employe.getPrenom());
					map.put("Mail", employe.getMail());
					map.put("mdp", employe.getPassword());
					map.put("dateArrivee", String.valueOf(employe.getDateA()).isEmpty() ? null : String.valueOf(employe.getDateA()));
					map.put("dateDepart", String.valueOf(employe.getDateD()).isEmpty() ? null : String.valueOf(employe.getDateD()));
		instruction.setString(1, map.get(dataList));
	    instruction.setInt(2, employe.getId());
			instruction.executeUpdate();
		}
		catch (SQLException e) 
		{
			
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public void SetAdmin(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("UPDATE Ligue SET LAdmin = ? WHERE id = ? AND idEmploye = ?");
			listEmploye.setInt(1, 1);
			listEmploye.setInt(2, employe.getLigue().getId());
			listEmploye.setInt(3, employe.getId());
			listEmploye.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	public void setRoot() 
	{
		try {
			
			Statement instruction = connection.createStatement();
			String requete = "INSERT INTO employe (NomEmploye, PrenomEmploye, Mail, mdp, IsRoot) VALUES (root,root,root@root.fr,toor,1)";
			instruction.executeUpdate(requete);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void updateRoot(Employe employe) throws SauvegardeImpossible
	{
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("UPDATE employe SET NomEmploye = ?, PrenomEmploye = ?, Mail = ?, mdp = ? WHERE IsRoot = 1");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	@Override
	public void removeAdmin(Ligue ligue) throws SauvegardeImpossible
	{
		try
		{
			PreparedStatement tableEmploye;
			tableEmploye = connection.prepareStatement("UPDATE Ligue SET LAdmin = null WHERE Id = ?");
			tableEmploye.setInt(1, ligue.getId());
			tableEmploye.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	public void setAdmin(Employe employe) throws SauvegardeImpossible
	{
		try 
		{
			PreparedStatement tableEmploye;
			tableEmploye = connection.prepareStatement("UPDATE Ligue SET LAdmin = (CASE WHEN IdEmploye = ? THEN 1 WHEN IdEmploye <> ? THEN null END) WHERE Id = ?");
			tableEmploye.setInt(1, employe.getId());
			tableEmploye.setInt(2, employe.getId());
			tableEmploye.setInt(3, employe.getLigue().getId());
			tableEmploye.executeUpdate();
		} 
		catch (SQLException e) 
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	public Employe getSuperAdmin() throws SauvegardeImpossible
	{
		try {
			Statement intruction = connection.createStatement();
			String requete = "SELECT * FROM employe WHERE IsRoot = 1";
			ResultSet response = intruction.executeQuery(requete);
			if(!response.next()) {
				setRoot();
				getSuperAdmin();
			}
		    return getGestionPersonnel().getRoot();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}
	
	
	
	public Employe getAdmin(Ligue ligue) throws SauvegardeImpossible
	{
		try {
			PreparedStatement intruction;
			intruction = connection.prepareStatement("SELECT * FROM employe WHERE IdEmploye = ?");
			intruction.setInt(1, (ligue.getAdministrateur()).getId());
			ResultSet response = intruction.executeQuery();
			Employe admin = new Employe();
			if(!response.next()) {
				admin=getSuperAdmin();
			}
			else {
				
				String nom = response.getString("NomEmploye");
				String prenom = response.getString("PrenomEmploye");
				String mail =  response.getString("Mail");
			    String password = response.getString("mdp");
			    admin.setNom(nom);
			    admin.setPrenom(prenom);
			    admin.setMail(mail);
			    admin.setPassword(password);
			}
		    return admin;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible 
	{	
		try
		{
			PreparedStatement listEmploye;
			listEmploye = connection.prepareStatement("DELETE FROM employe WHERE idEmploye = ?");
			listEmploye.setInt(1, employe.getId());
			listEmploye.executeUpdate();
			System.out.println("Employe " + employe.getNom() + " supprimé");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}
}

