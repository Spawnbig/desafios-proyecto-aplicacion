package ufro.dci.intranet.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String nombre;
    private String correo;
    private String contrasenna;
    private Rol rol;
}
