package Controller;

import DAO.programacionDAO;
import MODELO.V_PROGRAMACION_ATENCION;
import MODELO.programacion;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
public class programacionController {

    private programacionDAO objDAO;

    public programacionController() {
        objDAO = new programacionDAO();
    }

    @RequestMapping(value = "programacion.htm", method = RequestMethod.GET)
    public String programacion(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            switch ((int) session.getAttribute("rol")) {
                case 3:
                    url = "redirect:index.htm";
                    break;
                case 1:
                    url = "redirect:index.htm";
                    break;
                case 2:
                    url = "programacion";
                    modelo.addAttribute("listaProgramacionFechaHoy", objDAO.listaProgramacionFechaHoy());
                    modelo.addAttribute("programacionModelo", new programacion());
                    modelo.addAttribute("listapPracticanteAndContratados", new DAO.incidenciaDAO().listaPersonaDisponibleHoy());
                    modelo.addAttribute("listaCantidadTareasxEstado", objDAO.listaCantidadTareasxEstado(new java.text.SimpleDateFormat("YYYY-MM-dd").format(new java.util.GregorianCalendar().getTime())));
                    break;
            }

        }
        return url;
    }

    @RequestMapping(value = "atencion.htm", method = RequestMethod.POST)
    public String atencionProgramacion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            String[] personas = request.getParameterValues("id_pers");
            String id_programacion = request.getParameter("id_programacion");
            if (personas != null) {
                for (int i = 0; i < personas.length; i++) {
                    objDAO.insertarProgramacionAtencion(id_programacion, Integer.parseInt(personas[i]));
                }
                objDAO.cambiarEstadoProgramacion(id_programacion, 1);
            }
            url = "redirect:programacion.htm";
        }
        return url;
    }

    @RequestMapping(value = "finalizarTarea.htm", method = RequestMethod.GET)
    public String finalizarTarea(HttpServletRequest request, @RequestParam("id") String id) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objDAO.cambiarEstadoProgramacion(id, 3);
            url = "redirect:programacion.htm";
        }
        return url;
    }

    @ModelAttribute("oficinaLista")
    public Map<Integer, String> listaOficina() {
        return new DAO.oficinaDAO().listaOficina();
    }

    @ModelAttribute("listaTarea")
    public Map<Integer, String> listaTarea() {
        return objDAO.listaTarea();
    }

    @RequestMapping(value = "programacion.htm", method = RequestMethod.POST)
    public String nueva_Tarea(HttpServletRequest request, Model modelo, @ModelAttribute programacion objProgramacion) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objDAO.insert_programacion(objProgramacion, Integer.parseInt(String.valueOf(session.getAttribute("id"))));
            url = "redirect:programacion.htm";
        }
        return url;
    }

    @RequestMapping(value = "updateProgramacion.htm", method = RequestMethod.POST)
    public String updateprogramacion(HttpServletRequest request, @ModelAttribute programacion objProgramacion) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objDAO.update_programacion(objProgramacion);
            url = "redirect:programacion.htm";
        }
        return url;
    }

    @RequestMapping(value = "sePuedeCancelarTarea.htm", method = RequestMethod.GET)
    @ResponseBody
    public String sePuedeCancelarTarea(@RequestParam("id") String id) throws Exception {
        if (objDAO.ejecutarConsulta(String.format("select* from dbo.V_PROGRAMACION where id_progra='%s' and id_progra_esta=2", id)).next()) {
            return "true";
        } else {
            return "false";
        }
    }

    @RequestMapping(value = "cancelarTarea.htm", method = RequestMethod.GET)
    public String cancelarTarea(HttpServletRequest request, @RequestParam("id") String id) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objDAO.cambiarEstadoProgramacion(id, 4);
            url = "redirect:programacion.htm";
        }
        return url;
    }

    @RequestMapping(value = "filtroProgramacion.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filtroProgramacion(@RequestParam("estado") int estado, @RequestParam("fecha") String fecha, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            return objDAO.listaProgramacionHTML(estado, fecha);
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }

    @RequestMapping(value = "detallesProgramacion.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String detallesProgramacion(@RequestParam("id_progra") String id_progra, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            List<V_PROGRAMACION_ATENCION> atencion = new DAO.programacionAtencionDAO().detallesProgramacionAtencion(id_progra);
            programacion objProgramacion = objDAO.returnProgramacion(id_progra);
            MODELO.persona per = new DAO.personaDAO().detallesPersona(objProgramacion.getId_persona());

            JSON.addProperty("id_progra", objProgramacion.getId_progra());
            JSON.addProperty("fecha_progra", objProgramacion.getFecha_progra());
            JSON.addProperty("id_pers", per.getApellidos() + " " + per.getNombres());
            JSON.addProperty("id_progra_tarea", objProgramacion.getId_progra_tarea());
            JSON.addProperty("progra_tarea_deta", objProgramacion.getProgra_tarea_deta());
            JSON.addProperty("id_ofic", objProgramacion.getId_oficina());
            JSON.addProperty("id_unidad", objProgramacion.getId_unidad());
            JSON.addProperty("encargado", objProgramacion.getEncargado());
            JSON.addProperty("comentario", objProgramacion.getComentario());

            com.google.gson.JsonArray jsonArray = new com.google.gson.JsonArray();
            for (V_PROGRAMACION_ATENCION v : atencion) {
                jsonArray.add(v.getNombres());
            }

            JSON.add("atencion", jsonArray);
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }

    @RequestMapping(value = "unidad_por_oficina.htm", method = RequestMethod.POST)
    @ResponseBody
    public String listaUnidad(@RequestParam int id_oficina) {
        return objDAO.listaUnidadxOficinaHTML(id_oficina);
    }
    
    @RequestMapping(value = "atencionEnviadaProgramacion.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
             List<V_PROGRAMACION_ATENCION> atencion = new DAO.programacionAtencionDAO().detallesProgramacionAtencion(id_incidencia);
            for (V_PROGRAMACION_ATENCION a : atencion) {
                html.append("<li>");
                html.append(a.getNombres());
                html.append("</li>");
            }
            JSON.addProperty("per_enviado", html.toString());
            return JSON.toString();
        } else {
    
            JSON.addProperty("SIONFINALIZADA", Boolean.TRUE);
            return JSON.toString();
        }
    }
}
