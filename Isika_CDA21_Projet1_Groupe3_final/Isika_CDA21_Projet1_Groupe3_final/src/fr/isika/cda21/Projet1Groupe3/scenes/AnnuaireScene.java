// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.scenes;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.Optional;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.isika.cda21.Projet1Groupe3.application.ConstantesDAppli;
import fr.isika.cda21.Projet1Groupe3.entites.Stagiaire;
import fr.isika.cda21.Projet1Groupe3.entites.StagiaireARechercher;
import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.AbrBinaire;
import fr.isika.cda21.Projet1Groupe3.objetsGraphiques.PersoButton;
import fr.isika.cda21.Projet1Groupe3.objetsGraphiques.PersoLabel;
import fr.isika.cda21.Projet1Groupe3.objetsGraphiques.PersoTextField;
import fr.isika.cda21.Projet1Groupe3.outils.Outils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//Classe de la scène principale
public class AnnuaireScene extends Scene {

	// --------------------------------ATTRIBUTS----------------------------------------
	public BorderPane root2;
	public VBox formulaire;
	public HBox enTete;
	public GridPane informationsStagiaire;
	public HBox boutonsBas1;
	public HBox boutonsBas2;
	public HBox leftFooter;
	public VBox listeStagiaire;
	public HBox buttons;
	public HBox alerte;
	public Stage stage;
	// Titres
	public Label titreFormulaire;
	public Label titreListe;
	// Labels formulaire
	public Label nom;
	public Label prenom;
	public Label departement;
	public Label promo;
	public Label anneePromo;
	public Label messageAlerte; // Label qui s'affiche pour confirmer une action btn ou indiquer une erreur
	// TextFields formulaire
	public TextField textNom;
	public TextField textPrenom;
	public TextField textPromo;
	public TextField textAnnee; // -> rendre 4 chiffres obligatoires
	// BOUTONS
	public ChoiceBox btnDepartement;
	public Button btnRetour;
	public Button btnRechercher;
	public Button btnAjouter;
	public Button btnImprimer;
	public Button btnSupprimer;
	public Button btnModifier;
	public Button btnEffacer; // effacer la recherche et revenir à la liste initiale
	public Button btnAide;
	// Liste observable
	public ObservableList<Stagiaire> observableList;
	public TableView<Stagiaire> table;
	// Compteurs
	int nbStagiaires;
	Label lblNbStagiaires;
	Label lblNbSelec;

	// --------------------------------CONSTRUCTEUR----------------------------------------

