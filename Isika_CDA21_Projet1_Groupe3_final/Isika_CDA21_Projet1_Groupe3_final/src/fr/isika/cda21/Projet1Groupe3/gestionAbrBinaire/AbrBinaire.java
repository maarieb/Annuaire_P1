// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda21.Projet1Groupe3.application.ConstantesDAppli;
import fr.isika.cda21.Projet1Groupe3.entites.Stagiaire;
import fr.isika.cda21.Projet1Groupe3.entites.StagiaireARechercher;
import fr.isika.cda21.Projet1Groupe3.outils.Outils;

// Classe définissant un Arbre Binaire de Recherche ; a en attribut l'index du Bloc racine.
public class AbrBinaire {

	int racine;
	
	// ********** CONSTRUCTEURS ***********
	public AbrBinaire() {
		this.racine = -1;
	}
	
	// ********** METHODES SPECIFIQUES ***********
	
	// Ajoute le Bloc 'blocAAjouter' à l'arbre : détermine son index et demande à la racine de trouver sa place dans l'ABR
	public boolean ajouterStagiaire(Stagiaire stagAAjouter, RandomAccessFile raf) {  // Return 'false' si 'stagAAjouter' est déjà présent
		
		try {
			if (this.racine==-1) { 		// arbre vide -> créer la racine
				this.racine = 0;
				Bloc blocRacine = new Bloc(0, -1, stagAAjouter, -1, -1, -1);
				blocRacine.ecrireBloc(raf);	
				return true;
			}
			else {
				Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);		// Lecture de la racine
				int indexNouveauBloc = (int)raf.length()/ConstantesDAppli.TAILLE_BLOC;  	// détermination de l'index
				Bloc blocAAjouter = new Bloc(indexNouveauBloc, -1, stagAAjouter, -1, -1, -1);
				return(blocRacine.ajouterBloc(blocAAjouter, raf));	// lance la méthode récursive ajouterBLoc, appelée depuis la racine
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Crée un ABR sous forme de fichier binaire à partir des infos du fichier .txt
	public void creerFichierBinDepuisFichierTxt(RandomAccessFile annuaireBin) {
		File annuaireTxt = new File(ConstantesDAppli.getNomFichierTxt());		// fichier à lire
		try {
			FileReader fr = new FileReader(annuaireTxt);
			BufferedReader br = new BufferedReader(fr);
			
			while(br.ready()) {
				String nom = br.readLine().toUpperCase();				// lecture des attributs du Stagiaire
				String prenom = br.readLine();
				String dep = br.readLine();
				String promo = br.readLine();
				int anneeF = Integer.parseInt(br.readLine());
				br.readLine();											// pour passer la ligne *
				
				Stagiaire nouveauStagiaire = new Stagiaire(nom, prenom, dep, promo, anneeF);    // crée le nouveau stagiaire
				this.ajouterStagiaire(nouveauStagiaire, annuaireBin);	// ajout du nouveau stagiaire à l'ABR
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Lance la lecture du fichier .bin en parcours GND pour créer une liste dans l'ordre alphabétique
	public List<Stagiaire> listeGND(RandomAccessFile raf) {
		if (this.racine==-1) {
			return new ArrayList<>();  // ou null ? La liste vide permet d'utiliser un 'for each'
		}
		else {
			Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf); 			// lecture de la racine
			return blocRacine.listeGND(raf, racine, new ArrayList<>());	// lancement de la méthode récursive listeGND, appelée par la racine
		}
	}

	// Lance la lecture du fichier .bin en parcours GND pour créer une liste des stagiaires de nom égal à 'nom'
	public List<Stagiaire> rechercheNom(String nom, RandomAccessFile raf) {
		if (this.racine==-1) {
			return new ArrayList<>();
		}
		else {
			Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);		// lecture de la racine
			return blocRacine.rechercheNom(nom.toUpperCase(), raf, racine, new ArrayList<>());	// lancement de la méthode récursive
		}
	}

	// Lance la lecture du fichier .bin en parcours GND pour créer une liste des stagiaires de nom commençant par 'nom'
	public List<Stagiaire> rechercheNomPartiel(String nom, RandomAccessFile raf) {
		if (this.racine==-1) {
			return new ArrayList<>();
		}
		else {
			Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);		// lecture de la racine
			return blocRacine.rechercheNomPartiel(nom.toUpperCase(), raf, racine, new ArrayList<>());	// lancement de la méthode récursive
		}
	}

	// Lance la lecture du fichier .bin en parcours GND pour créer une liste des stagiaires de prénom égal à (ou commençant par) 'prenom'
	public List<Stagiaire> recherchePrenom(String prenom, RandomAccessFile raf, boolean partiel) {
		if (this.racine==-1) {
			return new ArrayList<>();
		}
		else {
			Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);		// lecture de la racine
			return blocRacine.recherchePrenom(Outils.prenomNormalise(prenom), raf, racine, new ArrayList<>(), partiel);	// lancement de la méthode récursive
		}
	}
	
	// Lance la lecture du fichier .bin en parcours GND pour créer une liste des stagiaires de département égal à 'dep'
			public List<Stagiaire> rechercheDep(String dep, RandomAccessFile raf) {
				if (this.racine==-1) {
					return new ArrayList<>();
				}
				else {
					Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);	// lecture de la racine
					return blocRacine.rechercheDep(dep,raf, racine, new ArrayList<>());	// lancement de la méthode récursive
				}
			}
	
