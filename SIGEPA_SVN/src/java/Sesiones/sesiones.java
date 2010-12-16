/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sesiones;

import Entities.Usuario;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 *
 * @author damstev
 */
@ManagedBean
@SessionScoped
public class sesiones {

    private int codigo;
    private String usuario;
    private String passwd;
    private String rol = "";
    private boolean validado = false;
    @EJB
    private Facades.UsuarioFacade facade_usuario;
    private Usuario user;

    /** Creates a new instance of sesiones */
    public sesiones() {
    }

    public String validar() {

        user=facade_usuario.findByLoginPasswd(usuario, passwd);
        if(user==null){
            cerrarSession();
        }else{
            validado=true;
            if(user.getOdontologo() == null){
                rol="Auxiliar";
            }else{
                rol="Odontologo";
            }
        }        

        return "/index";
    }

    public String cerrarSession()
    {
        codigo=0;
        usuario="";
        passwd="";
        rol="";
        user=null;
        validado=false;

        return "/index";

    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


}
