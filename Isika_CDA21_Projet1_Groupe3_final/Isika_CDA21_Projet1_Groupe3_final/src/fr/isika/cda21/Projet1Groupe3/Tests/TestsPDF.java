package fr.isika.cda21.Projet1Groupe3.Tests;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class TestsPDF {
    
    public static void main(String[] args) {
    	String FILE = "src/mesFichiers/FirstPdf.pdf";
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
     Font.BOLD);
        Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL, BaseColor.RED);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            Paragraph page1 = new Paragraph();
            Paragraph titre = new Paragraph("Annuaire des stagiaires de Peirce Formation", catFont);
            page1.add(titre);
            page1.add(new Paragraph(" "));
            page1.add(new Paragraph("Annuaire édité le " + new Date(), smallBold));
            page1.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(5);

            // t.setBorderColor(BaseColor.GRAY);
            // t.setPadding(4);
            // t.setSpacing(4);
            // t.setBorderWidth(1);

            // création de la table
            PdfPCell c1 = new PdfPCell(new Phrase("Nom"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Prénom"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Département"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Promotion"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Année"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            table.setHeaderRows(1);

            table.addCell("1.0");
            table.addCell("1.1");
            table.addCell("1.2");
            table.addCell("2.1");
            table.addCell("2.2");
            table.addCell("2.3");
            table.addCell("2.3");
            table.addCell("2.3");
            table.addCell("2.3");

            page1.add(table);

            
            document.add(page1);
            document.close();
            // Start a new page
            //document.newPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}