package com.example.myapplication.viewPatient;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.Session;
import com.example.myapplication.data.model.VentilatorSession;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PdfWriterManager {
    private final File file;
    ArrayList<VentilatorSession> sessions;

    PdfWriterManager(File file, ArrayList<VentilatorSession> sessions) {
        this.file = file;
        this.sessions = sessions;
    }

    void saveSessionsAsPdf(Context context) {
        try {
            Document document = new Document(new PdfDocument(new PdfWriter(file)));
            Table table = new Table(9);
            table.addHeaderCell("Date")
                    .addHeaderCell("National ID")
                    .addHeaderCell("Heart Rate")
                    .addHeaderCell("Oxygen Percentage")
                    .addHeaderCell("Ventilator OXI")
                    .addHeaderCell("Illness")
                    .addHeaderCell("Symptoms")
                    .addHeaderCell("Organization ID")
                    .addHeaderCell("Doctor Name");
            for (VentilatorSession session : sessions) {
                table.addCell(session.getDate().toLocaleString());
                table.addCell(session.getNationalID());
                table.addCell(String.valueOf(session.getHeartRate()));
                table.addCell(String.valueOf(session.getOxygenPercentage()));
                table.addCell(String.valueOf(session.getVentilatorOxi()));
                table.addCell(String.valueOf(session.getIllness()));
                table.addCell(String.valueOf(session.getSymptoms()));
                table.addCell(String.valueOf(session.getOrganizationID()));
                table.addCell(String.valueOf(session.getDoctorName()));
            }
            document.add((new Paragraph("Mobile Ventilator Report")).setPaddingBottom(8).setTextAlignment(TextAlignment.CENTER)).add(table).close();
            Toast.makeText(context, "Pdf created", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
