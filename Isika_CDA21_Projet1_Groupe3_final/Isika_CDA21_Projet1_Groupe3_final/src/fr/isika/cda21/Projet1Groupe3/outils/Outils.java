// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.outils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda21.Projet1Groupe3.application.ConstantesDAppli;
import fr.isika.cda21.Projet1Groupe3.entites.Stagiaire;

//Classe définissant un certain nombre de méthodes utilitaires
public class Outils {
	
	//méthode de recherche dans une liste selon le prénom
	//la liste vient d'une précédente recherche (dans l'arbre ou d'une liste)
	public static List<Stagiaire> recherchePrenomDansListe(List<Stagiaire> listeInitiale, String prenom, boolean partiel) {
		List<Stagiaire> res = new ArrayList<>();
		for (Stagiaire leStagiaire : listeInitiale) {
			String prenomATester = prenomNormalise(leStagiaire.getPrenom());	
			if (partiel && prenom.length()<prenomATester.length()) {					
				prenomATester = prenomATester.substring(0, prenom.length());			
			}
			if (prenomATester.equals(prenom)) {
				res.add(leStagiaire);
			}
		}
		return res;
	}
	
	//méthode de recherche dans une liste selon le département
	//la liste vient d'une précédente recherche (dans l'arbre ou d'une liste)
	public static List<Stagiaire> rechercheDepDansListe(List<Stagiaire> listeInitiale, String dep) {
		List<Stagiaire> res = new ArrayList<>();
		for (Stagiaire leStagiaire : listeInitiale) {
			if (leStagiaire.getDep().equals(dep)) {
				res.add(leStagiaire);
			}
		}
		return res;
	}
	
	//méthode de recherche dans une liste selon la promo
	//la liste vient d'une précédente recherche (dans l'arbre ou d'une liste)
	public static List<Stagiaire> recherchePromoDansListe(List<Stagiaire> listeInitiale, String promo) {
		List<Stagiaire> res = new ArrayList<>();
		for (Stagiaire leStagiaire : listeInitiale) {
			if (leStagiaire.getPromo().contains(promo)) {
				res.add(leStagiaire);
			}
		}
		return res;
	}
	
	//méthode de recherche dans une liste selon l'année
	//la liste vient d'une précédente recherche (dans l'arbre ou d'une liste)
	public static List<Stagiaire> rechercheAnneeDansListe(List<Stagiaire> listeInitiale, int annee) {
		List<Stagiaire> res = new ArrayList<>();
		for (Stagiaire leStagiaire : listeInitiale) {
			if (leStagiaire.getAnneeF()==annee) {
				res.add(leStagiaire);
			}
		}
		return res;
	}
	
	
	// Normalise l'écriture des prénoms pour les recherches
	public static String prenomNormalise(String prenom) {			// Faire une classe PrenomNormalise ?
		String res=prenom.replace("é", "e");
		res = res.replace("è", "e");
		res = res.replace("ê", "e");
		res = res.replace("ë", "e");
		res = res.replace("à", "a");
		res = res.replace("â", "a");
		res = res.replace("ä", "a");
		res = res.replace("î", "i");
		res = res.replace("ï", "i");
		res = res.replace("ô", "o");
		res = res.replace("ö", "o");
		res = res.replace("û", "u");
		res = res.replace("ü", "u");
		res = res.replace("ÿ", "y");
		return res.toLowerCase();
	}
	
	// Vérifie le format d'une date entrée
		public static boolean verifieDate(String date) {
			boolean res=true;
			int nbChar = date.length();
			if (nbChar!=4) {
				res = false;
			}
			else {
				for (int k=0; k<4; k++) {
					int c = date.charAt(k);
					if (c<48 || c>57 || (k==0 && (c!=48 && c!=50))) {
						res=false;
					}
				}
			}
			return res;
		}
		
		
	// **************************** Outils de développement ********************************
		
	// Affiche une liste de stagiaires à la console
	public static void afficherListe(List<Stagiaire> liste) {
		for (Stagiaire stag : liste) {
			System.out.println(stag);
		}
	}

	// Lit un fichier binaire comme s'il ne contenait que des int et l'affiche à la console
	public static void afficherFichBinInt(RandomAccessFile raf)
	{
		try {
			System.out.println("Lecture du fichier .bin mode 'int'");
			raf.seek(0);
			for (int k=0; k<raf.length()/4; k++) {
				System.out.print(raf.readInt()+" ");
				if ((k+1)%(ConstantesDAppli.TAILLE_BLOC/4)==0) {
					System.out.println();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
