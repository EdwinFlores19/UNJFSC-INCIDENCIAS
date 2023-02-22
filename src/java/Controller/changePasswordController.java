package Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class changePasswordController {

    @RequestMapping(value = "changePassword.htm", method = RequestMethod.GET)
    public String cambiarPassword(HttpServletRequest request, Model modelo) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        String url = "";
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            url = "changePassword";
            modelo.addAttribute("usuarioOSI", new MODELO.usuario());
        } else {
            url = "redirect:sesion.htm";
        }
        return url;
    }

    @RequestMapping(value = "changePassword.htm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String changePass(HttpServletRequest request, @RequestParam("currentPass") String currentPass, @RequestParam("newPass") String newPass) throws Exception {
        HttpSession session = request.getSession(false);
        Object usuario = null;

        com.google.gson.JsonObject JSON = new com.google.gson.JsonObject();
        if (session != null) {
            usuario = session.getAttribute("usuario");
        }
        if (usuario != null) {
            java.sql.ResultSet resultado;
            DAO.usuarioDAO objUsuarioDAO = new DAO.usuarioDAO();
            resultado = objUsuarioDAO.cambiarPassword(currentPass, newPass, Integer.parseInt(String.valueOf(session.getAttribute("id"))));
            try {
                if (resultado.next()) {
                    if (resultado.getInt("ESTADO") == 0) {
                        JSON.addProperty("ESTADO", Boolean.FALSE);
                    } else {
                        JSON.addProperty("ESTADO", Boolean.TRUE);
                        session.invalidate();

                    }
                }
            } catch (NullPointerException e) {
                JSON.addProperty("ESTADO", Boolean.TRUE);
            }
        } else {
            JSON.addProperty("SESIONFINALIZADA", Boolean.FALSE);
        }
        return JSON.toString();
    }
}
