package ufro.dci.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufro.dci.models.LoginDTO;
import ufro.dci.models.RegisterDTO;
import ufro.dci.models.Rol;
import ufro.dci.models.Usuario;
import ufro.dci.repositories.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioServices {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrarUsuario(RegisterDTO registerDTO) throws Exception {
        // Verificar integridad de los datos
        verificarDatos(registerDTO);
        // Verificar correo electronico unico
        verificarCorreoUnico(registerDTO.getCorreo());
        // Crear objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registerDTO.getNombre());
        usuario.setCorreo(registerDTO.getCorreo());
        usuario.setContrasenna(registerDTO.getContrasenna());
        usuario.setRol(registerDTO.getRol());
        usuario.setBloqueado(false);
        usuario.setIntentos(0);
        usuarioRepository.save(usuario);
    }

    private void verificarCorreoUnico(String correo) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (usuario.isPresent()) throw new Exception("Correo ya se encuentra registrado");
    }

    private void verificarDatos(RegisterDTO registerDTO) throws Exception {
        if (registerDTO.getNombre() == null) throw new Exception("Nombre no ingresado");
        if (registerDTO.getCorreo() == null) throw new Exception("Correo no ingresado");
        if (registerDTO.getContrasenna() == null) throw new Exception("Contraseña no ingresada");
        if (registerDTO.getRol() == null) throw new Exception("Rol no ingresado");
    }

    public Rol login(LoginDTO loginDTO) throws Exception {
        // Verificar si el usuario existe
        Usuario usuario = usuarioRepository.findByCorreo(loginDTO.getCorreo()).orElseThrow(() -> new Exception("Usuario no existe"));
        // Verificar si usuario se encuentra bloqueado
        if (usuario.isBloqueado()) throw new Exception("Usuario Bloqueado");
        // Verificar contraseña correcta
        if(!usuario.getContrasenna().equals(loginDTO.getContrasenna())){
            usuario.setIntentos(usuario.getIntentos()+1);
            if (usuario.getIntentos() > 3) {
                usuario.setBloqueado(true);
                throw new Exception("Exceso de intentos");
            }
            throw new Exception("Contraseña Incorrecta");
        }
        usuario.setIntentos(0);
        return usuario.getRol();
    }
}