	public AnnuaireScene(Stage stage, AbrBinaire arbre, RandomAccessFile raf) { // Arbre et raf nécessaires pour la
																				// construction du tableView
		super(new BorderPane(), 1200, 750);

		// ******************* PARTIE GAUCHE = FORMULAIRE  *******************************
		messageAlerte = new Label();
		formulaire = new VBox();
		alerte = new HBox();
		informationsStagiaire = new GridPane();
		informationsStagiaire.setHgap(10);
		informationsStagiaire.setVgap(20);
		informationsStagiaire.setPadding(new Insets(30, 30, 10, 10));
		boutonsBas1 = new HBox();
		boutonsBas2 = new HBox();
		boutonsBas1.setPadding(new Insets(20));
		boutonsBas2.setPadding(new Insets(20));
		boutonsBas1.setSpacing(10);
		boutonsBas2.setSpacing(10);

		// TITRE
		titreFormulaire = new Label("FORMULAIRE");
		titreFormulaire.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		titreFormulaire.setStyle("-fx-text-fill: white");
		titreFormulaire.setPadding(new Insets(5, 0, -20, 30));

		// GridPane : NOM, PRENOM, DEPARTEMENT, PROMO, ANNEE
		nom = new PersoLabel("Nom :");
		textNom = new PersoTextField();
		textNom.setPromptText("ex : DUPONT ou DU*");
		informationsStagiaire.addRow(1, nom, textNom);
		
		prenom = new PersoLabel("Prénom :");
		textPrenom = new PersoTextField();
		textPrenom.setPromptText("ex : Joachim ou joa*");
		informationsStagiaire.addRow(2, prenom, textPrenom);
		informationsStagiaire.setValignment(prenom, VPos.BASELINE);

		departement = new PersoLabel("Département :");
		btnDepartement = new ChoiceBox<String>();
		btnDepartement.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		listerDep(); // lecture du fichier txt et remplissage de la choice box
		informationsStagiaire.addRow(3, departement, btnDepartement);

		promo = new PersoLabel("Promotion : ");
		textPromo = new PersoTextField();
		informationsStagiaire.addRow(4, promo, textPromo);
		
		anneePromo = new PersoLabel("Année :");
		textAnnee = new PersoTextField();
		informationsStagiaire.addRow(5, anneePromo, textAnnee);

		// LABEL MESSAGEALERTE + BOUTONS
		btnRechercher = new PersoButton("Rechercher");
		btnAjouter = new PersoButton("Ajouter");
		btnEffacer = new PersoButton("Effacer");
		btnSupprimer = new PersoButton("Supprimer");
		btnModifier = new PersoButton("Modifier");
		messageAlerte = new PersoLabel("");
		alerte.getChildren().addAll(messageAlerte);
		alerte.setPadding(new Insets(0, 0, 0, 10));
		boutonsBas1.getChildren().addAll(btnRechercher, btnEffacer, btnAjouter);
		boutonsBas2.getChildren().addAll(btnModifier, btnSupprimer);

		// BTN RETOUR ET AIDE -> FOOTER
		leftFooter = new HBox();
		Image imageBtnRetour = new Image("btnretour2.png");
		ImageView imageView = new ImageView(imageBtnRetour);
		btnRetour = new Button("", imageView);

		Image imageBtnAide = new Image("btnaide2.png");
		imageView = new ImageView(imageBtnAide);
		btnAide = new Button("", imageView);
		leftFooter.getChildren().addAll(btnRetour, btnAide);
		leftFooter.setSpacing(320);
		leftFooter.setPadding(new Insets(120, 0, 0, 20));

		// ACTION BTNRETOUR -> vers LOGINSCENE
		btnRetour.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				stage.setScene(new LoginScene(stage, arbre, raf));
			}
		});
		
		// ACTION BOUTON AIDE
		btnAide.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				try  {
					File file = new File(ConstantesDAppli.getNomFichierAide());
					Desktop desktop = Desktop.getDesktop();
					desktop.open(file);
				}
				catch(Exception e)  {
					e.printStackTrace();
				}
			}
		});

		// ACTIONS BTN AJOUTER
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				ajouterStagiaireViaFormulaire(arbre, raf);
			}
		});

		// ACTIONS BTN RECHERCHER
		btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				rechercher(arbre, raf);
			}
		});

		// ACTIONS BTN EFFACER
		btnEffacer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				remiseAZero(arbre, raf);
			}
		});

		// Action sur le bouton supprimer
		btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				supprimer(arbre, raf);
			}
		});

		// Action sur le bouton modifier
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				modifier(arbre, raf);
			}
		});

		// PLACEMENT DANS LA BORDERPANE
		root2 = ((BorderPane) this.getRoot());
		root2.setLeft(formulaire);
		root2.setPadding(new Insets(10, 20, 20, 20));
		formulaire.getChildren().addAll(titreFormulaire, informationsStagiaire, alerte, boutonsBas1, boutonsBas2,
				leftFooter);
		formulaire.setAlignment(Pos.CENTER);
		formulaire.setPadding(new Insets(0, 20, -150, 0));
		root2.requestFocus();

		// ******************* PARTIE DROITE = TABLEVIEW ***************************
		listeStagiaire = new VBox();
		listeStagiaire.setSpacing(10);
		enTete = new HBox(260);
		enTete.setAlignment(Pos.CENTER_LEFT);
		titreListe = new Label("LISTE DES STAGIAIRES");
		titreListe.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		// titreListe.setPadding(new Insets(20, 0, 5, 230));
		titreListe.setStyle("-fx-text-fill: white");
		lblNbStagiaires = new Label();
		lblNbStagiaires.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		lblNbStagiaires.setStyle("-fx-text-fill: white");
		enTete.getChildren().addAll(titreListe, lblNbStagiaires);
		observableList = FXCollections.observableArrayList(arbre.listeGND(raf));
		nbStagiaires=observableList.size();
		lblNbStagiaires.setText(nbStagiaires+" stagiaires dans l'annuaire");
		lblNbSelec = new Label();
		creerTableView(arbre, raf);
		afficherStagiaireFormulaire();

		// FOOTER
		buttons = new HBox(170);
		buttons.setPadding(new Insets(5, 0, 0, 0));
		buttons.setAlignment(Pos.BASELINE_RIGHT);

		// BOUTON IMPRIMER
		// btnImprimer = new PersoButton("Imprimer");
		Image imageBtnImprimer = new Image("btnimprimer.png");
		ImageView imageView3 = new ImageView(imageBtnImprimer);
		btnImprimer = new Button("", imageView3);
		
		// Action sur le bouton Imprimer
		btnImprimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				exporterPDF();
			}
		});

		// Label nombre de stagiaires sélectionnés
		lblNbSelec.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		lblNbSelec.setStyle("-fx-text-fill: white");
		
		buttons.getChildren().addAll(lblNbSelec, btnImprimer);

		// PLACEMENT DANS LA BORDERPANE
		listeStagiaire.getChildren().addAll(enTete, table, buttons);
		listeStagiaire.setPadding(new Insets(0, 20, 0, 20));
		listeStagiaire.setPrefWidth(1000);
		root2.setCenter(listeStagiaire);
		// root2.setStyle("-fx-background-color:#24475B");

		Image image = new Image("Fond_Annuaire.png");
		BackgroundImage imgBack = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(imgBack);
		root2.setBackground(background);

		// AFFICHAGE ou MASQUAGE DES BOUTONS SUPP et MOD
		affichageAdmin();
		

	}

	// ------------------------------------------------METHODES-----------------------------------------------------------

	// permet de passer l'AnnuaireScene en mode admin
	public void affichageAdmin() {
		// si le user clique sur Acceder -> isAdmin = faux et les btn supprimer et modifier sont invisibles
		// si le user clique sur Valider -> isAdmin = vrai et les btn sont visibles
		if (LoginScene.isAdmin == false) {
			// table n'est pas éditable en mode visiteur
			btnSupprimer.setVisible(false);
			btnModifier.setVisible(false);
		}
	}

	// liste des départements = on va lire les dép dans le fichier txt et les
	// ajouter dans une liste, puis on ajoute chaque élément un par un à la ChoiceBox
	public void listerDep() {
		try {
			FileReader fr = new FileReader("src/mesFichiers/departements.txt");
			BufferedReader br = new BufferedReader(fr);

			while (br.ready()) { // chaque ligne lue = new département qu'on ajoute à la liste
				btnDepartement.getItems().add(br.readLine());
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// méthode pour afficher notre liste de stagiaire dans le table view
	public void creerTableView(AbrBinaire arbre, RandomAccessFile raf) {
		table = new TableView<>(observableList);
		table.setEditable(true);
		// on crée les colonnes
		TableColumn<Stagiaire, String> colNom = new TableColumn<>("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("nom"));
		// colNom.setCellFactory(TextFieldTableCell.<Stagiaire> forTableColumn());
		// //Pour rendre la cellule du Nom modifiable
		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>("Prénom");
		colPrenom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("prenom"));
		// colPrenom.setCellFactory(TextFieldTableCell.<Stagiaire> forTableColumn());
		// //Pour rendre la cellule du Prenom modifiable
		TableColumn<Stagiaire, String> colDep = new TableColumn<>("Département");
		colDep.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("dep"));
		// colDep.setCellFactory(TextFieldTableCell.<Stagiaire> forTableColumn());
		// //Pour rendre la cellule du Dep modifiable
		TableColumn<Stagiaire, String> colPromo = new TableColumn<>("Promotion");
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promo"));
		// colPromo.setCellFactory(TextFieldTableCell.<Stagiaire> forTableColumn());
		// //Pour rendre la cellule de la Promo modifiable
		TableColumn<Stagiaire, String> colAnnee = new TableColumn<>("Année");
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("anneeF"));
		// colAnnee.setCellFactory(TextFieldTableCell.<Stagiaire> forTableColumn());
		// //Pour rendre la cellule de l'Année modifiable

		table.getColumns().addAll(colNom, colPrenom, colDep, colPromo, colAnnee); // ajout des colonnes dans la table
		table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);
		table.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		table.setPrefSize(500, 650);
		lblNbSelec.setText(observableList.size()+" stagiaires sélectionnés");
	}
	
	// vérifie que tous les champs sont complets et que la date est au format correct
	public boolean verifierFormulaire() {
		boolean res = true;
		if ((textNom.getText().equals("")) || (textPrenom.getText().equals("")) || (textPromo.getText().equals(""))
				|| (textAnnee.getText().equals("")) || (btnDepartement.getValue() == null)) {
			messageAlerte.setText("Veuillez remplir tous les champs !");
			messageAlerte.setStyle("-fx-text-fill : red");
			res = false;
			if (textNom.getText() == "") {
				textNom.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
			}
			if (textPrenom.getText().equals("")) {
				textPrenom.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
			}
			if (textPromo.getText().equals("")) {
				textPromo.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
			}
			if (textAnnee.getText().equals("")) {
				textAnnee.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
			}
			if (btnDepartement.getValue() == null) {
				btnDepartement.setStyle("-fx-border-color: red;-fx-focus-color: red");
			}
		} else if (!Outils.verifieDate(textAnnee.getText())) {
			messageAlerte.setText("La date n'est pas au bon format");
			messageAlerte.setStyle("-fx-text-fill : red");
			textAnnee.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
			res = false;
		}
		return res;
	}
	
	// Enlève les bordures rouges autour des champs
	public void miseEnFormeInitiale() {
		textNom.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		textNom.clear();
		textPrenom.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		textPrenom.clear();
		textPromo.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		textPromo.clear();
		textAnnee.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		textAnnee.clear();
		btnDepartement.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		btnDepartement.setValue(null);
	}

	// ajouter un stagiaire au clic
	// si les champs ne sont pas remplis = border rouge sur les textsFields et label
	// d'erreur(alertAjout.setText)
	// ajoute un nouveau stagiaire si tout est ok -> label de
	// confirmation(alertAjout.setText)
	public void ajouterStagiaireViaFormulaire(AbrBinaire arbre, RandomAccessFile raf) {
		if (verifierFormulaire()) {
			// faire l'ajout d'un stagiaire
			Stagiaire nouveauStagiaire = new Stagiaire(textNom.getText().toUpperCase(), textPrenom.getText(),
					((String) (btnDepartement.getValue())).substring(0, 2), textPromo.getText(),
					Integer.parseInt(textAnnee.getText()));
			if (arbre.ajouterStagiaire(nouveauStagiaire, raf) == true) {
				// on met à jour le tableView
				observableList = FXCollections.observableArrayList(arbre.listeGND(raf));
				creerTableView(arbre, raf);
				// on met à jour la scène
				listeStagiaire.getChildren().set(1, table);
				// puis écrire :
				messageAlerte.setText(
						"Le stagiaire " + textNom.getText() + " " + textPrenom.getText() + " " + "a bien été ajouté.");
				// et mettre en forme
				messageAlerte.setStyle("-fx-text-fill :  lightgreen");
				miseEnFormeInitiale();
				nbStagiaires++;
				lblNbStagiaires.setText(nbStagiaires+" stagiaires dans l'annuaire");
				
			} else {
				messageAlerte.setText("Ce stagiaire existe déjà.");
				messageAlerte.setStyle("-fx-text-fill :  red");
			}
		}
		afficherStagiaireFormulaire();
	}

	
	// Fenêtres d'alerte pour la suppression d'un stagiaire
	// les fenêtres d'alerte gèrent directement le lancement des actions lorsqu'on
	// clique sur OK, et ferment la fenêtre dans les 2 cas (OK ou CANCEL)
	// idem pour la modification
	public void supprimer(AbrBinaire arbre, RandomAccessFile raf) {
		messageAlerte.setText("");
		if (verifierFormulaire()) {
			Stagiaire stagiaireTable = table.getSelectionModel().getSelectedItem();
			Stagiaire stagiaireFormulaire = new Stagiaire(textNom.getText().toUpperCase(), textPrenom.getText(),
					((String) (btnDepartement.getValue())).substring(0, 2), textPromo.getText(),
					Integer.parseInt(textAnnee.getText()));
			if (stagiaireFormulaire.compareTo(stagiaireTable)!=0) {
				messageAlerte.setText("Le formulaire doit correspondre à la ligne sélectionnée.");
				messageAlerte.setStyle("-fx-text-fill : red");
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Supprimer ?");
				alert.setHeaderText("Etes-vous sûr de vouloir supprimer ce stagiaire ?");
		
				Optional<ButtonType> option = alert.showAndWait();
		
				if (option.get() == ButtonType.OK) {
					// lancer la méthode supprimer
					if (arbre.supprimer(stagiaireFormulaire, raf) == true) {
						messageAlerte.setText("Le stagiaire a été supprimé.");
						messageAlerte.setStyle("-fx-text-fill :  lightgreen");
						miseEnFormeInitiale();
						observableList = FXCollections.observableArrayList(arbre.listeGND(raf));
						creerTableView(arbre, raf);
						listeStagiaire.getChildren().set(1, table);
						nbStagiaires--;
						lblNbStagiaires.setText(nbStagiaires+" stagiaires dans l'annuaire");
					} else {
						messageAlerte.setText("Ce stagiaire n'est pas dans l'annuaire.");
						messageAlerte.setStyle("-fx-text-fill : red");
					}
					textNom.clear();
					textPrenom.clear();
					btnDepartement.setValue(null);
					textPromo.clear();
					textAnnee.clear();
					afficherStagiaireFormulaire();
				}
			}
		}
	}

	// Modifie un stagiaire (après vérifications et confirmation)
	public void modifier(AbrBinaire arbre, RandomAccessFile raf) {
		messageAlerte.setText("");
		if (table.getSelectionModel().getSelectedIndex() == -1) { // une ligne de la table doit être
			// sélectionnée
			messageAlerte.setText("Sélectionnez dans la table le stagiaire à modifier");
			messageAlerte.setStyle("-fx-text-fill : red");
		}
		else if (verifierFormulaire()) {
			Stagiaire stagiaire = table.getSelectionModel().getSelectedItem();
			Stagiaire stagiaireModifie = new Stagiaire(textNom.getText().toUpperCase(), textPrenom.getText(),
					((String) (btnDepartement.getValue())).substring(0, 2), textPromo.getText(),
					Integer.parseInt(textAnnee.getText()));
			if (stagiaire.compareTo(stagiaireModifie) == 0) {
				messageAlerte.setText("Il n'y a aucune modification à apporter !");
				messageAlerte.setStyle("-fx-text-fill : red");
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION); // Fenêtre d'alerte pour la modification
				alert.setTitle("Modifier ?");
				alert.setHeaderText("Etes-vous sûr de vouloir modifier ce stagiaire ?");
				Optional<ButtonType> option = alert.showAndWait();
				if (option.get() == ButtonType.OK) {
					// lancer la méthode modifier
					if (arbre.modifier2(stagiaire, stagiaireModifie, raf) == true) { // on lance la méthode. Elle retourne
																						// 'false' si 'stagiaireModifie' est
																						// déjà dans l'annuaire
						messageAlerte.setText("Le stagiaire a bien été modifié.");
						messageAlerte.setStyle("-fx-text-fill :  lightgreen");
						miseEnFormeInitiale();
						observableList = FXCollections.observableArrayList(arbre.listeGND(raf));
						creerTableView(arbre, raf);
						listeStagiaire.getChildren().set(1, table);
					} else {
						messageAlerte.setText("Opération annulée : le stagiaire modifié est déjà présent");
						messageAlerte.setStyle("-fx-text-fill : red");
					}
					textNom.clear();
					textPrenom.clear();
					btnDepartement.setValue(null);
					textPromo.clear();
					textAnnee.clear();
					afficherStagiaireFormulaire();
				}
			}
		}
	}

	// méthode de recherche simple + multiple à partir des entrées du user
	public void rechercher(AbrBinaire arbre, RandomAccessFile raf) {
		if ((textNom.getText().equals("")) && (textPrenom.getText().equals("")) && (textPromo.getText().equals(""))
				&& (textAnnee.getText().equals("")) && (btnDepartement.getValue() == null)) {
			messageAlerte.setText("Veuillez remplir au  moins un champs !");
			messageAlerte.setStyle("-fx-text-fill : red");
		}
		else if (!textAnnee.getText().equals("") && !Outils.verifieDate(textAnnee.getText())) {
			messageAlerte.setText("La date n'est pas au bon format");
			messageAlerte.setStyle("-fx-text-fill : red");
			textAnnee.setStyle("-fx-text-box-border: red; -fx-focus-color: red");
		}
		else {
			// on crée notre stagiaire à rechercher en récupérant les infos entrées dans le formulaire
			// le stagiaire à rechercher est composé du : nom, prenom, dep, promo, anneeF, nomPartiel, prenomPartiel
			StagiaireARechercher leStagiaire = new StagiaireARechercher();
			String nom = textNom.getText();
			String prenom = textPrenom.getText();
			if (!nom.equals("") && nom.substring(nom.length() - 1).equals("*")) {
				leStagiaire.setNomPartiel(true);
				nom = nom.substring(0, nom.length() - 1);
			} else {
				leStagiaire.setNomPartiel(false);
			}
			leStagiaire.setNom(nom);
			if (!prenom.equals("") && prenom.substring(prenom.length() - 1).equals("*")) {
				leStagiaire.setPrenomPartiel(true);
				prenom = prenom.substring(0, prenom.length() - 1);
			} else {
				leStagiaire.setPrenomPartiel(false);
			}
			leStagiaire.setPrenom(prenom);
			// condition sur la choice box car sinon retourne erreur "getValue=null"
			if ((String) (btnDepartement.getValue()) == null) {
				leStagiaire.setDep("");
			} else {
				leStagiaire.setDep(((String) (btnDepartement.getValue())).substring(0, 2));
			}
			leStagiaire.setPromo(textPromo.getText());
			// idem, condition sur l'année car sinon retourne erreur
			if (textAnnee.getText().equals("")) {
				leStagiaire.setAnneeF(0);
			} else {
				leStagiaire.setAnneeF(Integer.parseInt(textAnnee.getText()));
			}
			// on met à jour notre table avec résultat de la recherche
			observableList = FXCollections.observableArrayList(arbre.recherche(leStagiaire, raf));
			creerTableView(arbre, raf);
			listeStagiaire.getChildren().set(1, table);
			afficherStagiaireFormulaire();
		}
	}

	// clear tous les champs et remettre la table initiale
	public void remiseAZero(AbrBinaire arbre, RandomAccessFile raf) {
		textNom.clear();
		textPrenom.clear();
		btnDepartement.setValue(null);
		textPromo.clear();
		textAnnee.clear();
		if (observableList.size()<nbStagiaires) {
			observableList = FXCollections.observableArrayList(arbre.listeGND(raf));
			System.out.println("bouh");
		}
		creerTableView(arbre, raf);
		listeStagiaire.getChildren().set(1, table);
		messageAlerte.setText("");
		afficherStagiaireFormulaire();
	}

	// Methode pour afficher dans le formulaire les informations du stagiaire sélectionné dans la table
	public void afficherStagiaireFormulaire() {
		table.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			textNom.setText(newValue.getNom());
			textPrenom.setText(newValue.getPrenom());
			btnDepartement.setValue(newValue.getDep());
			textPromo.setText(newValue.getPromo());
			textAnnee.setText(Integer.toString(newValue.getAnneeF()));
			messageAlerte.setText("");
		});
	}

	// Crée un fichier PDF à partir des données dans le TableView puis ouvre ce fichier pour impression éventuelle
	public void exporterPDF() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(ConstantesDAppli.getNomFichierPdf()));
			document.open();
			Paragraph page1 = new Paragraph();
			Paragraph titre = new Paragraph("Annuaire des stagiaires de Peirce Formation");
			titre.setIndentationLeft(145);
			page1.add(titre);
			page1.add(new Paragraph(" "));
			page1.add(new Paragraph("Annuaire édité le " + LocalDate.now().getDayOfMonth() + "/"
					+ LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear()));
			page1.add(new Paragraph(" "));

			// création de la table
			PdfPTable tablePDF = new PdfPTable(5);
			tablePDF.setWidths(new int[] { 260, 200, 60, 160, 80 });

			// création de la table
			PdfPCell cell = new PdfPCell(new Phrase("Nom"));
			tablePDF.addCell(cell);
			cell = new PdfPCell(new Phrase("Prénom"));
			tablePDF.addCell(cell);
			cell = new PdfPCell(new Phrase("Dépt"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePDF.addCell(cell);
			cell = new PdfPCell(new Phrase("Promotion"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePDF.addCell(cell);
			cell = new PdfPCell(new Phrase("Année"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablePDF.addCell(cell);

			tablePDF.setHeaderRows(1);

			int nbStag = table.getItems().size();
			for (int k = 0; k < nbStag; k++) {
				tablePDF.addCell(table.getItems().get(k).getNom());
				tablePDF.addCell(table.getItems().get(k).getPrenom());
				cell = new PdfPCell(new Phrase(table.getItems().get(k).getDep()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tablePDF.addCell(cell);
				cell = new PdfPCell(new Phrase(table.getItems().get(k).getPromo()));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tablePDF.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(table.getItems().get(k).getAnneeF())));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tablePDF.addCell(cell);
			}
			page1.add(tablePDF);

			document.add(page1);
			document.close();

			messageAlerte.setText("Votre sélection a bien été exportée en fichier PDF");
			messageAlerte.setStyle("-fx-text-fill :  lightgreen");
			
			File file = new File(ConstantesDAppli.getNomFichierPdf());
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

// Revoir PrinterJob !!
		
	}
}