	// Lance la lecture du fichier .bin en parcours GND pour créer une liste des stagiaires de promo égal à (ou commençant par) 'promo'
		public List<Stagiaire> recherchePromo(String promo, RandomAccessFile raf) {
			if (this.racine==-1) {
				return new ArrayList<>();
			}
			else {
				Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);		// lecture de la racine
				return blocRacine.recherchePromo(promo,raf, racine, new ArrayList<>());	// lancement de la méthode récursive
			}
		}
		
	// Lance la lecture du fichier .bin en parcours GND pour créer une liste des stagiaires d'année égale à 'annee'
		public List<Stagiaire> rechercheAnnee(int annee, RandomAccessFile raf) {
			if (this.racine==-1) {
				return new ArrayList<>();
			}
			else {
					Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);		// lecture de la racine
					return blocRacine.rechercheAnnee(annee,raf, racine, new ArrayList<>());	// lancement de la méthode récursive
				}
			}	
		
	//Lance une recherche multicritère. 'stagiaire' contient deux booléens pour savoir si les recherches 'nom' et 'prenom' sont totales ou partielles
		public List<Stagiaire> recherche(StagiaireARechercher stagiaire, RandomAccessFile raf) {
			List<Stagiaire> res = new ArrayList<>();
			boolean premiereRecherche = true;
			//recherche nom = prioritaire
			if (!(stagiaire.getNom().equals(""))) {
				if (stagiaire.isNomPartiel()) {
					res = this.rechercheNomPartiel(stagiaire.getNom().toUpperCase(), raf);	// la structure ABR permet de localiser rapidement la zone où se trouvent les personnes recherchées	
				} else {
					res = this.rechercheNom(stagiaire.getNom().toUpperCase(), raf);  // seul cas où la structure ABR est pleinement utilisée
				}
			premiereRecherche = false;
			} 
			//ensuite recherche prénom - si vide on passe au prochain critère
			//on choisit la méthode de recherche en fonction du boolean premiereRecherche (dans l'arbre si true, dans une liste si false)
			if (!(stagiaire.getPrenom().equals(""))) {
				String prenomATester = Outils.prenomNormalise(stagiaire.getPrenom());
				if (premiereRecherche){
					res = this.recherchePrenom(prenomATester, raf, stagiaire.isPrenomPartiel());
					premiereRecherche = false;
				} else {
					res = Outils.recherchePrenomDansListe(res, prenomATester,stagiaire.isPrenomPartiel());
				}
			}
			//recherche département - si vide on passe au prochain critère
			//on choisit la méthode de recherche en fonction du boolean premiereRecherche (dans l'arbre si true, dans une liste si false)
			if (!(stagiaire.getDep().equals(""))) {
				if (premiereRecherche){
					res = this.rechercheDep(stagiaire.getDep(), raf);
					premiereRecherche = false;
				} else {
					res = Outils.rechercheDepDansListe(res, stagiaire.getDep());
				}
			}
			//recherche promo - si vide on passe au prochain critère
			//on choisit la méthode de recherche en fonction du boolean premiereRecherche (dans l'arbre si true, dans une liste si false)
			if (!(stagiaire.getPromo().equals(""))) {
				if (premiereRecherche){
					res = this.recherchePromo(stagiaire.getPromo().toUpperCase(), raf);
					premiereRecherche = false;
				} else {
					res = Outils.recherchePromoDansListe(res, stagiaire.getPromo());
				}
			}
			//recherche annee - dernier critère
			//on choisit la méthode de recherche en fonction du boolean premiereRecherche (dans l'arbre si true, dans une liste si false)
			if (stagiaire.getAnneeF()>0) {
				if (premiereRecherche){
					res = this.rechercheAnnee(stagiaire.getAnneeF(), raf);
				} else {
					res = Outils.rechercheAnneeDansListe(res, stagiaire.getAnneeF());
				}
			}
			return res;
		}
		
	// Recherche un bloc dans l'ABR (pour suppression). Retourne null si le stagiaire n'est pas dans la BDD
	public Bloc rechercheBloc(Stagiaire stagASupprimer,RandomAccessFile raf) {
		if (this.racine==-1) {
			return null;
		}
		else {
			Bloc blocRacine=Bloc.lireBlocAIndex(racine, raf);	// lecture de la racine
			return blocRacine.rechercheBloc(stagASupprimer, raf);  // lancement de la méthode récursive
		}
	}	
		
	// Suppresssion du stagiaire 'stagASupprimer' dans l'ABR. Retourne 'false' si le stagiaire n'est pas dans l'annuaire
	// !!!  Méthode trop longue, à scinder. Certaines parties relèveraient plus de la classe Blox ?  !!!
	public boolean supprimer(Stagiaire stagASupprimer, RandomAccessFile raf) {
		
		Bloc blocADesindexe = rechercheBloc(stagASupprimer, raf);  // recherche la position de 'stagASupprimer' dans l'ABR
		if (blocADesindexe==null) {
			return false;
		}
		else {
			int index = blocADesindexe.getIndex();    // liens concernant le bloc du stagiaire à supprimer
			int pere = blocADesindexe.getPere();
			int filsG = blocADesindexe.getFilsG();
			int filsD = blocADesindexe.getFilsD();
			int indexDoublon = blocADesindexe.getIndexDoublon();
			
			if (filsG==-1 && filsD==-1 && indexDoublon ==-1) {// Cas 1 : feuille ou cellule terminale d'une liste de doublons
				if (pere == -1) {			// sous-cas 'racine' -> plus d'ABR !
					this.racine = -1;
				}
				else {
					Bloc blocPere = Bloc.lireBlocAIndex(pere, raf);  // on cherche le père
					if (blocPere.getFilsG()==index) {						//
						blocPere.ecrireFilsG(-1, raf);						//
					} 														//
				else if (blocPere.getFilsD()==index) {						// on met à -1 l'index du père qui contenait 'index'
						blocPere.ecrireFilsD(-1, raf);						//
					} 														//
					else {													//
						blocPere.ecrireIndexDoublon(-1, raf);				//
					}
				}
			}
			else if (indexDoublon!=-1) {				// Cas 2 : j'ai un suivant dans la liste des homonymes
				Bloc blocSuivant = Bloc.lireBlocAIndex(indexDoublon, raf);
				blocSuivant.ecrirePere(pere,raf);				// mon père devient le père de mon suivant (éventuellement -1)
				if (filsG!=-1) {													//
					blocSuivant.ecrireFilsG(filsG, raf);							//
					Bloc.lireBlocAIndex(filsG, raf).ecrirePere(indexDoublon,raf);	//
				}																	// mon suivant récupère mes éventuels fils
				if (filsD!=-1) {													//
					blocSuivant.ecrireFilsD(filsD, raf);							//
					Bloc.lireBlocAIndex(filsD, raf).ecrirePere(indexDoublon,raf);	//
				}
				if (pere==-1) {  	// sous-cas racine
					this.racine = indexDoublon;  // la racine est désormais mon suivant
				}
				else { 	// j'ai un suivant mais je ne suis pas la racine : màj du père
					Bloc blocPere = Bloc.lireBlocAIndex(pere, raf);   // on cherche le père 
					if (blocPere.getFilsG()==index) {						//
					blocPere.ecrireFilsG(indexDoublon, raf);				//
					}														//
					else if (blocPere.getFilsD()==index) {					// on met 'indexDoublon' à l'index du père qui contenait 'index'
						blocPere.ecrireFilsD(indexDoublon, raf);			//
					}														//
					else {													//
						blocPere.ecrireIndexDoublon(indexDoublon, raf);		//  !!!  Déjà présent dans cas 1 -> méthode à part
					}
				}
			}
			else if (filsG == -1 || filsD == -1) {	//Cas 3 : noeud qui a un seul fils et pas de doublon
				// !!!  ce cas semble se traiter comme le précédent, pourrait-on les réunir ? !!!
				int fils = filsG;
				if (filsG == -1) {		//recherche du fils
					fils = filsD;
				}
				Bloc.lireBlocAIndex(fils, raf).ecrirePere(pere, raf);	// mon père devient le père de mon fils (éventuellement -1)
				if (pere == -1) { 		// sous-cas racine
					this.racine = fils;         // mon fils devient la racine
				}	
				else {
					Bloc blocPere = Bloc.lireBlocAIndex(pere, raf);	// on cherche le père 
						if (blocPere.getFilsG()==index) { 				//
							blocPere.ecrireFilsG(fils,raf);  			//
						}												// on met 'fils' à l'index du père qui contenait 'index'
						else {											//
						blocPere.ecrireFilsD(fils,raf); 				//
						}
				}
			}
			else { 				// Cas 4 : deux fils et pas de doublon
				int minDesMax = filsD;
				while(Bloc.lireBlocAIndex(minDesMax, raf).getFilsG() != -1) {	// recherche du plus petit majorant
					minDesMax = Bloc.lireBlocAIndex(minDesMax, raf).getFilsG();
				}
				Bloc blocADeplacer = Bloc.lireBlocAIndex(minDesMax, raf);	// lecture du bloc qui va venir me remplacer
				// Maj des enfants du bloc à déplacer : 
				// 1. mon filsG devient son filsG
				blocADeplacer.ecrireFilsG(filsG, raf);
				Bloc.lireBlocAIndex(filsG, raf).ecrirePere(minDesMax, raf);
				if (minDesMax!=filsD) {
					// 2. son filsD devient le filsG de son père
					if (blocADeplacer.getFilsD()==-1) {
						Bloc.lireBlocAIndex(blocADeplacer.getPere(), raf).ecrireFilsG(-1, raf);
					}
					else {
						Bloc.lireBlocAIndex(blocADeplacer.getPere(), raf).ecrireFilsG(blocADeplacer.getFilsD(), raf);
						Bloc.lireBlocAIndex(blocADeplacer.getFilsD(), raf).ecrirePere(blocADeplacer.getPere(), raf);
					}
					// 3. mon filsD devient son filsD
					blocADeplacer.ecrireFilsD(filsD, raf);
					Bloc.lireBlocAIndex(filsD, raf).ecrirePere(minDesMax, raf);
				}
				// Maj du père du bloc à déplacer
				blocADeplacer.ecrirePere(pere, raf);
				if (pere==-1) {			// sous-cas racine
					this.racine=minDesMax;
				}
				else {
					Bloc blocPere = Bloc.lireBlocAIndex(pere, raf); //
					if (blocPere.getFilsG()==index) { 				//
						blocPere.ecrireFilsG(minDesMax,raf);  		//		encore !!
					}												//
					else {											//
						blocPere.ecrireFilsD(minDesMax,raf); 		//
					}
				}
			}
			return true;
		}
	}	

	// Modifie un stagiaire de façon brutale : ajoute 'nouveauStag' puis, si l'ajout s'est bien passé, supprime 'ancienStag'
	// Deprecated
	public boolean modifier(Stagiaire ancienStag, Stagiaire nouveauStag, RandomAccessFile raf) {
		boolean res = ajouterStagiaire(nouveauStag, raf);
		if (res) {
			supprimer(ancienStag, raf);
		}
		return res;
	}

	// Modifie un stagiaire : si même nom, modifie les infos du bloc concerné, sinon ajoute puis supprime. Retourne 'false' si 'nouveauStag' déjà dans l'annuaire
	public boolean modifier2(Stagiaire ancienStag, Stagiaire nouveauStag, RandomAccessFile raf) {
		boolean res = true;
		if (ancienStag.getNom().equals(nouveauStag.getNom())) {		// le nom de nouveauStag est déjà présent dans l'annuaire : il suffit de modifier les infos du bloc dans le fichier .bin
			if (recherche(new StagiaireARechercher(nouveauStag,false,false), raf).size()==1) {  // si 'nouveauStag' est déjà dans l'annuaire
				res = false;																		// pas d'ajout et retourne 'false'
			}
			else {														//  si 'nouveauStag' n'est pas déjà dans l'annuaire
				Bloc blocAncienStag = rechercheBloc(ancienStag, raf);		// on recherche le bloc de 'ancienStag'
				blocAncienStag.setCle(nouveauStag);							// on met à jour les infos du stagiaire
				blocAncienStag.ecrireBloc(raf);
			}
		}
		else {														// le nom de 'nouveauStag' est absent de l'annuaire : on crée nouveauStag et on supprime ancienStag
			res = ajouterStagiaire(nouveauStag, raf);					// ajout ; si 'nouveauStag' deja dans l'annuaire, pas d'ajout et retourne 'false'
			if (res) {													// si l'ajout a été effectué
			supprimer(ancienStag, raf);										// suppression de 'ancienStag'
			}
		}
		return res;
	}

	@Override
	public String toString() {
		if (this.racine==-1) {
			return "Arbre vide";
		} else {
		return "Arbre de racine : "+racine;
		}
	}

	
	// ********** Getters et Setters ***********
	public int getRacine() {
		return racine;
	}

	public void setRacine(int racine) {
		this.racine = racine;
	}
	
}
