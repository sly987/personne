package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;
import static commandLineMenus.rendering.examples.util.InOut.getInt;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.SauvegardeImpossible;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDateArrivee(employe));
			menu.add(changerDateDepart(employe));
			menu.addBack("q");
			return menu;
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
	private Option changerDateArrivee(final Employe employe)
	{
		return new Option("Changer la date d'arrivée", "a", () -> {
			try
			{
				employe.setDateA(getInt("Nouvelle année : "), getInt("Nouveau mois : "),getInt("Nouveau jour : ") );
			}
			catch(SauvegardeImpossible e)
			{
				System.err.println("erreur sur saisie des dates ");
			}
		});
	}
	private Option changerDateDepart(final Employe employe) 
	{
		return new Option("Changer le date de départ", "d", () -> {	
			try
			{
				employe.setDateA(getInt("Nouvelle année : "), getInt("Nouveau mois : "),getInt("Nouveau jour : ") );
			}
			catch(SauvegardeImpossible e)
			{
				System.err.println("erreur sur saisie des dates ");
			}
			});
	}
}
