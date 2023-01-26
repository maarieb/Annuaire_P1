package fr.isika.cda21.Projet1Groupe3.Tests;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda21.Projet1Groupe3.application.ConstantesDAppli;
import fr.isika.cda21.Projet1Groupe3.entites.Stagiaire;
import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.AbrBinaire;
import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.Bloc;
import fr.isika.cda21.Projet1Groupe3.outils.Outils;

public class TestsBloc {

	public static void main(String[] args) {

		try {
			RandomAccessFile  raf =new RandomAccessFile(ConstantesDAppli.getNomFichierBinDate(), "rw");
			Stagiaire jerome = new Stagiaire ("POTIN", "Jérôme", "75", "ATOD 21", 2014);
			Bloc bloc1 = new Bloc(0, -1, jerome, -1, -1, -1);
			Stagiaire marie = new Stagiaire ("POTIN", "Marie", "75", "ATOD 21", 2014);
			Bloc bloc2 = new Bloc(1, -1, marie, -1, -1, -1);
			Stagiaire mila = new Stagiaire ("Vallin", "Mila", "69", "CDA21", 2022);
			Bloc bloc3 = new Bloc(2, -1, mila, -1, -1, -1);
			Stagiaire joachim = new Stagiaire ("Vallin", "Joachim", "69", "CDA21", 2022);
			Bloc bloc4 = new Bloc(3, -1, joachim, -1, -1, -1);
			
			bloc1.ecrireBloc(raf);
			System.out.println(bloc1.lireBlocAIndex(0, raf));
//			bloc1.ajouterDansListeDoublons(bloc2, raf);
//			bloc1=bloc1.lireBlocAIndex(0, raf);
//			bloc1.ajouterDansListeDoublons(bloc3, raf);
//			bloc1=bloc1.lireBlocAIndex(0, raf);
//			bloc1.ajouterDansListeDoublons(bloc4, raf);
//			Outils.afficherFichBinInt(raf);
//			System.out.println();
			
			
			
			/*// test de la méthode ecrireBloc()
			System.out.println("Bloc créé : "+blocRacine);
			blocRacine.ecrireBloc(raf);
			
			System.out.println();
			
			// test de la méthode lireBlocAIndex()
			System.out.println("Bloc lu : "+blocRacine.lireBlocAIndex(0, raf));
			
			System.out.println();
			
			// test de la méthode ecrireFilsG()
			blocRacine.ecrireFilsG(6, raf);
			System.out.println("Bloc lu après MaJ du filsG : "+blocRacine.lireBlocAIndex(0, raf));
			
			System.out.println();*/
			
			// test de la méthode ecrirePere()
			bloc1.ecrirePere(6, raf);
			System.out.println("Bloc lu après MaJ du pere : "+bloc1.lireBlocAIndex(0, raf));
			
			/*
			//test de ajouterBloc()
				// pour racine
			Stagiaire jerome = new Stagiaire ("Vallin", "Jérôme", "69", "CDA21", 2022);
			Bloc blocRacine = new Bloc(-1, -1, jerome, -1, -1);
			blocRacine.ajouterBloc(blocRacine, raf);
			System.out.println("Bloc lu : "+blocRacine.lireBlocAIndex(0, raf));
			
				// pour noeud interne
			Stagiaire mila = new Stagiaire ("Keuro", "Mila", "34", "CDA21", 2022);
			Bloc newBloc = new Bloc(-1, -1, mila, -1, -1);
			System.out.println("Bloc créé : "+newBloc);
			blocRacine.ajouterBloc(newBloc, raf);
			System.out.println("Bloc lu à l'index 0 : "+blocRacine.lireBlocAIndex(0, raf));
			System.out.println("Bloc lu à l'index 1 : "+newBloc.lireBlocAIndex(1, raf));
			*/
			
			//test de creerFichierBinDepuisFichierTxt() dans classe Bloc
			//Bloc.creerFichierBinDepuisFichierTxt();
			
			/*/test de la méthode listeGND() dans classe Bloc
			Stagiaire.afficherListe(blocRacine.listeGND(raf, 0, new ArrayList<>()));
			
			//test de la méthode rechercheNom() dans classe Bloc
			System.out.println("Résultat de la recherche sur le nom 'POTIN' avec Bloc.rechercheNom() :");
			Outils.afficherListe(blocRacine.rechercheNom("POTIN", raf, 0, new ArrayList<>()));
			System.out.println();
			System.out.println("Résultat de la recherche sur le nom 'Potin' avec Bloc.rechercheNom() :");
			Outils.afficherListe(blocRacine.rechercheNom("Potin", raf, 0, new ArrayList<>()));
			System.out.println();
			
			//test de la méthode rechercheNomPartiel() dans classe Bloc
			System.out.println("Résultat de la recherche avec le nom partiel 'PO' avec Bloc.rechercheNomPartiel() :");
			Outils.afficherListe(blocRacine.rechercheNomPartiel("PO", raf, 0, new ArrayList<>()));
			System.out.println();
			
			
			
			//test de la méthode recherchePrenom() dans classe Bloc
			System.out.println("Résultat de la recherche sur le prénom 'Kim' avec Bloc.recherchePrenom() :");
			Outils.afficherListe(blocRacine.recherchePrenom("Kim", raf, 0, new ArrayList<>()));
			System.out.println();
			System.out.println("Résultat de la recherche sur le prenom 'anne' avec Bloc.recherchePrenom() :");
			Outils.afficherListe(blocRacine.recherchePrenom("anne", raf, 0, new ArrayList<>()));
			System.out.println();
			
			//test de la méthode recherchePrenomPartiel() dans classe Bloc
			System.out.println("Résultat de la recherche avec le prénom partiel 'Ro' avec Bloc.recherchePrenomPartiel() :");
			Outils.afficherListe(blocRacine.recherchePrenomPartiel("Ro", raf, 0, new ArrayList<>()));
			System.out.println();
			System.out.println("Résultat de la recherche avec le prénom partiel 'ka' avec Bloc.recherchePrenomPartiel() :");
			Outils.afficherListe(blocRacine.recherchePrenomPartiel("ka", raf, 0, new ArrayList<>()));
			System.out.println();*/

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
