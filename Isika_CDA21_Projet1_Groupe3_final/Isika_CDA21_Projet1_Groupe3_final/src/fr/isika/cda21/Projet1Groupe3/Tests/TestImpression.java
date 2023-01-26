package fr.isika.cda21.Projet1Groupe3.Tests;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestImpression extends Application {

 public static void main(String[] args) {
  launch(args);
 }

 @Override
 public void start(Stage stage) {

  Label jobStatus = new Label("Status de l'impression : ");

  TextArea textArea = new TextArea();

  Button printButton = new Button("Imprimer");

  // Creation d'un handler pour le bouton
  printButton.setOnAction(new EventHandler < ActionEvent > () {
   public void handle(ActionEvent event) {

    // Creation du printer job
    PrinterJob job = PrinterJob.createPrinterJob();

    // Montre la boite de dialogue
    boolean proceed = job.showPrintDialog(stage);

    // Si l'utilisateur clique sur imprimer dans la boite de dialogue
    if (proceed) {

     job.jobStatusProperty().addListener((observable, oldValue, newValue) -> {

      if (newValue == PrinterJob.JobStatus.PRINTING)
       jobStatus.setText("Status de l'impression : Impression en cours...");

      if (newValue == PrinterJob.JobStatus.DONE)
       jobStatus.setText("Status de l'impression : Texte imprimé avec succès !");

      if (newValue == PrinterJob.JobStatus.ERROR)
       jobStatus.setText("Status de l'impression : Erreur lors de l'impression");

     });

     // Imprime la zone texte
     boolean printed = job.printPage(textArea);

     if (printed) {
      job.endJob();
     }
    }

   }
  });

  /* Mise en page */
  VBox root = new VBox();

  root.getChildren().addAll(textArea, printButton, jobStatus);

  Scene scene = new Scene(root);
  stage.setScene(scene);
  /* Fin mise en page */

  stage.show();
 }

}
