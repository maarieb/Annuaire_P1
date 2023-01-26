// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.objetsGraphiques;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PersoLabel extends Label {
	
	public PersoLabel(String text){
		super(text);
		//ou super(); avec this.setText(text);
		this.setFont(Font.font("Verdana",FontWeight.BOLD,12));
		this.setPadding(new Insets(5,10,0,10));
		this.setStyle("-fx-text-fill: white");
	}

}