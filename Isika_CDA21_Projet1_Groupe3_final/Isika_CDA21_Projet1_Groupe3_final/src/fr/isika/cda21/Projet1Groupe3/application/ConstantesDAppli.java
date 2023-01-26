// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.application;

import java.time.LocalDate;
import java.time.LocalTime;

// Classe contenant les différentes constantes utilisées par l'application
// Rem : nous avons hésité entre private et public, du coup on a fait moitié / moitié !
public abstract class ConstantesDAppli {
	
	private static final String NOM_FICHIER_BIN = "src/mesFichiers/STAGIAIRES.bin";
	private static final String NOM_FICHIER_BIN_DATE = "src/mesFichiers/Annuaire_"+LocalDate.now().toString()+"_"+LocalTime.now().getHour()+"H"+LocalTime.now().getMinute()+".bin";
	private static final String NOM_FICHIER_TXT = "src/mesFichiers/STAGIAIRES.DON";
	//private static final String NOM_FICHIER_TXT = "src/mesFichiers/FichierTest.DON";		// pour les tests
	//private static final String NOM_FICHIER_TXT = "src/mesFichiers/FichierTestDoublons.DON";
	private static final String NOM_FICHIER_PDF = "src/mesFichiers/AnnuairePDF_"+LocalDate.now().toString()+"_"+LocalTime.now().getHour()+"H"+LocalTime.now().getMinute()+".pdf";
	private static final String NOM_FICHIER_AIDE = "src/mesFichiers/manuel.pdf";
	
	// int index									     4 octets
	// int pere										     4 octets
	
	public static final int TAILLE_MAX_NOM = 25;      //50 octets
	public static final int TAILLE_MAX_PRENOM = 20;   //40 octets
	public static final int TAILLE_MAX_DEP = 2;		  // 4 octets
	public static final int TAILLE_MAX_PROMO = 11;    //22 octets
	// int anneeF										 4 octets
	
	// int filsG									  // 4 octets
	// int filsD									  // 4 octets
	// int indexDoublon								  // 4 octets
	
	public static final int TAILLE_BLOC = 140; 		// 4 + 4 + 120 + 4 + 4 + 4
	
	// ******************** Getters *************************

	public static String getNomFichierTxt() {
		return NOM_FICHIER_TXT;
	}
	
	public static String getNomFichierBin() {
		return NOM_FICHIER_BIN;
	}
	
	public static String getNomFichierBinDate() {
		return NOM_FICHIER_BIN_DATE;
	}

	public static String getNomFichierPdf() {
		return NOM_FICHIER_PDF;
	}

	public static String getNomFichierAide() {
		return NOM_FICHIER_AIDE;
	}
	
	public static int getTailleMaxNom() {
		return TAILLE_MAX_NOM;
	}

	public static int getTailleMaxPrenom() {
		return TAILLE_MAX_PRENOM;
	}

	public static int getTailleMaxDep() {
		return TAILLE_MAX_DEP;
	}

	public static int getTailleMaxPromo() {
		return TAILLE_MAX_PROMO;
	}

}
