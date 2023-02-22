package Controller;

import DAO.oficinaDAO;
import DAO.unidadDAO;
import MODELO.oficina;
import MODELO.unidad;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class oficinaController {

    private oficinaDAO dao;

    public oficinaController() {
        dao = new oficinaDAO();
    }

    @RequestMapping("oficina.htm")
    public String asistencia(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        ModelAndView mav = new ModelAndView();
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
                    url = "oficina";
                    modelo.addAttribute("listaPrincipalOficina", dao.listaPrincipalOficina());
                    modelo.addAttribute("oficina", new oficina());
                    modelo.addAttribute("unidad", new unidad());
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

    @RequestMapping(value = "oficina.htm", method = RequestMethod.POST)
    public String insertarOficina(HttpServletRequest request, @ModelAttribute oficina ofi) {
        HttpSession session = request.getSession(false);
        ModelAndView mav = new ModelAndView();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            if (ofi.getId_oficina() == 0) {
                dao.insert_oficina(ofi);
            } else {
                dao.update_oficina(ofi);
            }
            return "redirect:oficina.htm";

        } else {
            return "redirect:sesion.htm";
        }
    }

    @RequestMapping(value = "detallesoficina.htm", method = RequestMethod.POST)
    @ResponseBody
    public String detallesOficina(HttpServletRequest request, @RequestParam("id") int id) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {

            return new GsonBuilder().serializeNulls().create().toJson(dao.detallesOficina(id,null));
        } else {
            JsonObject JSON = new JsonObject();
            JSON.addProperty("SIONFINALIZADA", Boolean.TRUE);
            return JSON.toString();
        }
    }

//    @ModelAttribute("oficinaLista")
//    public Map<Integer, String> listaOficina() {
//        return dao.listaOficina();
//    }

    @RequestMapping(value = "mantenimientoUnidad.htm", method = RequestMethod.POST)
    public String unidad(HttpServletRequest request, @ModelAttribute unidad unidad) {
        HttpSession session = request.getSession(false);
        ModelAndView mav = new ModelAndView();
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            if (unidad.getId_unidad() == 0) {
                new DAO.unidadDAO().insert_unidad(unidad);
            } else {
                new DAO.unidadDAO().update_unidad(unidad);
            }
            return "redirect:oficina.htm";
        } else {
            return "redirect:sesion.htm";
        }
    }

    @RequestMapping(value = "desactivarUnidad.htm", method = RequestMethod.GET)
    public String desactivarUnidad(HttpServletRequest request, @RequestParam("id") int id) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            new DAO.unidadDAO().delete_unidad(id);
            return "redirect:oficina.htm";
        } else {
            return "redirect:sesion.htm";
        }
    }
    @RequestMapping(value = "desactivarOficina.htm", method = RequestMethod.GET)
    public String desactivarOficina(HttpServletRequest request, @RequestParam("id") int id) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            dao.delete_oficina(id);
            return "redirect:oficina.htm";
        } else {
            return "redirect:sesion.htm";
        }
    }

    @RequestMapping(value = "detallesUnidad.htm", method = RequestMethod.POST)
    @ResponseBody
    public String detallesUnidad(@RequestParam("id") int id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            return new GsonBuilder().serializeNulls().create().toJson(new unidadDAO().detallesUnidad(id));
        } else {
            JsonObject JSON = new JsonObject();
            JSON.addProperty("SIONFINALIZADA", Boolean.TRUE);
            return JSON.toString();
        }
    }
    
    @RequestMapping(value = "filtroOficina.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filtroPorCargo(@RequestParam("oficina") String oficina, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            JSON.addProperty("oficinaHTML", dao.OficinasHTML(oficina));
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }
      @RequestMapping(value = "retornarUnidadesPorOficina", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String retornarUnidadesPorOficina(HttpServletRequest request,@RequestParam("id_oficina") int id_oficina){
         HttpSession session = request.getSession(false);
        Object usuario = null;
        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            StringBuilder html=new StringBuilder();
            List<Object[]> unidad=new DAO.unidadDAO().lista_unidad_oficina(id_oficina);
            for(Object[] o:unidad){
                html.append("<tr>");
                html.append("<td>");
                html.append(o[0].toString());
                html.append("</td>");
                html.append("<td>");
                html.append(o[2].toString());
                html.append("</td>");
                html.append("<td>");
                html.append(o[1].toString());
                html.append("</td>");
                html.append("<td>");
                html.append("<button class=\"btn btn-warning\"><span class=\"fa fa-edit\" title=\"Editar\"></span></button>");
                html.append("<button class=\"btn btn-danger\"><span class=\"fa fa-recycle\" title=\"Desacticar\"></span></button>");
                html.append("</td>");
                html.append("</tr>");
            }
            
            JSON.addProperty("unidadHTML", html.toString());
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.TRUE);
        }
        return JSON.toString();
    }
}
