package Controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class indexController {

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object usuario = null;
        if (session != null) {
            usuario = (String) session.getAttribute("usuario");
        }
        if (usuario != null) {
            return "index";
        } else {
            return "redirect:sesion.htm";
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public String handleIOException() {
        return "error404";

    }
}
