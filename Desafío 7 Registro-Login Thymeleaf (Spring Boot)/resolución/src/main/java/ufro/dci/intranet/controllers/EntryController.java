package ufro.dci.intranet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ufro.dci.intranet.models.LoginDTO;
import ufro.dci.intranet.models.RegisterDTO;
import ufro.dci.intranet.models.Rol;
import ufro.dci.intranet.services.UsuarioServices;


@Controller
public class EntryController {
    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping("*")
    public String initialPage(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginView(Model model){
        model.addAttribute("loginDTO",new LoginDTO());
        return "login";
    }

    @GetMapping("/registrar")
    public String registrarView(Model model){
        model.addAttribute("registerDTO",new RegisterDTO());
        return "registrar";
    }

    @PostMapping("/api/login")
    public String login(LoginDTO loginDTO, Model model){
        try {
            Rol rol = usuarioServices.login(loginDTO);
            if (rol.equals(Rol.ADMIN)) return "admin";
            if (rol.equals(Rol.ALUMNO)) return "alumno";
            if (rol.equals(Rol.PERSONAL)) return "personal";
            return "profesor";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/login";
        }
    }

    @PostMapping("/api/registrar")
    public String registrarUsuario(RegisterDTO registerDTO,Model model){
        try {
            usuarioServices.registrarUsuario(registerDTO);
        } catch (Exception e) {
            return "registrar";
        }
        return "redirect:/login";
    }

}

