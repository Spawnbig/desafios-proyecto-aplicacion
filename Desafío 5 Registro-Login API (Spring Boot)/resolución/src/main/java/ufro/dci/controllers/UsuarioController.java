package ufro.dci.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufro.dci.models.LoginDTO;
import ufro.dci.models.RegisterDTO;
import ufro.dci.models.Rol;
import ufro.dci.services.UsuarioServices;

@RestController
@RequestMapping("/api/usuario/")
public class UsuarioController {

    @Autowired
    private UsuarioServices usuarioServices;

    @PostMapping("registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegisterDTO registerDTO){
        try{
            usuarioServices.registrarUsuario(registerDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Registrado correctamente");
    }

    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        try {
            Rol rol = usuarioServices.login(loginDTO);
            return ResponseEntity.accepted().body(rol);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
