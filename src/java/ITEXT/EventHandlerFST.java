/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ITEXT;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

/**
 *
 * @author P3017OSI
 */
public class EventHandlerFST implements IEventHandler {

    private Table crearTablaFirmas() {
        Table Firmas = new Table(2);
        Firmas.setBorder(null);
        Firmas.setWidth(UnitValue.createPercentValue(100));
        Firmas.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("------------------------------").setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("Solicitante del Servicio").setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("(FIRMA Y SELLO)").setTextAlignment(TextAlignment.RIGHT))
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER));
        Firmas.addCell(new Cell().setBorder(Border.NO_BORDER)
                .add(new Paragraph("-----------------------------").setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("Técnio Responsable \"OSI\"").setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("(NOMBRE Y APELLIDO)").setTextAlignment(TextAlignment.RIGHT))
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER));
        return Firmas;
    }

    private Rectangle crearRectanguloFirmas(int rectangulo) {

        PageSize ps = PageSize.A4;
        float offSet = 36;
        float columnHeight=(ps.getHeight()-offSet*2)/2;
        if (rectangulo == 1) {
            //x,y,ancho, alto
            Rectangle rectanguloFirma = new Rectangle(0, offSet,columnHeight-10, 50);
            return rectanguloFirma;
        } else {
            Rectangle rectanguloFirma = new Rectangle(ps.getHeight()/2, offSet,columnHeight-10, 50);
            return rectanguloFirma;
        }

    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        try {
            Table firmas1 = this.crearTablaFirmas();
            Rectangle rectangulo1 = this.crearRectanguloFirmas(1);
            Canvas canvasFirmas1 = new Canvas(canvas, pdfDoc, rectangulo1);
            canvasFirmas1.add(firmas1);

            Table firmas2 = this.crearTablaFirmas();
            Rectangle rectangulo2 = this.crearRectanguloFirmas(2);
            Canvas canvasFirmas2 = new Canvas(canvas, pdfDoc, rectangulo2);
            canvasFirmas2.add(firmas2);
        } catch (Exception e) {
        }
    }

}
