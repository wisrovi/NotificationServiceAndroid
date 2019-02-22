/*
 * Copyright (c) 2019. Created by William Steve Rodriguez Villamizar.
 * Esta representación está protegida por WISROVI. Queda prohibida la reproducción  y distribución del còdigo sin permiso del autor.
 * https://www.linkedin.com/in/wisrovi-rodriguez/
 * https://github.com/wisrovi/
 * wisrovi.rodriguez@gmail.com
 * https://es.stackoverflow.com/users/85923/william-angel
 *
 *
 *
 */

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Dto.Other;
import android.content.Context;
import android.provider.Settings.Secure;

public class LoginDto {
    String usuario;
    String password;
    String numeroTelefono;
    String rolUsuario;
    String idDispositivo;

    public LoginDto(Context context) {
        this.rolUsuario = DiccionarioRolLogin.SinAcceso;
        this.idDispositivo = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
}
