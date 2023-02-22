/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ITEXT;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

/**
 *
 * @author P3017OSI
 */
public class EventHandlerPDF implements IEventHandler {
    
    private String nombrePrac;
    private PdfFont TIMESS__Fuente;
    private PdfFont CaslonCPFuente;
    private PdfFont anandaFuente;
    private PdfFont Roboto_MediumItalic;
    private Image logo;
    private String nResolucion;
    private String fecha;
    
    public EventHandlerPDF(String nombrePrac, PdfFont TIMESS__Fuente, PdfFont CaslonCPFuente, PdfFont anandaFuente, Image logo, String nResolucion, String fecha,PdfFont Roboto_MediumItalic) {
        this.nombrePrac = nombrePrac;
        this.TIMESS__Fuente = TIMESS__Fuente;
        this.CaslonCPFuente = CaslonCPFuente;
        this.anandaFuente = anandaFuente;
        this.logo = logo;
        this.nResolucion = nResolucion;
        this.fecha = fecha;
        this.Roboto_MediumItalic = Roboto_MediumItalic;
    }
    
    private Rectangle crearRectanguloEncabezado() {
        
        PageSize ps = PageSize.A4;
        float offSet = 36;
        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 2;
        float columnHeight = ps.getHeight() - offSet * 2;
        // float altoCuartaParte = columnHeight / 4;
       // float altoSextaParte = columnHeight / 6;
            float altoQuintaParte = columnHeight / 5;
        
        Rectangle rectanguloEncabezado = new Rectangle(offSet, offSet + columnHeight - altoQuintaParte, (ps.getWidth() - offSet * 2 + 10), altoQuintaParte      );
        return rectanguloEncabezado;
    }
    
    private Table crearTablaEncabezado() throws Exception {
        Table encabezado = new Table(new float[]{1, 4});
        encabezado.setMarginBottom(10);
        encabezado.setBorder(Border.NO_BORDER);
        encabezado.setWidth(UnitValue.createPercentValue(85));
        encabezado.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph()));
        encabezado.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Universidad Nacional José Faustino Sánchez Carrión").setTextAlignment(TextAlignment.CENTER).setFont(anandaFuente)));
        
        encabezado.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph().setTextAlignment(TextAlignment.CENTER).add(logo)));
        Cell celdaHeader = new Cell().setBorder(Border.NO_BORDER);
        celdaHeader.add(new Paragraph("OFICINA DE SERVICIOS INFORMATICOS").setTextAlignment(TextAlignment.CENTER).setFontSize(12).setFont(CaslonCPFuente))
                .add(new Paragraph("ASISTENCIA DE PRACTICANTES").setTextAlignment(TextAlignment.CENTER).setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN)).setFontSize(16).setBold())
                .add(new Paragraph(String.format("RESOLUCIÓN DE DECANATO Nº %s-%s-FIISI-UNJFSC", nResolucion, fecha.substring(0, 4))).setFont(Roboto_MediumItalic).setFontColor(Color.GRAY).setTextAlignment(TextAlignment.CENTER).setFontSize(12))
                .add(new Paragraph("Huacho, ").setFont(Roboto_MediumItalic).setFontColor(Color.GRAY).setFontSize(10).add(new java.text.SimpleDateFormat("MMMMM MM 'del' yyyy").format(new java.util.GregorianCalendar(Integer.parseInt(fecha.substring(0, 4)), Integer.parseInt(fecha.substring(5, 7)), Integer.parseInt(fecha.substring(8))).getTime())).setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph(nombrePrac.toUpperCase()).setFontSize(14).setFont(TIMESS__Fuente).setBold().setTextAlignment(TextAlignment.CENTER).setUnderline());
        
        encabezado.addCell(celdaHeader);
        return encabezado;
        
    }
    
    private Table crearTablaPie(int pagina) {
        Table pie = new Table(1);   
        pie.setBorder(Border.NO_BORDER);
        pie.setWidth(UnitValue.createPercentValue(100));
        pie.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(String.format("Pagina %d", pagina)).setTextAlignment(TextAlignment.RIGHT)));
        return pie;
        
    }
    
    private Rectangle crearRectanguloPie() {
        
        PageSize ps = PageSize.A4;
        float offSet = 36;
        
        Rectangle rectanguloEncabezado = new Rectangle(offSet, 30, (ps.getWidth() - offSet * 2 + 10), 10);
        return rectanguloEncabezado;
    }
    
    @Override
    public void handleEvent(Event event) {
        
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        int pageNumber = pdfDoc.getPageNumber(page);
        
        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        try {
            Table encabezado = this.crearTablaEncabezado();
            Rectangle rectanguloEncabezado = this.crearRectanguloEncabezado();
            Canvas canvasEncabezado = new Canvas(canvas, pdfDoc, rectanguloEncabezado);
            canvasEncabezado.add(encabezado);
            
            Table pie = this.crearTablaPie(pageNumber);
            Rectangle rectanguloPie = this.crearRectanguloPie();
            Canvas canvasPie = new Canvas(canvas, pdfDoc, rectanguloPie);
            canvasPie.add(pie);
        } catch (Exception e) {
        }
        
    }
    
}
