// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.application;
	
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import fr.isika.cda21.Projet1Groupe3.gestionAbrBinaire.AbrBinaire;
import fr.isika.cda21.Projet1Groupe3.scenes.LoginScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// Classe de lancement du projet JavaFX, avec méthodes main et start
public class Main extends Application {
	
	public static RandomAccessFile raf;
	public static AbrBinaire arbre ;
	
	@Override
	public void start(Stage stage) {
		try {
			stage.getIcons().add(new Image ("favicon2.png"));	// Initialisation de la fenêtre
			stage.setTitle("PIERCE ALUMNI");
			stage.setResizable(false);
			stage.setScene(new LoginScene(stage, arbre, raf));	// Lancement de la scene Login
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			arbre = new AbrBinaire();
			raf = new RandomAccessFile(ConstantesDAppli.getNomFichierBinDate(), "rw");
			arbre.creerFichierBinDepuisFichierTxt(raf);		// Création du fichier binaire
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		launch(args);
	}
}