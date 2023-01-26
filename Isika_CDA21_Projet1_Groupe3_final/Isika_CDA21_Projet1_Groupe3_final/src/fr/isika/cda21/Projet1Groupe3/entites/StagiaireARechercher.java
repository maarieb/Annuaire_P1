// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.entites;

// Classe fille de Stagiaire, pour un stagiaire qu'on recherche dans la BDD (pour gérer recherches partielles)
public class StagiaireARechercher extends Stagiaire {
	
	private boolean nomPartiel;
	private boolean prenomPartiel;
	

	//------------------CONSTRUCTEUR--------------------
	
	public StagiaireARechercher(String nom, String prenom, String dep, String promo, int anneeF, boolean nomPartiel,
			boolean prenomPartiel) {
		super(nom, prenom, dep, promo, anneeF);
		this.nomPartiel = nomPartiel;
		this.prenomPartiel = prenomPartiel;
	}
	
	public StagiaireARechercher(Stagiaire stag, boolean nomPartiel, boolean prenomPartiel) {
		super(stag.getNom(), stag.getPrenom(), stag.getDep(), stag.getPromo(), stag.getAnneeF());
		this.nomPartiel = nomPartiel;
		this.prenomPartiel = prenomPartiel;
	}
	
	public StagiaireARechercher() {
		super();
	}

	//-----------------GETTER/SETTER--------------------
	
	public boolean isNomPartiel() {
		return nomPartiel;
	}

	public void setNomPartiel(boolean nomPartiel) {
		this.nomPartiel = nomPartiel;
	}

	public boolean isPrenomPartiel() {
		return prenomPartiel;
	}

	public void setPrenomPartiel(boolean prenomPartiel) {
		this.prenomPartiel = prenomPartiel;
	}
	
}