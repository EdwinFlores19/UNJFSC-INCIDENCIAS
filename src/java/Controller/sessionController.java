package Controller;

import DAO.usuarioDAO;
import MODELO.usuario;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class sessionController {

    @RequestMapping(value = "sesion.htm", method = RequestMethod.GET)
    public String inicioSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            return "redirect:index.htm";
        }else{
            return "sesion";
        }
    }

    @RequestMapping(value = "sesion.htm", method = RequestMethod.POST)
    public String inicioSesion(@ModelAttribute usuario usuario, HttpServletRequest request, Model modelo) throws Exception {
        ResultSet resultado;
        HttpSession sesion;
        resultado = new usuarioDAO().validadarUsuario(usuario.getUsuario(), usuario.getPassword());
        if (resultado.next()) {
            sesion = request.getSession(true);
            sesion.setAttribute("id", resultado.getInt("ID"));
            sesion.setAttribute("usuario", resultado.getString("PERSONA"));
            sesion.setAttribute("rol", resultado.getInt("ROL"));
            sesion.setMaxInactiveInterval(1800);
            return "redirect:index.htm";
        } else {
            modelo.addAttribute("incorrecto", new String("incorreto"));
            return "redirect:sesion.htm";
        }
    }

    @RequestMapping(value = "salir.htm", method = RequestMethod.GET)
    public String salir(HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);
        if (sesion != null) {
            sesion.invalidate();
        }
        return "redirect:sesion.htm";
    }
}
