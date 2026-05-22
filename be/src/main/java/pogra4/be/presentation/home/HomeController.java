package pogra4.be.presentation.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pogra4.be.logic.Service;

@Controller
public class HomeController {

    @Autowired
    private Service service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("puestos", service.getUltimosPuestosPublicos());
        return "index";
    }
}