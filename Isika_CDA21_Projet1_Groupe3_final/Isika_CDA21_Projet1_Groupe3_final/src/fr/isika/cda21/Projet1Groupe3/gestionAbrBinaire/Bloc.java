// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import fr.isika.cda21.Projet1Groupe3.application.ConstantesDAppli;
import fr.isika.cda21.Projet1Groupe3.entites.Stagiaire;
import fr.isika.cda21.Projet1Groupe3.outils.Outils;

public class Bloc {

	int index;
	int pere;
	private Stagiaire cle;
	private int filsG;
	private int filsD;
	private int indexDoublon;
	
	// ********** CONSTRUCTEURS ***********
	public Bloc(int index, int pere, Stagiaire cle, int filsG, int filsD, int indexDoublon) {
		this.index = index;
		this.pere = pere;
		this.cle = cle;
		this.filsG = filsG;
		this.filsD = filsD;
		this.indexDoublon = indexDoublon;
	}
	
	
	// ********** METHODES SPECIFIQUES ***********
	
	// Ecrit un bloc dans le fichier bin (à la position this.index)
	public void ecrireBloc(RandomAccessFile raf) {	
		try {
			raf.seek(this.index*ConstantesDAppli.TAILLE_BLOC);
			raf.writeInt(this.index);
			raf.writeInt(this.pere);
			raf.writeChars(this.cle.nomLong());
			raf.writeChars(this.cle.prenomLong());
			raf.writeChars(this.cle.depLong());
			raf.writeChars(this.cle.promoLong());
			raf.writeInt(this.cle.getAnneeF());
			raf.writeInt(this.filsG);
			raf.writeInt(this.filsD);
			raf.writeInt(this.indexDoublon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Met à jour l'index du bloc appelant dans le fichier 
	public void ecrireIndex(int nouvelIndex, RandomAccessFile raf) {
		try {
			raf.seek((this.index)*ConstantesDAppli.TAILLE_BLOC);
			raf.writeInt(nouvelIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Met à jour le pere du bloc appelant dans le fichier 
		public void ecrirePere(int nouvelIndex, RandomAccessFile raf) {
			try {
				raf.seek((this.index)*ConstantesDAppli.TAILLE_BLOC+4);
				raf.writeInt(nouvelIndex);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	// Met à jour le filsG du bloc appelant dans le fichier 
	public void ecrireFilsG(int nouvelIndex, RandomAccessFile raf) {
		try {
			raf.seek((this.index+1)*ConstantesDAppli.TAILLE_BLOC-12);
			raf.writeInt(nouvelIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Met à jour le filsD du bloc appelant dans le fichier 
		public void ecrireFilsD(int nouvelIndex, RandomAccessFile raf) {
			try {
				raf.seek((this.index+1)*ConstantesDAppli.TAILLE_BLOC-8);
				raf.writeInt(nouvelIndex);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Met à jour le filsD du bloc appelant dans le fichier 
		public void ecrireIndexDoublon(int nouvelIndex, RandomAccessFile raf) {
			//System.out.println("Bloc appelant ecrire index doublon : "+this);
			//System.out.println("nouvel index : "+nouvelIndex);
			try {
				raf.seek((this.index+1)*ConstantesDAppli.TAILLE_BLOC-4);
				raf.writeInt(nouvelIndex);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Lit une String dans le fichier bin et se positionne au début de la donnée suivante. TailleMax varie selon l'attribut à lire
		public static String lireString(int tailleMax, RandomAccessFile raf) { // à mettre dans la classe outils ?
	 		String res = "";
			for (int j=0; j<tailleMax; j++) {
				char c;
				try {
					c = raf.readChar();
					if (c!='*') res+=c;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			return res;
		}
		
	// Lit un bloc du fichier .bin, celui en position 'index'
	public static Bloc lireBlocAIndex(int index, RandomAccessFile raf) {	// à mettre dans la classe outils ?
		
		Bloc res = new Bloc(index, -1, new Stagiaire(), -2, -2, -2);
		try {
			raf.seek(index*ConstantesDAppli.TAILLE_BLOC);
			res.index=raf.readInt();
			res.pere=raf.readInt();
			res.cle.setNom(lireString(ConstantesDAppli.TAILLE_MAX_NOM, raf));
			res.cle.setPrenom(lireString(ConstantesDAppli.TAILLE_MAX_PRENOM, raf));
			res.cle.setDep(lireString(ConstantesDAppli.TAILLE_MAX_DEP, raf));
			res.cle.setPromo(lireString(ConstantesDAppli.TAILLE_MAX_PROMO, raf));
			res.cle.setAnneeF(raf.readInt());
			res.filsG=raf.readInt();
			res.filsD=raf.readInt();
			res.indexDoublon=raf.readInt();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	// Ajoute un bloc dans la liste des doublons de 'blocAAjouter.getCle.getNom'. 'blocAAjouter' contient déjà son Index, la méthode doit établir les liens suivant/précédant
	public boolean ajouterDansListeDoublons(Bloc blocAAjouter, RandomAccessFile raf) {
		if (blocAAjouter.cle.compareTo(this.cle)==0) {	// je contiens le stagiaire à ajouter
			return false;
		}
		else if (this.indexDoublon==-1) {				// je suis en fin de liste
			this.ecrireIndexDoublon(blocAAjouter.getIndex(), raf);  	// MaJ de mon index doublon
			blocAAjouter.setPere(this.index);				 			// MaJ du père du bloc à ajouter
			blocAAjouter.ecrireBloc(raf);								// Ecriture du bloc dans le fichier .bin
			return true;
		}
		else {											// j'ai un suivant
			lireBlocAIndex(this.indexDoublon, raf).ajouterDansListeDoublons(blocAAjouter, raf); // je lui refile le boulot d'ajouter à la liste
			return true;
		}
	}
	
	// Charge le bloc_appelant de placer le bloc 'blocAAjouter' (ie MaJ d'un des fils du père). Méthode récursive. 'blocAAjouter' contient déjà son Index, la méthode doit établir les liens fils/père
	public boolean ajouterBloc(Bloc blocAAjouter, RandomAccessFile raf) {
		boolean res;
		if (blocAAjouter.cle.compareTo(this.cle)==0) {			 		// je contiens le stagiaire à ajouter
			res = false;
		}
		else if (blocAAjouter.cle.getNom().equals(this.cle.getNom())) { // je contiens un stagiaire ayant ce nom
			if (this.indexDoublon==-1) {									// il n'y a pas encore de doublon
				this.ecrireIndexDoublon(blocAAjouter.getIndex(), raf);  		// MaJ du doublon du bloc appelant
				blocAAjouter.setPere(this.index);				 				// MaJ du père du bloc à ajouter
				blocAAjouter.ecrireBloc(raf);									// écriture du bloc dans le fichier
				res = true;
			}
			else {														// il y déjà un doublon
				res = lireBlocAIndex(this.indexDoublon, raf).ajouterDansListeDoublons(blocAAjouter, raf); // je lui refile le boulot d'ajouter à la liste d'homonymes
			}
		}
		else if (blocAAjouter.cle.getNom().compareTo(this.cle.getNom())<0) {// on part à gauche
			if (this.filsG==-1) {												// il n'y a personne
				this.ecrireFilsG(blocAAjouter.getIndex(), raf);  					// MaJ de mon filsG
				blocAAjouter.setPere(this.index);				 					// MaJ du père du bloc à ajouter
				blocAAjouter.ecrireBloc(raf);										// écriture du bloc dans le fichier
				res = true;
			}
			else {																// il y a quelqu'un
				res = lireBlocAIndex(this.filsG, raf).ajouterBloc(blocAAjouter, raf); 	// je lui refile le boulot
			}
		}
		else {																	// on part à droite
			if (this.filsD==-1) {													// il n'y a personne
				this.ecrireFilsD(blocAAjouter.getIndex(), raf);  						// MaJ de mon filsD					
				blocAAjouter.setPere(this.index);										// MaJ du père du bloc à ajouter
				blocAAjouter.ecrireBloc(raf);											// écriture du bloc dans le fichier
				res = true;
			}
			else {																	// il y a quelqu'un
				res = lireBlocAIndex(this.filsD, raf).ajouterBloc(blocAAjouter, raf);	// je lui refile le boulot
			}
		}
		return res;
	}
	
	// Lit le fichier .bin en parcours GND pour créer une liste dans l'ordre alphabétique, pour le sous-arbre commençant au bloc 'index'
	// !!!  Bancale. Statique sans l'être. Pourquoi lire le bloc si c'est le bloc appelant ?  !!!
	public List<Stagiaire> listeGND(RandomAccessFile raf, int index, List<Stagiaire> liste) {
		Bloc blocALire = lireBlocAIndex(index, raf);
		if (blocALire.getFilsG()!=-1) {
			listeGND(raf, blocALire.getFilsG(), liste); 	// s'il y a un FilsG, on crée sa liste
		}
		liste.add(blocALire.cle);							// on ajoute le Stagiaire en cours
		if (blocALire.indexDoublon!=-1) {					// s'il y a des doublons
			Bloc blocDoublon = lireBlocAIndex(index, raf);					//
			while (blocDoublon.indexDoublon!=-1) {							//					Dans une méthode dédiée ?
				blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);// on les ajoute 
				liste.add(blocDoublon.cle);									//
			}
		}
		if (blocALire.getFilsD()!=-1) {
			listeGND(raf, blocALire.getFilsD(), liste);		// s'il y a un FilsD, on crée sa liste (qui est ajoutée à la liste)
		}
		return liste;
	}
		
	// Crée une liste de tous les stagiaires dont le nom est 'nom' (recherche dichotomique, parcours GND). Rem : 'nom' est mis en UpperCase dans la méthode appelante de AbrBinaire
	public List<Stagiaire> rechercheNom(String nom, RandomAccessFile raf, int index, List<Stagiaire> liste) {
		Bloc blocATester = lireBlocAIndex(index, raf);					// on récupère les infos du bloc en cours
		String nomATester = blocATester.getCle().getNom();
		if (nom.compareTo(nomATester)<0 && blocATester.getFilsG()!=-1) { 	// si nom cherché < nom du bloc  ET  il y a un fils gauche
			rechercheNom(nom, raf, blocATester.getFilsG(), liste);		// on cherche à gauche
		}
		else if (nom.equals(nomATester)) {										// si nom cherché = nom du bloc
			liste.add(blocATester.getCle());									// on ajoute le stagiaire du bloc à la liste
			if (blocATester.indexDoublon!=-1) {									// s'il y a des doublons
				Bloc blocDoublon = lireBlocAIndex(index, raf);						//
				while (blocDoublon.indexDoublon!=-1) {								//
					blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);	// on les ajoute
					liste.add(blocDoublon.cle);										//
				}
			}
		}
		else if (blocATester.getFilsD()!=-1) {								// si nom cherché > nom du bloc  ET  il y a un fils droit
			rechercheNom(nom, raf, blocATester.getFilsD(), liste);		// on cherche à droite
		}
		return liste;
	}
	
	// Crée une liste de tous les stagiaires dont le nom commence par 'nom' (recherche dichotomique jusqu'au premier noeud favorable (=> parcours complet dans le ca le + défavorable), parcours GND). Rem : 'nom' est mis en UpperCase dans la méthode appelante de AbrBinaire
	public List<Stagiaire> rechercheNomPartiel(String nom, RandomAccessFile raf, int index, List<Stagiaire> liste) {
		Bloc blocATester = lireBlocAIndex(index, raf);					// on récupère les infos du bloc en cours
		String nomATester = blocATester.getCle().getNom();
		if (nom.length()<nomATester.length()) {			// si recherche sur le début du nom
			nomATester = nomATester.substring(0, nom.length());			// on ne garde que le début du nomATester
		}
		if (nom.compareTo(nomATester)<0 && blocATester.getFilsG()!=-1) { 	// si nom cherché < nom du bloc  ET  il y a un fils gauche
			rechercheNomPartiel(nom, raf, blocATester.getFilsG(), liste);		// on cherche à gauche
		}
		if (nom.equals(nomATester)) {										// si nom cherché = nom du bloc
			if (blocATester.getFilsG()!=-1) {									// si filsG
				rechercheNomPartiel(nom, raf, blocATester.getFilsG(), liste);		//on cherche à gauche 	(pour noms partiels !)
			}
			liste.add(blocATester.getCle());									// on ajoute le stagiaire du bloc à la liste
			if (blocATester.indexDoublon!=-1) {									// s'il y a des doublons
				Bloc blocDoublon = lireBlocAIndex(index, raf);						//
				while (blocDoublon.indexDoublon!=-1) {								//
					blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);	// on les ajoute
					liste.add(blocDoublon.cle);										//
				}
			}
			if (blocATester.getFilsD()!=-1) {									// si filsD
				rechercheNomPartiel(nom, raf, blocATester.getFilsD(), liste);		//on cherche à droite 	(pour noms partiels !)
			}
		}
		else if (blocATester.getFilsD()!=-1) {								// si nom cherché > nom du bloc  ET  il y a un fils droit
			rechercheNomPartiel(nom, raf, blocATester.getFilsD(), liste);		// on cherche à droite
		}
		return liste;
	}
	
	// Crée une liste de tous les stagiaires dont le prénom est (ou commence par) 'prenom' (recherche complète, parcours GND). Rem : 'prenom' doit être normalisé
	public List<Stagiaire> recherchePrenom(String prenom, RandomAccessFile raf, int index, List<Stagiaire> liste, boolean partiel) {
		Bloc blocATester = lireBlocAIndex(index, raf);					// on récupère les infos du bloc en cours
		String prenomATester = Outils.prenomNormalise(blocATester.getCle().getPrenom());
		if (partiel && prenom.length()<prenomATester.length()) {				// si recherche sur le début du nom
			prenomATester = prenomATester.substring(0, prenom.length());		// on ne garde que le début du prenomATester
		}
		if (blocATester.getFilsG()!=-1) {										// s'il y a un fils gauche
			recherchePrenom(prenom, raf, blocATester.getFilsG(), liste, partiel);	// on cherche à gauche
		}
		if (prenom.equals(prenomATester)) {										// si prénom cherché = prénom du bloc
			liste.add(blocATester.getCle());										// on ajoute le stagiaire du bloc à la liste
		}
		if (blocATester.indexDoublon!=-1) {
			Bloc blocDoublon = lireBlocAIndex(index, raf);
			while (blocDoublon.indexDoublon!=-1) {											//
				blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);				//
				prenomATester = Outils.prenomNormalise(blocDoublon.getCle().getPrenom());	//
				if (partiel && prenom.length()<prenomATester.length()) {					// on teste aussi les éventuels doublons
					prenomATester = prenomATester.substring(0, prenom.length());			//
				}																			//
				if (prenom.equals(prenomATester)) {											//
				liste.add(blocDoublon.cle);													//
				}
			}
		}
		if (blocATester.getFilsD()!=-1) {										// s'il y a un fils droit
			recherchePrenom(prenom, raf, blocATester.getFilsD(), liste, partiel);	// on cherche à droite
		}
		return liste;
	}
	
	// Crée une liste de tous les stagiaires dont le département est 'dep' (recherche complète, parcours GND). 
	public List<Stagiaire> rechercheDep(String dep, RandomAccessFile raf, int index, List<Stagiaire> liste) {
		Bloc blocATester = lireBlocAIndex(index, raf);					// on récupère les infos du bloc en cours
		String depATester = blocATester.getCle().getDep();
		if (blocATester.getFilsG()!=-1) {										// s'il y a un fils gauche
			rechercheDep(dep, raf, blocATester.getFilsG(), liste);	// on cherche à gauche
		}
		if (depATester.equals(dep)) {										// si département cherché = dep du bloc
			liste.add(blocATester.getCle());										// on ajoute le stagiaire du bloc à la liste
		}
		if (blocATester.indexDoublon!=-1) {
			Bloc blocDoublon = lireBlocAIndex(index, raf);
			while (blocDoublon.indexDoublon!=-1) {											//
				blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);				//
				depATester = blocDoublon.getCle().getDep();									//on teste les doublons
				if (depATester.equals(dep)) {												//
				liste.add(blocDoublon.cle);													//
				}
			}
		}
		if (blocATester.getFilsD()!=-1) {										// s'il y a un fils droit
			rechercheDep(dep, raf, blocATester.getFilsD(), liste);	// on cherche à droite
		}
		return liste;
	}
		
	//Crée une liste de tous les stagiaires dont le promo est 'promo' (les CP inclus) (recherche complète, parcours GND). 
	public List<Stagiaire> recherchePromo(String promo, RandomAccessFile raf, int index, List<Stagiaire> liste) {
		Bloc blocATester = lireBlocAIndex(index, raf);					// on récupère les infos du bloc en cours
		String promoATester = blocATester.getCle().getPromo();
		if (blocATester.getFilsG()!=-1) {										// s'il y a un fils gauche
			recherchePromo(promo, raf, blocATester.getFilsG(), liste);	// on cherche à gauche
		}
		if (promoATester.contains(promo)) {										// si prénom cherché = prénom du bloc
			liste.add(blocATester.getCle());										// on ajoute le stagiaire du bloc à la liste
		}
		if (blocATester.indexDoublon!=-1) {
			Bloc blocDoublon = lireBlocAIndex(index, raf);
			while (blocDoublon.indexDoublon!=-1) {											//
				blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);				//
				promoATester = blocDoublon.getCle().getPromo();								//on teste les doublons
																							//
				if (promoATester.contains(promo)) {											//
				liste.add(blocDoublon.cle);													//
				}
			}
		}
		if (blocATester.getFilsD()!=-1) {										// s'il y a un fils droit
			recherchePromo(promo, raf, blocATester.getFilsD(), liste);	// on cherche à droite
		}
		return liste;
	}
		
	//Crée une liste de tous les stagiaires dont l'année est 'annee' (recherche complète, parcours GND). 
	public List<Stagiaire> rechercheAnnee(int annee, RandomAccessFile raf, int index, List<Stagiaire> liste) {
		Bloc blocATester = lireBlocAIndex(index, raf);					// on récupère les infos du bloc en cours
		int anneeATester = blocATester.getCle().getAnneeF();
		if (blocATester.getFilsG()!=-1) {										// s'il y a un fils gauche
			rechercheAnnee(annee, raf, blocATester.getFilsG(), liste);	// on cherche à gauche
		}
		if (anneeATester == annee) {										// si annee cherchée = annee du bloc
			liste.add(blocATester.getCle());										// on ajoute le stagiaire du bloc à la liste
		}
		if (blocATester.indexDoublon!=-1) {
			Bloc blocDoublon = lireBlocAIndex(index, raf);
			while (blocDoublon.indexDoublon!=-1) {											//
				blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);				//
				anneeATester = blocDoublon.getCle().getAnneeF();							//on teste les doublons
																							//
				if (anneeATester == annee) {												//
				liste.add(blocDoublon.cle);													//
				}
			}
		}
		if (blocATester.getFilsD()!=-1) {										// s'il y a un fils droit
			rechercheAnnee(annee, raf, blocATester.getFilsD(), liste);	// on cherche à droite
		}
		return liste;
	}
	
	// Retourne le Bloc de l'ABR (hors doublons) ayant le même nom que 'stagASupprimer'. Retourne 'null' si absent.
	public Bloc rechercheBlocParNom(Stagiaire stagASupprimer, RandomAccessFile raf) {
		if (stagASupprimer.getNom().compareTo(this.cle.getNom())<0) {  // je transmets à mon fils gauche
			if (this.filsG==-1) {
				return null;
			}
			else {
				return lireBlocAIndex(this.filsG, raf).rechercheBlocParNom(stagASupprimer, raf);
			}
		}
		else if (stagASupprimer.getNom().compareTo(this.cle.getNom())==0) {		// c'est moi
				return this;
		}
		else {															// je transmets à mon fils droit
			if (this.filsD==-1) {
				return null;
			}
			else {
				return lireBlocAIndex(this.filsD, raf).rechercheBlocParNom(stagASupprimer, raf);
			}
		}
	}
	
	// retourne le bloc contenant le stagiaire 'stagASupprimer', ou 'null' si ce stagiaire n'est pas dans la BDD
	public Bloc rechercheBloc(Stagiaire stagASupprimer, RandomAccessFile raf) {
		Bloc res = this.rechercheBlocParNom(stagASupprimer, raf);  // cherche le bloc dans l'ABR ayant le bon nom
		if (res==null) {								 // ce nom n'est pas présent dans l'ABR
			return null;
		}
		else if (stagASupprimer.compareTo(res.cle)==0) { // je suis le stagiaire cherché
			return res;
		}
		else {											 // le stagiaire cherché est un de mes homonymes (ou est absent)
			Bloc blocDoublon = lireBlocAIndex(res.index, raf);			
			while (blocDoublon.indexDoublon!=-1) {									
				blocDoublon = lireBlocAIndex(blocDoublon.indexDoublon, raf);
				if (stagASupprimer.compareTo(blocDoublon.cle)==0) {
					return blocDoublon;
				}
			}
			return null;
		}
	}

	@Override
	public String toString() {
		//return "Stagiaire : "+this.cle.toString()+" / Index : "+this.index+" / Index du père : "+this.pere+" / Index du fils G : "+this.filsG+" / Index du fils D : "+this.filsD+" / Index doublon : "+this.indexDoublon;
		return "Index : "+this.index+" / Index du père : "+this.pere+" / Index du fils G : "+this.filsG+" / Index du fils D : "+this.filsD+" / Index doublon : "+this.indexDoublon+" / "+this.cle.getNom();
	}

	// ********** Getters et Setters ***********
	
		public Stagiaire getCle() {
		return cle;
	}

	public void setCle(Stagiaire cle) {
		this.cle = cle;
	}

	public int getFilsG() {
		return filsG;
	}

	public void setFilsG(int filsG) {
		this.filsG = filsG;
	}

	public int getFilsD() {
		return filsD;
	}

	public void setFilsD(int filsD) {
		this.filsD = filsD;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getPere() {
		return pere;
	}
	
	public void setPere(int pere) {
		this.pere = pere;
	}

	public int getIndexDoublon() {
		return indexDoublon;
	}

	public void setIndexDoublon(int indexDoublon) {
		this.indexDoublon = indexDoublon;
	}

}
