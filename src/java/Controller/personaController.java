package Controller;

import DAO.personaDAO;
import DAO.practicanteDAO;
import MODELO.persona;
import MODELO.practicante;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class personaController {

    private personaDAO objDAO;

    public personaController() {
        objDAO = new personaDAO();
    }

    @RequestMapping("personal.htm")
    public String personal(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            switch ((int) session.getAttribute("rol")) {
                case 1:
                    url = "redirect:index.htm";
                    break;
                case 2:
                    url = "personal";
                    modelo.addAttribute("listaPersonaPrincipal", objDAO.listaPrincipalPersona());
                    modelo.addAttribute("persona", new persona());
                    break;
                case 3:
                      url = "redirect:index.htm";
                    break;
            }
        } else {
            url = "redirect:sesion.htm";
        }
        return url;
    }

    @RequestMapping(value = "personal.htm", method = RequestMethod.POST)
    public String nuevaPersona(HttpServletRequest request, @ModelAttribute persona p, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            if (p.getId_persona() != 0) {
                //EDITAR PERSONA
                if (objDAO.actualizarPersona(p)) {
                    url = "redirect:personal.htm";
                }
            } else {
                //REGISTRAR NUEVA PERSONA
                if (objDAO.insertarPersona(p)) {
                    url = "redirect:personal.htm";
                }
            }
            modelo.addAttribute("listaPersonaPrincipal", objDAO.listaPrincipalPersona());
            modelo.addAttribute("persona", new persona());
        } else {
            url = "redirect:sesion.htm";
        }
        return url;
    }

    @RequestMapping(value = "eliminarPersona.htm", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarPersona(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        JsonObject JSON = new JsonObject();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            if (objDAO.eliminarPersona(Integer.parseInt(request.getParameter("id").trim()))) {
                JSON.addProperty("RESPUESTA", objDAO.vPrincipalPersonaHTML());
                JSON.addProperty("SESIONFINALIZADA", false);
            }
        } else {
            JSON.addProperty("SESIONFINALIZADA", true);
        }
        return JSON.toString();
    }

    @RequestMapping(value = "detallesPersona.htm", method = RequestMethod.POST)
    @ResponseBody
    public String detallesPersona(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        JsonObject JSON = new JsonObject();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        StringBuilder fileJson = new StringBuilder();
        Gson Json = new GsonBuilder().serializeNulls().create();
        if (usuario != null) {
            persona p = objDAO.detallesPersona(Integer.parseInt(request.getParameter("id")));

            JsonObject object = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            object.addProperty("apellidos", p.getApellidos());
            object.addProperty("cargo", p.getCargo());
            object.addProperty("correo", p.getCorreo());
            object.addProperty("direccion", p.getDireccion());
            object.addProperty("dni", p.getDni());
            object.addProperty("estado", p.getEstado());
            object.addProperty("id_persona", p.getId_persona());
            object.addProperty("nombres", p.getNombres());
            object.addProperty("sexo", p.getSexo());
            object.addProperty("telefono", p.getTelefono());
            object.addProperty("SESIONFINALIZADA", false);

            StringTokenizer tokenizer = new StringTokenizer(p.getHorario(), ",");
            while (tokenizer.hasMoreElements()) {
                jsonArray.add(Integer.parseInt(tokenizer.nextToken().trim()));
            }
            object.add("horario", jsonArray);
            fileJson.append(Json.toJson(object));
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("SESIONFINALIZADA", true);
            fileJson.append(Json.toJson(object));
        }
        return fileJson.toString();
    }

    @RequestMapping(value = "detallesPracticantes.htm", method = RequestMethod.POST)
    @ResponseBody
    public String detallesPracticante(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        JsonObject JSON = new JsonObject();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        StringBuilder fileJson = new StringBuilder();
        Gson Json = new GsonBuilder().serializeNulls().create();
        if (usuario != null) {
            persona p = objDAO.detallesPersona(Integer.parseInt(request.getParameter("id")));
            practicante prac = new practicanteDAO().detallesPracticante(Integer.parseInt(request.getParameter("id")));

            JsonObject object = new JsonObject();
            object.addProperty("apellidos", p.getApellidos());
            object.addProperty("id_persona", p.getId_persona());
            object.addProperty("nombres", p.getNombres());
            object.addProperty("horas", prac.getHoras_x_dia());
            object.addProperty("dias", prac.getTotal_dias_laborables());
            object.addProperty("SESIONFINALIZADA", false);
            if (prac.getId_prac() == 0) {
                object.addProperty("ISPRACTICANTE", false);
            } else {
                object.addProperty("ISPRACTICANTE", true);
            }

            fileJson.append(Json.toJson(object));
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("SESIONFINALIZADA", true);
            fileJson.append(Json.toJson(object));
        }
        return fileJson.toString();
    }

    @RequestMapping(value = "updatePracticante.htm", method = RequestMethod.POST)
    public String updatePracticante(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            url = "personal";
            int[] practicante = new int[3];
            practicante[0] = Integer.parseInt(request.getParameter("id_persona"));
            practicante[1] = Integer.parseInt(request.getParameter("horas"));
            practicante[2] = Integer.parseInt(request.getParameter("dias"));
            new practicanteDAO().updatePracticante(new practicante(0, practicante[0], practicante[1], practicante[2]));

            modelo.addAttribute("listaPersonaPrincipal", objDAO.listaPrincipalPersona());
            modelo.addAttribute("persona", new persona());
        }
        return url;
    }

    @RequestMapping(value = "esPracticante.htm", produces = "application/json")
    @ResponseBody
    public String esPracticante(@RequestParam("id") int id_pers, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            practicante prac = new practicanteDAO().detallesPracticante(id_pers);
            if (prac.getId_prac() != 0) {
                JSON.addProperty("ESPRACTICANTE", Boolean.TRUE);
            } else {
                JSON.addProperty("ESPRACTICANTE", Boolean.FALSE);
            }
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }

    @RequestMapping(value = "Ficha_Asistencia_Practicante.htm", method = RequestMethod.POST, produces = "application/pdf")
    public void generarAsistenciaPDF(HttpServletResponse response, HttpServletRequest request, @RequestParam("id_pers") int id_pers, @RequestParam("nombres") String nombrePrac) throws Exception {

        //RECUPERAMOS ALGUNOS DATOS DE LA SOLICITUD
        String requestFecha = request.getParameter("fecha");
        String requestNResolucion = request.getParameter("nResolucion");

        //OBTEMOS EL HISTORIAL DE ASISTENCIAS DEL PRACTICANTE
        java.util.List<MODELO.asistencia> asistencia = new DAO.asistenciaDAO().ListaAsistenciaTotalPracticante(id_pers);

        //CREAMOS STYLOS DE FUENTES 
        String rutaAnanda = request.getSession().getServletContext().getRealPath("/PUBLIC/fuentes") + "/AnandaBlackPersonalUse.ttf";
        PdfFont anandaFuente = PdfFontFactory.createFont(rutaAnanda, PdfEncodings.WINANSI, true);

        String rutaCaslonCP = request.getSession().getServletContext().getRealPath("/PUBLIC/fuentes") + "/CaslonCP.otf";
        PdfFont CaslonCPFuente = PdfFontFactory.createFont(rutaCaslonCP, PdfEncodings.WINANSI, true);

        String rutaTIMESS__ = request.getSession().getServletContext().getRealPath("/PUBLIC/fuentes") + "/TIMESS__.ttf";
        PdfFont TIMESS__Fuente = PdfFontFactory.createFont(rutaTIMESS__, PdfEncodings.WINANSI, true);

        String rutaRoboto_MediumItalic = request.getSession().getServletContext().getRealPath("/PUBLIC/fuentes") + "/Roboto_MediumItalic.ttf";
        PdfFont Roboto_MediumItalicFuente = PdfFontFactory.createFont(rutaRoboto_MediumItalic, PdfEncodings.WINANSI, true);

        //CREAR IMAGEN
        Image logo = new Image(ImageDataFactory.create(request.getSession().getServletContext().getRealPath("/PUBLIC/img") + "/repositorio-unalm.png")).setWidth(80);

        //EMPESAMOS CON LA CREACION DEL DOCUMENTO   
        PdfDocument pdf = new PdfDocument(new PdfWriter(response.getOutputStream()));

        //ASOCIOAMOS EL MANEJADOR DE EVENTOS
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new ITEXT.EventHandlerPDF(nombrePrac, TIMESS__Fuente, CaslonCPFuente, anandaFuente, logo, requestNResolucion, requestFecha, Roboto_MediumItalicFuente));
        PageSize ps = PageSize.A4;
        Document document = new Document(pdf, ps);

        //Set column parameters
        float offSet = 36;
        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 2;
        float columnHeight = ps.getHeight() - offSet * 2;
        //float altoCuartaParte = columnHeight / 4;
        //float altoSextaParte = columnHeight / 6;
        float altoQuintaParte = columnHeight / 5;

        Rectangle[] columns = {
            // x    y  ancho   alto
            //new Rectangle(offSet, offSet + columnHeight - altoSextaParte, (ps.getWidth() - offSet * 2 + 10), altoSextaParte),
            new Rectangle(offSet - 5, offSet + 80, columnWidth, columnHeight - altoQuintaParte - 80),
            new Rectangle(offSet + columnWidth + 5, offSet + 80, columnWidth, columnHeight - altoQuintaParte - 80)
        };

        document.setRenderer(new ColumnDocumentRenderer(document, columns));

        //CREAMOS TABLA DE ASISTENCIAS
        Table cuerpoT = new Table(new float[]{1, 1.5f, 1.5f});
        cuerpoT.setBorder(Border.NO_BORDER);
        cuerpoT.setWidth(UnitValue.createPercentValue(100));

        String fechaTemp = null; // dd/MM/YYYY
        java.util.Calendar fecha = null;
        if (!asistencia.isEmpty()) {
            fechaTemp = asistencia.get(0).getFecha();
            fecha = new java.util.GregorianCalendar(Integer.parseInt(fechaTemp.substring(6)), Integer.parseInt(fechaTemp.substring(3, 5)) - 1, Integer.parseInt(fechaTemp.substring(0, 2)));
            cuerpoT.addCell(new Cell(1, 3).add(new Paragraph().setFontSize(16).setFont(CaslonCPFuente).setTextAlignment(TextAlignment.CENTER).add(new java.text.SimpleDateFormat("MMMM 'de' YYYY").format(fecha.getTime()))));

            cuerpoT.addCell(new Cell().setBackgroundColor(Color.GRAY).add(new Paragraph("FECHA").setFontColor(Color.WHITE).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));
            cuerpoT.addCell(new Cell().setBackgroundColor(Color.GRAY).add(new Paragraph("HORA DE INGRESO").setFontColor(Color.WHITE).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));
            cuerpoT.addCell(new Cell().setBackgroundColor(Color.GRAY).add(new Paragraph("HORA DE SALIDA").setFontColor(Color.WHITE).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));

        }
        for (int i = 0; i < asistencia.size(); i++) {
            //COMPARAMOS MM/YYYY == MM/YYYY
            if (asistencia.get(i).getFecha().substring(3).equals(fechaTemp.substring(3))) {

                cuerpoT.addCell(new Cell().add(new Paragraph().setFontSize(10).setTextAlignment(TextAlignment.CENTER).add(asistencia.get(i).getFecha())));
                cuerpoT.addCell(new Cell().add(new Paragraph().setFontSize(10).setTextAlignment(TextAlignment.CENTER).add(asistencia.get(i).getHora_de_ingreso())));
                cuerpoT.addCell(new Cell().add(new Paragraph().setFontSize(10).setTextAlignment(TextAlignment.CENTER).add(asistencia.get(i).getHora_de_salida())));
            } else {
                fechaTemp = asistencia.get(i).getFecha().trim();
                fecha.set(Integer.parseInt(fechaTemp.substring(6)), Integer.parseInt(fechaTemp.substring(3, 5)) - 1, Integer.parseInt(fechaTemp.substring(0, 2)));
                cuerpoT.addCell(new Cell(1, 3).add(new Paragraph().setFontSize(16).setFont(CaslonCPFuente).setTextAlignment(TextAlignment.CENTER).add(new java.text.SimpleDateFormat("MMMM 'de' YYYY").format(fecha.getTime()))));

                cuerpoT.addCell(new Cell().setBackgroundColor(Color.GRAY).add(new Paragraph("FECHA").setFontColor(Color.WHITE).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));
                cuerpoT.addCell(new Cell().setBackgroundColor(Color.GRAY).add(new Paragraph("HORA DE INGRESO").setFontColor(Color.WHITE).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));
                cuerpoT.addCell(new Cell().setBackgroundColor(Color.GRAY).add(new Paragraph("HORA DE SALIDA").setFontColor(Color.WHITE).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));

                cuerpoT.addCell(new Cell().add(new Paragraph().setFontSize(10).setTextAlignment(TextAlignment.CENTER).add(asistencia.get(i).getFecha())));
                cuerpoT.addCell(new Cell().add(new Paragraph().setFontSize(10).setTextAlignment(TextAlignment.CENTER).add(asistencia.get(i).getHora_de_ingreso())));
                cuerpoT.addCell(new Cell().add(new Paragraph().setFontSize(10).setTextAlignment(TextAlignment.CENTER).add(asistencia.get(i).getHora_de_salida())));
            }
        }
        document.add(cuerpoT);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph(String.format("TOTAL DÍAS ASISTIDOS: %d", asistencia.size())).setBold());

        document.close();
    }

    @RequestMapping(value = "filtroPersonaPorcargo.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filtroPorCargo(@RequestParam("cargo") int cargo, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            JSON.addProperty("perHTML", objDAO.listaPersonalPorCargoHTML(cargo));
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }
}