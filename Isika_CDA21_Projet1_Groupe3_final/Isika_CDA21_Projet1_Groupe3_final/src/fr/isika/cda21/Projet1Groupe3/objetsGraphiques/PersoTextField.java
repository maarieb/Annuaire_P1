// Authors : Marie Briere, Rocio Keuro, Joachim Ouafo, Jérôme Vallin

package fr.isika.cda21.Projet1Groupe3.objetsGraphiques;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class PersoTextField extends TextField {
	
	public PersoTextField(){
		super();
		//ou super(); avec this.setText(text);
		this.setFont(Font.font("Verdana",12));
		this.setStyle("-fx-focus-color: orange; -fx-faint-focus-color: transparent"); 
	}
	
}