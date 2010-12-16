/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Entities.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author damstev
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    private List  pacientesMes;
    @PersistenceContext(unitName = "SIGEPA_SVNPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario findByLoginPasswd(String login, String passwd) {
        try {
              Usuario usuario= (Usuario)em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.contrasena= :contrasena")
                      .setParameter("login", login).setParameter("contrasena", passwd)
                      .getSingleResult();

              return usuario;

        } catch (NoResultException e) {
            return null;
        }
    }

    public List findPacientesOdontologo(String mes) {
        try {
              String fecha=mesAFecha(mes);
              String luego=fechaMasUnMes(mes);
              pacientesMes= (List)em.createNativeQuery("select B.nombre nombre,  count(codpaciente) cantidad"
                                                      + " from (cita AS A JOIN usuario AS B"
                                                      + " ON A.cododontologo = B.codigo)"

                                                      + " WHERE (A.fecha_hora BETWEEN '"
                                                      + fecha
                                                      + "' AND '"+  luego+ "' )"
                                                      + " group by B.nombre")
                      //.setParameter("idCita", idCita)
                                                      .getResultList();

              return (List)pacientesMes;

        } catch (NoResultException e) {
            return null;
        }
    }

    public List findPacientesAuxiliar(String mes) {
        try {
              String fecha=mesAFecha(mes);
              String luego=fechaMasUnMes(mes);
              //pacientesMes=new ArrayList();
              pacientesMes= (List)em.createNativeQuery("select B.nombre nombre,  count(DISTINCT(codpaciente)) cantidad"
                                                      + " from( cita AS A"
                                                      + " JOIN cita_asignada_por AS C"
                                                      + " ON A.codigo = C.codcita"
                                                      + " JOIN usuario AS B"
                                                      + " ON C.codauxiliar = B.codigo)"

                                                      + " WHERE (A.fecha_hora BETWEEN '"
                                                      + fecha
                                                      + "' AND '"+  luego+ "' )"
                                                      + " group by B.nombre")
                      //.setParameter("idCita", idCita)
                                                      .getResultList();

              return (List)pacientesMes;

        } catch (NoResultException e) {
            return null;
        }
    }

    public List findPacientesProcedimiento(String mes) {
        try {
              //pacientesMes=new ArrayList();
              String fecha=mesAFecha(mes);
              String luego=fechaMasUnMes(mes);
              pacientesMes= (List)em.createNativeQuery("select B.nombre nombre,  count(DISTINCT(codpaciente)) cantidad"
                                                      + " from (cita AS A"
                                                      + " JOIN cita_procedimiento AS C"
                                                      + " ON A.codigo = C.codcita"
                                                      + " JOIN procedimiento AS B"
                                                      + " ON C.codproced = B.codigo)"
                                                      
                                                      + " WHERE (A.fecha_hora BETWEEN '"
                                                      + fecha
                                                      + "' AND '"+  luego+ "' )"
                                                      + " group by B.nombre")
                      //.setParameter("f", fecha)
                                                      .getResultList();

              return (List)pacientesMes;

        } catch (NoResultException e) {
            return null;
        }
    }

    private String mesAFecha(String m)
    {
        //Date fecha;
        StringTokenizer a=new StringTokenizer(m, "/");
        int mes=Integer.parseInt(a.nextToken());
        int anno=Integer.parseInt(a.nextToken());
        return anno+"-"+mes+"-01"+ " 00:00:00";
    }

    private String fechaMasUnMes(String m)
    {
        //Date fecha;
        StringTokenizer a=new StringTokenizer(m, "/");
        int mes=Integer.parseInt(a.nextToken());
        String smes=mes+"";
        int anno=Integer.parseInt(a.nextToken());
        mes++;
        if(mes==13)
        {
            anno++;
            mes=1;
        }
        //if(mes==0)
        //    smes="01";
        return anno+"-"+mes+"-01"+ " 00:00:00";
    }
    

}
