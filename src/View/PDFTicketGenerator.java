package View;

import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.HorizontalAlignment;

public class PDFTicketGenerator {

    public static void generate(String filePath, String origin, String destination, String departDate, String arrivalDate,
                                 String plateNo, String totalPrice, List<CustomerPaymentGUI.PassengerForm> passengers) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            Paragraph title = new Paragraph("BUS TICKET")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold()
                    .setMarginBottom(20);
            document.add(title);

            // Trip Information Table
            Table tripTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1))
                    .setMarginBottom(20);

            tripTable.addCell(makeHeaderCell("From"));
            tripTable.addCell(makeValueCell(origin));
            tripTable.addCell(makeHeaderCell("To"));
            tripTable.addCell(makeValueCell(destination));
            tripTable.addCell(makeHeaderCell("Departure"));
            tripTable.addCell(makeValueCell(departDate));
            tripTable.addCell(makeHeaderCell("Arrival"));
            tripTable.addCell(makeValueCell(arrivalDate));
            tripTable.addCell(makeHeaderCell("Bus Plate"));
            tripTable.addCell(makeValueCell(plateNo));
            tripTable.addCell(makeHeaderCell("Total Price"));
            tripTable.addCell(makeValueCell("RM " + totalPrice));

            document.add(tripTable);

            // Passenger Details Title
            Paragraph passTitle = new Paragraph("Passenger Details")
                    .setBold()
                    .setFontSize(16)
                    .setUnderline()
                    .setMarginBottom(10);
            document.add(passTitle);

            // Passenger Info Table
            Table passengerTable = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 1}))
                    .useAllAvailableWidth()
                    .setBorder(new SolidBorder(1))
                    .setMarginBottom(20);

            passengerTable.addHeaderCell(makeHeaderCell("Seat No"));
            passengerTable.addHeaderCell(makeHeaderCell("Name"));
            passengerTable.addHeaderCell(makeHeaderCell("Gender"));
            passengerTable.addHeaderCell(makeHeaderCell("Phone"));
            passengerTable.addHeaderCell(makeHeaderCell("Age"));

            for (CustomerPaymentGUI.PassengerForm pf : passengers) {
                passengerTable.addCell(makeValueCell(pf.seatNum));
                passengerTable.addCell(makeValueCell(pf.nameField.getText()));
                passengerTable.addCell(makeValueCell(pf.genderBox.getSelectedItem().toString()));
                passengerTable.addCell(makeValueCell(pf.telField.getText()));
                passengerTable.addCell(makeValueCell(pf.ageField.getText()));
            }

            document.add(passengerTable);

            // Footer Note
            Paragraph note = new Paragraph("Please show this ticket at the counter for check-in to retrieve your boarding pass.")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic()
                    .setFontSize(10)
                    .setMarginTop(30);
            document.add(note);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Cell makeHeaderCell(String content) {
        return new Cell()
                .add(new Paragraph(content).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1));
    }

    private static Cell makeValueCell(String content) {
        return new Cell()
                .add(new Paragraph(content))
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(new SolidBorder(1));
    }
}
