package Controller;

import DAO.asistenciaDAO;
import MODELO.asistencia;
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
public class asistenciaController {

    asistenciaDAO objAsistenciaDao;

    public asistenciaController() {
        objAsistenciaDao = new asistenciaDAO();
    }

    //----------ESTE METODO RETORNA LA VISTA asistencia.jsp------------------------------
    @RequestMapping(value = "asistencia.htm", method = RequestMethod.GET)
    public String asistencia(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            switch ((int) session.getAttribute("rol")) {
                case 1:
                    url = "redirect:index.htm";
                    break;
                case 2:
                    url = "asistencia";
                    modelo.addAttribute("listaPrincipal", objAsistenciaDao.listaPrincipalAsistencia());
                    modelo.addAttribute("listaPracticanteSinAsistencia", objAsistenciaDao.listaPracticanteSinAsistencia());
                    modelo.addAttribute("asistencia", new asistencia());
                    break;
                case 3:
                    url = "redirect:index.htm";
                    break;
            }

        }
        return url;
    }

    //----------ESTE METODO REGISTRA UNA O MAS ASISTENCIAS A LA VES EN LA VISTA aistencia.jsp----------------------
    @RequestMapping(value = "asistencia.htm", method = RequestMethod.POST)
    public String RegistrarAsistencia(HttpServletRequest request, @ModelAttribute asistencia asist, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            if (request.getParameterValues("practicantes") != null) {
                objAsistenciaDao.insert_asistencia(request.getParameterValues("practicantes"), asist);
            }
            url = "redirect:asistencia.htm";
        }
        return url;
    }

    // RETORNA DETALLES DE LA ASISTENCIA DE UN PRACTICANTE MEDIANTE AJAX
    @RequestMapping(value = "detallesAsistencia.htm", method = RequestMethod.POST)
    @ResponseBody
    public String detallesAsistencia(@RequestParam("date") String fecha, @RequestParam("name") String nombre, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            return objAsistenciaDao.detallesAsistencia(fecha, nombre);
        } else {
            com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
            JSON.addProperty("SesionFinalizada", true);
            return JSON.toString();
        }
    }

    //ESTE METODO ACUTALIZA UNA SOLA ASISTENCIA
    @RequestMapping(value = "updateAsistencia.htm", method = RequestMethod.POST)
    public String updateAsistencia(HttpServletRequest request, @ModelAttribute asistencia asist) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objAsistenciaDao.update_asistencia(asist);
            url = "redirect:asistencia.htm";
        }
        return url;
    }

    //-------------ESTE METODO RETORNA LA VISTA asistenciaAlumno.JSP------------------------------
    @RequestMapping(value = "registrarAsistencia.htm", method = RequestMethod.GET)
    public String asistenciaAlumno(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            modelo.addAttribute("listaPrincipal", objAsistenciaDao.listaPrincipalAsistencia());
            url = "asistenciaAlumno";
        }
        return url;
    }

    //----------ESTE METODO REGISTRA SOLO UNA ASISTENCIA EN LA VISTA asistenciaAlumno.JSP-------------------
    @RequestMapping(value = "registrarAsistencia.htm", method = RequestMethod.POST)
    public String registroAsistenciaAlumno(HttpServletRequest request, @RequestParam("dni") String dni) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "redirect:sesion.htm";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            objAsistenciaDao.insert_asistencia_practicante(dni);
            url = "redirect:registrarAsistencia.htm";
        }
        return url;

    }

    //retorna en formato HTML la historia de asistencias de un alumno
    @RequestMapping(value = "filtroAsistenciaPracticante.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filtroAsistenciaPraticante(@RequestParam("id_prac") int id_prac, @RequestParam("Fecha-inicio") String date_start, @RequestParam("Fecha-fin") String date_end, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            JSON.addProperty("asistenciaHTML", objAsistenciaDao.ListaAsistenciaPracticanteHTML(id_prac, date_start, date_end));
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }

    @ModelAttribute("listaPracticantes")
    public Map<Integer, String> listaPracticante() {
        return objAsistenciaDao.listaPracticante();
    }
}
