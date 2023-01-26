package fr.isika.cda21.Projet1Groupe3.Tests;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda21.Projet1Groupe3.application.ConstantesDAppli;
import fr.isika.cda21.Projet1Groupe3.entites.Stagiaire;
import fr.isika.cda21.Projet1Groupe3.entites.StagiaireARechercher;
import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.AbrBinaire;
import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.Bloc;
import fr.isika.cda21.Projet1Groupe3.outils.Outils;

public class TestsAbrBinaire {
	

	public static void main(String[] args) {
		
		long t1, t2;

		try {
			RandomAccessFile  raf =new RandomAccessFile(ConstantesDAppli.getNomFichierBinDate(), "rw");
			
			AbrBinaire monABR = new AbrBinaire();
			
			/*// test de la méthode ajouterStagiaire()
				// pour racine
			Stagiaire newStag = new Stagiaire ("Vallin", "Jérôme", "69", "CDA21", 2022);
			System.out.println("Stagiaire créé : "+newStag);
			monABR.ajouterStagiaire(newStag, raf);
			System.out.println("ABR : "+monABR);
			System.out.println();
			
				// pour bloc interne
			newStag = new Stagiaire ("Keuro", "Mila", "34", "CDA21", 2022);
			System.out.println("Stagiaire créé : "+newStag);
			monABR.ajouterStagiaire(newStag, raf);
			System.out.println("ABR : "+monABR);
			System.out.println("Racine lue : "+monABR.getRacine().lireBlocAIndex(0, raf));
			System.out.println();*/
			
			//test de la méthode creerFichierBinDepuisFichierTxt() dans classe AbrBinaire (avec listes de doublons)
			monABR.creerFichierBinDepuisFichierTxt(raf);
			System.out.println();
			
			//Outils.afficherFichBinInt(raf);
			
//			Stagiaire jerome = new Stagiaire ("POTIN", "Thomas", "75", "ATOD 21", 2014);
//			Bloc bloc1 = new Bloc(0, -1, jerome, -1, -1, -1);
//			Stagiaire marie = new Stagiaire ("POTIN", "Mila", "75", "ATOD 21", 2014);
//			Bloc bloc2 = new Bloc(1, -1, marie, -1, -1, -1);
//			Stagiaire mila = new Stagiaire ("KLEBER", "Jerome", "75", "ATOD 21", 2014);
//			Bloc bloc3 = new Bloc(2, -1, mila, -1, -1, -1);
			
//			System.out.println(bloc1.lireBlocAIndex(0, raf));
//			System.out.println(bloc1.lireBlocAIndex(1, raf));
//			System.out.println();
//			
			//Outils.afficherListe(monABR.listeGND(raf));
			System.out.println();
			
			// test de la méthode supprimer()
			// 1. pour une feuille de la liste
  			Stagiaire lacroix = new Stagiaire ("LACROIX", "Pascale", "91", "BOBI 5", 2008);
//			monABR.supprimer(stag, raf);
			Stagiaire ung = new Stagiaire ("UNG", "Jet-Ming", "75", "ATOD 16 CP", 2012);
			// 2. pour un bloc ayant un suivant (doublon)
			Stagiaire potinM = new Stagiaire ("POTIN", "Marie", "75", "ATOD 21", 2014);  // à l'intérieur de la liste
			Stagiaire kleber = new Stagiaire ("KLEBER", "Jerome", "75", "ATOD 21", 2014);  // en tête de liste, sans fils
			Stagiaire potinT = new Stagiaire ("POTIN", "Thomas", "75", "ATOD 21", 2014);	   // en tête de liste avec fils
			Stagiaire augereau = new Stagiaire ("AUGEREAU", "Kévin", "76", "AI 78", 2010);	// la racine
			Stagiaire chaveneau1 = new Stagiaire ("CHAVENEAU", "Kim Anh", "92", "ATOD 22", 2014);	// la racine
			Stagiaire chaveneau2 = new Stagiaire ("CHAVENEAU", "Cécile", "93", "ATOD 22", 2014);	// la racine
			
			monABR.modifier(chaveneau1, potinT, raf);
						
			Outils.afficherListe(monABR.listeGND(raf));
			
//			for (int i=0; i<raf.length()/ConstantesDAppli.TAILLE_BLOC; i++) {
//				System.out.println(Bloc.lireBlocAIndex(i, raf));
//			}
			
//			
//			// test de la méthode rechercheBloc()
//			System.out.println(monABR.rechercheBloc(jerome, raf));
//			System.out.println(monABR.rechercheBloc(marie, raf));
//			System.out.println(monABR.rechercheBloc(mila, raf));
//					
			
			//test de la méthode listeGND() dans classe AbrBinaire
			//System.out.println("Annuaire complet :");
			//Outils.afficherListe(monABR.listeGND(raf));
			System.out.println();
			
			//test de la méthode rechercheNom() dans classe AbrBinaire
//			System.out.println("Résultat de la recherche sur le nom 'POTIN' avec AbrBinaire.rechercheNom() :");
//			Outils.afficherListe(monABR.rechercheNom("POTIN", raf));
//			System.out.println();
//			System.out.println("Résultat de la recherche sur le nom 'Potin' avec AbrBinaire.rechercheNom() :");
//			t1 = System.nanoTime();
//			Outils.afficherListe(monABR.rechercheNom("Potin", raf));
//			t2 = System.nanoTime();
//			System.out.println("temps : "+(t2-t1));
//			System.out.println();
//			System.out.println("Résultat de la recherche avec le nom partiel 'Potin' avec AbrBinaire.rechercheNomPartiel() :");
//			t1 = System.nanoTime();
//			Outils.afficherListe(monABR.rechercheNomPartiel("Potin", raf));
//			t2 = System.nanoTime();
//			System.out.println();
//			System.out.println("temps : "+(t2-t1));
//			
//			//test de la méthode recherchePrenom() dans classe AbrBinaire
//			System.out.println("Résultat de la recherche sur le prénom 'Anne' avec AbrBinaire.recherchePrenom() :");
//			Outils.afficherListe(monABR.recherchePrenom("Anne", raf, false));
//			System.out.println();
//			System.out.println("Résultat de la recherche sur le prénom 'anne' avec AbrBinaire.recherchePrenom() :");
//			Outils.afficherListe(monABR.recherchePrenom("anne", raf, false));
//			System.out.println();
//			System.out.println("Résultat de la recherche avec le prénom partiel 'da' avec AbrBinaire.recherchePrenom() :");
//			Outils.afficherListe(monABR.recherchePrenom("da", raf, true));
//			System.out.println();
//			
//			//test de la méthode prenomNormalise()
//			System.out.println("Résultat de la recherche avec le prénom 'Jérôme' avec AbrBinaire.recherchePrenom() :");
//			Outils.afficherListe(monABR.recherchePrenom("Jérôme", raf, false));
//			System.out.println();
		
//			//test de la méthode recherchePromo() dans la classe AbrBinaire
//			System.out.println("Résultat de la recherche avec la promo 'ATOD' avec AbrBinaire.recherchePromo() :");
//			Outils.afficherListe(monABR.recherchePromo("ATOD", raf));
//			System.out.println();
			
			//test de la méthode rechercheDep() dans la classe AbrBinaire
//			System.out.println("Résultat de la recherche avec le departement '69' avec AbrBinaire.rechercheDep() :");
//			Outils.afficherListe(monABR.rechercheDep("69", raf));
//			System.out.println();
			
			//test de la méthode rechercheAnnee() dans la classe AbrBinaire
//			System.out.println("Résultat de la recherche avec l'année '2014' avec AbrBinaire.rechercheAnnee() :");
//			Outils.afficherListe(Outils.recherchePromoDansListe(monABR.rechercheAnnee(2014, raf),"ATOD"));
//			System.out.println();
			
			//recherche multiple
//			System.out.println("Résultat de la recherche multiple (Nom partiel -> année puis promo :");
//			Outils.afficherListe(Outils.recherchePromoDansListe(Outils.rechercheAnneeDansListe(monABR.rechercheNomPartiel("BOU", raf),2014),"ATOD 21"));
//			System.out.println();
			
//			//test méthode recherche() 
//			StagiaireARechercher newStag = new StagiaireARechercher ("", "j", "94", "", 2008, true, true);
//			Outils.afficherListe(monABR.recherche(newStag, raf));
//			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
