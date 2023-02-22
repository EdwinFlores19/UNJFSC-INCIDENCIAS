package Controller;

import DAO.perifericoDAO;
import DAO.incidenciaAtencionDAO;
import DAO.incidenciaDAO;
import MODELO.incidencia;
import MODELO.incidenciaAtencion;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class incidenciaController {

    private incidenciaDAO objInciDAO;

    public incidenciaController() {
        objInciDAO = new incidenciaDAO();
    }

    @RequestMapping(value = "incidencia.htm", method = RequestMethod.GET)
    public String incidencia(HttpServletRequest request, Model modelo) throws SQLException {
        HttpSession session = request.getSession(false);
        ModelAndView mav = new ModelAndView();
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (session != null & usuario != null) {
            url = "incidencia";
            modelo.addAttribute("incidenciaModelo", new MODELO.incidencia());
            modelo.addAttribute("listaPrincipalIncidencias", objInciDAO.listaPrincipalIncidencias());
            modelo.addAttribute("listaCantidadIncidenciaxEstado", objInciDAO.listaCantidadIncidenciaxEstado(new java.text.SimpleDateFormat("YYYY-MM-dd").format(new java.util.GregorianCalendar().getTime())));
            modelo.addAttribute("listapPracticanteAndContratados", objInciDAO.listaPersonaDisponibleHoy());
            modelo.addAttribute("fichaServicioTecnico", new MODELO.FichaServicioTecnico());
            if (new DAO.programacionDAO().ejecutarConsulta("select top 1 * from dbo.V_PROGRAMACION where id_progra_esta=2 and fecha_progra=CAST(GETDATE() AS DATE)").next()) {
                modelo.addAttribute("tareasProgramadas", objInciDAO.listaPersonaDisponibleHoy());
            }
        }

        return url;
    }

    @RequestMapping(value = "incidencia.htm", method = RequestMethod.POST)
    public String InsertIncidencia(@ModelAttribute incidencia inci, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
        
            System.out.println("id de la incidencia a editar: "+ inci.getId_inci());
            if (inci.getId_inci() != null) {
                // ACTUALIZAMOS LA INCIDENCIA S
                objInciDAO.updateIncidencia(inci);
            } else {
                //REGISTRAMOS LA NUEVA INCIDENCIA
                objInciDAO.insertarIncidencia(inci, Integer.parseInt(String.valueOf(session.getAttribute("id"))));
            }
            return "redirect:incidencia.htm";
        } else {
            return "redirect:sesion.htm";
        }
    }

    @ModelAttribute("medioLista")
    public Map<Integer, String> listaMedios() {
        return new DAO.inicidenciaMedioDAO().listaMedio();
    }

    @ModelAttribute("incidenciaDetalleLista")
    public Map<Integer, String> listaIncidenciaDetalle() {
        return new DAO.inicidenciaDetalleDAO().listaIncidenciaDetalle();
    }

    @ModelAttribute("oficinaLista")
    public Map<Integer, String> listaOficina() {
        return new DAO.oficinaDAO().listaOficina();
    }

    @RequestMapping(value = "unidad.htm", method = RequestMethod.POST)
    @ResponseBody
    public String listaUnidad(@RequestParam int id_oficina, HttpServletRequest request) {
      HttpSession sesion = request.getSession(false);
        JsonObject object = new JsonObject();
        Object usuario = null;
        if (sesion != null) {
            usuario = sesion.getAttribute("usuario");
        }
        if (usuario != null) {
            object.addProperty("unidades", objInciDAO.listaUnidadxOficinaHTML(id_oficina));
            return object.toString();
        } else {
            object.addProperty("SESIONFINALIZADA", Boolean.TRUE);
            return object.toString();
        }
    }

    @RequestMapping(value = "detallesIncidencia.htm", method = RequestMethod.POST)
    @ResponseBody
    public String detallesIncidencia(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        JsonObject object = new JsonObject();
        Object usuario = null;
        if (sesion != null) {
            usuario = sesion.getAttribute("usuario");
        }
        if (usuario != null) {
            List<MODELO.incidenciaAtencion> atencion = new incidenciaAtencionDAO().ListaincidenciaAtencion(request.getParameter("id"));
            incidencia I = objInciDAO.detallesIncidencia(request.getParameter("id"));
            MODELO.persona per = new DAO.personaDAO().detallesPersona(I.getId_pers());

            JsonArray objectArray = new JsonArray();
            object.addProperty("id", I.getId_inci());
            object.addProperty("fecha", I.getFecha().substring(0, 10));
            object.addProperty("hora", I.getFecha().substring(11));
            object.addProperty("registrador", per.getApellidos().concat(" " + per.getNombres()));
            object.addProperty("incidencia", I.getId_inci_detalle());
            object.addProperty("detalle", I.getInci_detalle());
            object.addProperty("oficina", I.getId_oficina());
            object.addProperty("unidad", I.getId_unidad());
            object.addProperty("encargado", I.getEncargado());
            object.addProperty("medio", I.getId_inci_medio());
            object.addProperty("comentario", I.getComentario());
            for (int i = 0; i < atencion.size(); i++) {
                objectArray.add(atencion.get(i).getPersona());
            }
            object.add("atencion", objectArray);
        } else {
            object.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return object.toString();
    }

    @RequestMapping(value = "inicidenciaEnProceso.htm", method = RequestMethod.POST)
    @ResponseBody
    public String inicidenciaProceso(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        JsonObject jsonObject = new JsonObject();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (session != null & usuario != null) {
            String[] personas = request.getParameterValues("id_pers");
            String incidencia = request.getParameter("id_incidencia");
            if (personas != null) {
                for (int i = 0; i < personas.length; i++) {
                    objInciDAO.insertarIncidenciaAtencion(incidencia, Integer.parseInt(personas[i]));
                }
                objInciDAO.cambiarEstadoIncidencia(incidencia, 1);
            }
            jsonObject.addProperty("RESPUESTA", true);
            jsonObject.addProperty("SESIONFINALIZADA", false);
        } else {
            jsonObject.addProperty("SESIONFINALIZADA", true);
        }

        return jsonObject.toString();
    }

    @RequestMapping(value = "inicidenciaFinalizado.htm", method = RequestMethod.POST)
    @ResponseBody
    public String inicidenciaFinalizado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        JsonObject jsonObject = new JsonObject();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (session != null & usuario != null) {
            String incidencia = request.getParameter("id_incidencia");
            objInciDAO.cambiarEstadoIncidencia(incidencia, 3);
            jsonObject.addProperty("RESPUESTA", true);
            jsonObject.addProperty("SESIONFINALIZADA", false);
        } else {
            jsonObject.addProperty("SESIONFINALIZADA", true);
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "cancelarIncidencia.htm", method = RequestMethod.POST)
    @ResponseBody
    public String cancelarIncidencia(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        JsonObject jsonObject = new JsonObject();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            String incidencia = request.getParameter("id_incidencia");

            jsonObject.addProperty("RESPUESTA", objInciDAO.cancelarIncidencia(incidencia));//TRUE SI SE CANCELO Y FALSE SI NO SE PUEDE CANCELAR
            jsonObject.addProperty("SESIONFINALIZADA", false);
        } else {
            jsonObject.addProperty("SESIONFINALIZADA", true);
        }
        return jsonObject.toString();
    }

    //INSERTA UN FILA EN LA TABLA FICHA DE SERVICIO TECNICO Y GENERA PDF
    @RequestMapping(value = "FichaServicioTecnico.htm", produces = "application/pdf", method = RequestMethod.POST)
    public Document FichaServicioTecnico(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MODELO.FichaServicioTecnico objFST) throws Exception {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        Document documento = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            //si la ficha ya exixte se actualizara todos sus campos menos la fecha
            new DAO.FichaServicioTecnicoDAO().insert_FichaServicioTecnico(objFST);
            documento = pdf(response, request, new DAO.FichaServicioTecnicoDAO().detalles_FichaServicioTecnico(objFST.getId_incidencia()), objInciDAO.detallesIncidencia(objFST.getId_incidencia()));
        }
        return documento;
    }

    @ModelAttribute("listaPerifericos")
    public Map<Integer, String> listaPerifericos() {
        return new perifericoDAO().listaPerifericos();
    }

    //si el estado es 3 de finbalizado devolver los datos de esa ficha y si aun no existe no devolvera nada
    @RequestMapping(value = "estadoIncidencia.htm", method = RequestMethod.POST)
    @ResponseBody
    public String estadoIncidencia(HttpServletRequest request, @RequestParam("id") String id) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String JSON = "";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            incidencia objIncidencia = objInciDAO.detallesIncidencia(id);
            if (objIncidencia.getId_inci_estado() == 3) {
                com.google.gson.Gson gson = new com.google.gson.GsonBuilder().serializeNulls().create();
                JSON = new DAO.FichaServicioTecnicoDAO().detalles_FichaServicioTecnicoJSON(id);
            } else {
                com.google.gson.JsonObject objObject = new com.google.gson.JsonObject();
                objObject.addProperty("Finalizado", Boolean.FALSE);
                JSON = objObject.toString();
            }
        } else {
            com.google.gson.JsonObject objObject = new com.google.gson.JsonObject();
            objObject.addProperty("SESIONFINALIZADA", Boolean.TRUE);
            JSON = objObject.toString();
        }
        return JSON;
    }

    // CREA EL DOCUMENTO
    public Document pdf(HttpServletResponse response, HttpServletRequest request, MODELO.FichaServicioTecnico objFST, incidencia objIncidencia) throws Exception {
        //recuperamos los nombres de la oficina, unidad e incidencia
        String oficina = new DAO.oficinaDAO().detallesOficina(objIncidencia.getId_oficina(), null).getOficina();
        String unidad = new DAO.unidadDAO().detallesUnidad(objIncidencia.getId_unidad()).getUnidad();
        String incidencia = new DAO.inicidenciaDetalleDAO().listaIncidenciaDetalle().get(objIncidencia.getId_inci_detalle());
        String periferico = new DAO.perifericoDAO().nombrePeriferico(objFST.getId_periferico());
        if (periferico.equalsIgnoreCase("otro")) {
            if (objFST.getPeriferico_detalle() != null) {
                periferico = objFST.getPeriferico_detalle();
            }
        }

        //CREAMOS EL DOCUMENTO
        PdfWriter escritor = new PdfWriter(response.getOutputStream());
        PdfDocument pdfdocument = new PdfDocument(escritor);
        pdfdocument.addEventHandler(PdfDocumentEvent.END_PAGE, new ITEXT.EventHandlerFST());
        PageSize objPageSize = PageSize.A4;
        Document documento = new Document(pdfdocument, objPageSize.rotate());

        //PARAMETROS DE LAS COLUMNAS
        float offSet = 36;
        float anchoColumna = (objPageSize.getHeight() - (offSet * 2 + 10)) / 2;
        float altoColumna = objPageSize.getWidth() - offSet * 2;

        //DEFINIMOS EL AREA DE LAS 2 COLUMNAS
        Rectangle[] columnas = {new Rectangle(offSet - 5, offSet, anchoColumna, altoColumna),
            new Rectangle(offSet + anchoColumna + 5, offSet, anchoColumna, altoColumna)
        };
        documento.setRenderer(new ColumnDocumentRenderer(documento, columnas));

        //CREAR IMAGEN
        Image logo = new Image(ImageDataFactory.create(request.getSession().getServletContext().getRealPath("/PUBLIC/img") + "/logoOSI.png")).setWidth(80);

        //CREAR TABLA PARA LA CABEZERA QUE CONTENDRA EL LOGO Y EL TITULO
        Table tablaHeader = new Table(new float[]{1, 4});
        tablaHeader.setBorder(null);
        tablaHeader.setWidth(UnitValue.createPercentValue(100));
        tablaHeader.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));

        //CREACION DE UNA CELDA QUE CONTIENE 3 PARRAFOS QUE CONSTITUIRA EL TITULO DE LA TABLA CABEZERA
        Cell celdaHeader = new Cell();
        celdaHeader.add(new Paragraph("UNIVERSIDAD NACIONAL \"JOSE FAUSTINO SANCHEZ CARRION\" ").setTextAlignment(TextAlignment.CENTER).setFontSize(9).setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN)))
                .add(new Paragraph("OFICINA DE SERVICIOS INFORMATICOS").setTextAlignment(TextAlignment.CENTER).setPaddingTop(10).setFont(PdfFontFactory.createFont(request.getSession().getServletContext().getRealPath("/PUBLIC/fuentes") + "/CaslonCP.otf", PdfEncodings.WINANSI, true)))
                .add(new Paragraph(String.format("FICHA DE SERVICIO TECNICO Nº %s", objFST.getId_fst())).setPaddingTop(5).setTextAlignment(TextAlignment.LEFT).setFontSize(11).setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD)))
                .add(new Paragraph("Email: informatica@unjfsc.edu.pe    Teléfono: 232-1338").setFontSize(9).setTextAlignment(TextAlignment.RIGHT))
                .setBorder(Border.NO_BORDER);
        tablaHeader.addCell(celdaHeader);
        documento.add(tablaHeader);

        //TABLA CON DATOS RELEVANTES
        Table tablaDatos = new Table(5);
        tablaDatos.setBorder(null);
        tablaDatos.setWidth(UnitValue.createPercentValue(100));
        tablaDatos.setFontSize(9);
        tablaDatos.addCell(new Cell().add(new Paragraph("Equipo:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell(1, 2).add(new Paragraph(periferico)).setBorder(Border.NO_BORDER));//Equipo
        tablaDatos.addCell(new Cell().add(new Paragraph("Código Equipo:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph(objFST.getCod_equipo())).setBorder(Border.NO_BORDER));//Codigo Equipo

        tablaDatos.addCell(new Cell().add(new Paragraph("Oficina:")).setBorder(Border.NO_BORDER));//
        tablaDatos.addCell(new Cell(1, 4).add(new Paragraph(oficina)).setBorder(Border.NO_BORDER));// area Solicitante
        tablaDatos.addCell(new Cell().add(new Paragraph("Unidad:")).setBorder(Border.NO_BORDER));//
        tablaDatos.addCell(new Cell(1, 4).add(new Paragraph(unidad)).setBorder(Border.NO_BORDER));// unidad Solicitante

        tablaDatos.addCell(new Cell().add(new Paragraph("Usuario:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell(1, 2).add(new Paragraph(objIncidencia.getEncargado())).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("Telefono:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("H. de inicio:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("H. de termino:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph("Fecha:")).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell().add(new Paragraph(objIncidencia.getFecha().substring(0, 10))).setBorder(Border.NO_BORDER));
        tablaDatos.addCell(new Cell(1, 2).setBorder(Border.NO_BORDER).add(new Paragraph("TRASLADO AL TALLER PARA REVISIÓN:").setFontSize(9).setFont(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD))));
        tablaDatos.addCell(new Cell().setBorder(new SolidBorder(1)).add(new Paragraph(objFST.getTraslado()).setFontColor(Color.MAGENTA).setTextAlignment(TextAlignment.CENTER)));
        documento.add(tablaDatos);
        documento.add(new Paragraph("\n"));

        Table tableOpciones = new Table(5);
        tableOpciones.setWidth(UnitValue.createPercentValue(100));
        tableOpciones.addHeaderCell(new Cell(1, 2).add(new Paragraph("Requiere Informe del Documento").setFontSize(9)));
        tableOpciones.addHeaderCell(new Cell().add(new Paragraph("PLACA")).setFontSize(9));
        tableOpciones.addHeaderCell(new Cell().add(new Paragraph("PROCESADOR")).setFontSize(9));
        tableOpciones.addHeaderCell(new Cell().add(new Paragraph("MEMORIA")).setFontSize(9));
        tableOpciones.addCell(new Cell().add(new Paragraph("SI(  )").setFontSize(13)).setTextAlignment(TextAlignment.CENTER));
        tableOpciones.addCell(new Cell().add(new Paragraph("NO(  )").setFontSize(13)).setTextAlignment(TextAlignment.CENTER));
        tableOpciones.addCell(new Cell().add(new Paragraph(" ").setFontSize(14)));
        tableOpciones.addCell(new Cell().add(new Paragraph(" ").setFontSize(14)));
        tableOpciones.addCell(new Cell().add(new Paragraph(" ").setFontSize(14)));
        documento.add(tableOpciones);

        Table tProblema = new Table(5);
        tProblema.setMarginTop(10);
        tProblema.setFontSize(9);
        tProblema.setWidth(UnitValue.createPercentValue(100));
        tProblema.addCell(new Cell().add(new Paragraph("PROBLEMA: ")));
        tProblema.addCell(new Cell(1, 4).add(new Paragraph(" ")).add(new Paragraph(incidencia)));
        documento.add(tProblema);

        Table tServicio = new Table(5);
        tServicio.setBorder(null);
        tServicio.setMarginTop(10);
        tServicio.setFontSize(9);
        tServicio.setWidth(UnitValue.createPercentValue(100));
        tServicio.addCell(new Cell().add(new Paragraph("SERVICIO CORRECTIVO:")).add(new Paragraph(" ")));
        tServicio.addCell(new Cell(1, 4).add(new Paragraph(" ")).add(new Paragraph(objFST.getS_correctivo())));
        documento.add(tServicio);

        Table tRecomendacion = new Table(5);
        tRecomendacion.setBorder(null);
        tRecomendacion.setMarginTop(10);
        tRecomendacion.setFontSize(9);
        tRecomendacion.setWidth(UnitValue.createPercentValue(100));
        tRecomendacion.addCell(new Cell().add(new Paragraph("RECOMENDACIÓN:")).add(new Paragraph(" ")));
        tRecomendacion.addCell(new Cell(1, 4).add(new Paragraph(" ")).add(new Paragraph(objFST.getRecomendacion())));
        documento.add(tRecomendacion);

        //ESTAS DOS TXT FORMARAN UN SOLO PARRAFO, LOS SEPARAMOS PARA PÒDER DARLES ESTILOS POE SEPARADO
        Text txtNota = new Text("NOTA.").setUnderline();
        Text txtNotaDetalle = new Text("Este documento no es vàlido para tramitar cambio de accesorio, si desea solicitar el cambio de un accesorio deberá hacerlo con un informe emitido"
                + "por la Oficina de Servicios Informaticos");
        documento.add(new Paragraph().add(txtNota).setFontSize(8).add(" ").add(txtNotaDetalle).setFontSize(8).setTextAlignment(TextAlignment.CENTER));

        //AREA DE LAS FIRMAS
//        Table Firmas = new Table(2);
//        Firmas.setBorder(null);
//        Firmas.setMarginTop(30);
//        Firmas.setWidth(UnitValue.createPercentValue(100));
//        Firmas.addCell(new Cell().setBorder(Border.NO_BORDER)
//                .add(new Paragraph("------------------------------").setTextAlignment(TextAlignment.CENTER))
//                .add(new Paragraph("Solicitante del Servicio").setTextAlignment(TextAlignment.CENTER))
//                .add(new Paragraph("(FIRMA Y SELLO)").setTextAlignment(TextAlignment.RIGHT))
//                .setFontSize(9)
//                .setTextAlignment(TextAlignment.CENTER));
//        Firmas.addCell(new Cell().setBorder(Border.NO_BORDER)
//                .add(new Paragraph("-----------------------------").setTextAlignment(TextAlignment.CENTER))
//                .add(new Paragraph("Técnio Responsable \"OSI\"").setTextAlignment(TextAlignment.CENTER))
//                .add(new Paragraph("(NOMBRE Y APELLIDO)").setTextAlignment(TextAlignment.RIGHT))
//                .setFontSize(9)
//                .setTextAlignment(TextAlignment.CENTER));
//        documento.add(Firmas);
        //DUPLICAMOS EL CONTENIDO PARA LA COLUMNA 2
        documento.add(tablaHeader);
        documento.add(tablaDatos);
        documento.add(new Paragraph("\n"));
        documento.add(tableOpciones);
        documento.add(tProblema);
        documento.add(tServicio);
        documento.add(tRecomendacion);
        documento.add(new Paragraph().add(txtNota).add(" ").add(txtNotaDetalle).setFontSize(8).setTextAlignment(TextAlignment.CENTER));
        //documento.add(Firmas);

        documento.close();
        return documento;
    }

    @RequestMapping(value = "filtroIncidencias.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filtroIncidencias(@RequestParam("estado") int estado, @RequestParam("fecha") String fecha, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            return objInciDAO.listaIncidenciasHTML(estado, fecha);
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }

    @RequestMapping(value = "returnIdOficina.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String returnIdOficina(HttpServletRequest request, @RequestParam("nombre") String nomb_oficina) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            return new GsonBuilder().serializeNulls().create().toJson(new DAO.oficinaDAO().detallesOficina(0, nomb_oficina));
        } else {
            JsonObject JSON = new JsonObject();
            JSON.addProperty("SIONFINALIZADA", Boolean.TRUE);
            return JSON.toString();
        }
    }

    @RequestMapping(value = "atencionEnviada.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String returnAtencionEnviada(HttpServletRequest request, @RequestParam("id") String id_incidencia) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
         JsonObject JSON = new JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            StringBuilder html = new StringBuilder();
            List<incidenciaAtencion> atencion = new DAO.incidenciaAtencionDAO().ListaincidenciaAtencion(id_incidencia);
            for (incidenciaAtencion a : atencion) {
                html.append("<li>");
                html.append(a.getPersona());
                html.append("</li>");
            }
            JSON.addProperty("per_enviado", html.toString());
            JSON.addProperty("solicitante", objInciDAO.name_usuario_solicitante(id_incidencia));
            return JSON.toString();
        } else {
    
            JSON.addProperty("SIONFINALIZADA", Boolean.TRUE);
            return JSON.toString();
        }
    }
    
    @RequestMapping("reconciderarIncidenciaFilnalizada.htm")
    public String reconciderarIncidenciaFilnalizada(HttpServletRequest request, @RequestParam("id") String id_incidencia){
         HttpSession session = request.getSession(false);
        Object usuario = null;
         
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objInciDAO.reconciderar_incidencia_finalizada(id_incidencia);
            return "redirect:incidencia.htm";
        }else{
            return "sesion.htm";
        }
    }
}
