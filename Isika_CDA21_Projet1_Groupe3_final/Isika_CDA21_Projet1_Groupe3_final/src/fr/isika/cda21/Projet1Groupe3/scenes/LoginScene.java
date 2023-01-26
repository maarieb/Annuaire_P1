// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.scenes;

import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.AbrBinaire;
import fr.isika.cda21.Projet1Groupe3.objetsGraphiques.*;

import java.io.RandomAccessFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//Classe de la scène de loggin (lance la scène principale)
public class LoginScene extends Scene {

	//---------------ATTRIBUTS-----------------
	//Layout
	public VBox root;
	public HBox ligne1;
	public HBox ligne2;
	public HBox ligne3;
	public HBox ligne4;
	public HBox ligne5;
	public HBox ligne6;
	public Stage stage;
	//Controls
	public Label espaceAdmin;
	public Label id;
	public TextField textId;
	public Label mdp;
	public PasswordField textMdp;
	public Button valider;
	public Button acceder;
	public Label alert;
	//Accès Admin
	public String idAdmin;
	public String mdpAdmin;
	public static boolean isAdmin; //afin de reconnaitre le profil de l'utilisateur et d'afficher / masquer les btn supprimer et modifier de l'AnnuaireScene
	//Mise en forme
	public Image image;

	//--------------CONSTRUCTEUR---------------
	
	public LoginScene(Stage stage, AbrBinaire arbre,RandomAccessFile raf) {
		super(new VBox(), 600, 500);
		acceder = new Button("ACCÉDER À L'ANNUAIRE");
		personnaliserBtnAcceder(acceder);
		espaceAdmin = new Label("ESPACE ADMINISTRATEUR");
		espaceAdmin.setPadding(new Insets(20,0,0,40));
		espaceAdmin.setFont(Font.font("VERDANA",FontWeight.BOLD,14));
		espaceAdmin.setStyle("-fx-text-fill: white");
		id = new PersoLabel("Identifiant : ");
		id.setPadding(new Insets(5,25,0,10));
		textId = new PersoTextField();
		textId.setPromptText("Votre identifiant");
		mdp = new PersoLabel("Mot de passe : ");
		textMdp = new PasswordField();
		textMdp.setFont(Font.font("Verdana",12));
		textMdp.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
		textMdp.setPromptText("Votre mot de passe");
		//Msg d'alerte pour la connexion admin
		alert = new PersoLabel("");
		alert.setStyle("-fx-text-fill : red");
		//Ici pour modifier l'acces admin          !!  temporaire !! amélioration à prévoir
		idAdmin = "Admin";
		mdpAdmin = "0000";
		valider = new PersoButton("Valider");
		
		//HBOX 
		ligne1 = new PersoHBox();
		ligne2 = new PersoHBox();
		ligne3 = new PersoHBox();
		ligne4 = new PersoHBox();
		ligne5 = new PersoHBox();
		ligne6 = new PersoHBox();
		ligne1.getChildren().add(acceder);
		ligne1.setPadding(new Insets(40,0,10,-50));
		ligne2.getChildren().add(espaceAdmin);
		ligne3.getChildren().addAll(id, textId);
		ligne3.setSpacing(20);
		ligne4.getChildren().addAll(mdp, textMdp);
		ligne4.setSpacing(20);
		ligne5.getChildren().add(alert);
		ligne5.setPadding(new Insets(10,0,0,0));
		ligne6.getChildren().add(valider);
		ligne6.setPadding(new Insets(10,0,0,110));
		
		root = ((VBox)this.getRoot());
		root.getChildren().addAll(ligne1,ligne2,ligne3,ligne4,ligne5,ligne6);
		
		//Mise en forme de la pane
		root.setPadding(new Insets(100,0,0,150));
		image = new Image("Fond_Log.png");
		BackgroundImage imgBack = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		Background background = new Background(imgBack);
		root.setBackground(background);
		
		// ACtion du bouton 'Accéder' (pour accès en mode visiteur)
		acceder.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				isAdmin = false;
				stage.setScene(new AnnuaireScene(stage,arbre,raf));
				stage.centerOnScreen();
			}
		});
		
		// ACtion du bouton 'Valider' (pour accès en mode admin)
		valider.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				if (textId.getText().equals("") || textMdp.getText().equals("")) {
					alert.setText("Veuillez remplir les champs");
				} else if ((!textId.getText().equals(idAdmin)) || (!textMdp.getText().equals(mdpAdmin))) {
					alert.setText("ID ou MDP incorrect");
				} else { 
					isAdmin = true;
					stage.setScene(new AnnuaireScene(stage,arbre,raf));
					stage.centerOnScreen();
				}
				};
		});
	}
	
	//--------------METHODES---------------
	
	public void personnaliserBtnAcceder(Button btn) {
		btn.setPrefWidth(400);
		btn.setPrefHeight(50);
		btn.setFont(Font.font("Century Gothic",FontWeight.BOLD,20));
		//pour retirer la lueur bleue par défaut des btn -> transparent
		btn.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent");
	}

}