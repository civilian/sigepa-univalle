/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Entities.Odontologo;
import Reportes.Reporte;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author damstev
 */
@Stateless
public class OdontologoFacade extends AbstractFacade<Odontologo> {
    @PersistenceContext(unitName = "SIGEPA_SVNPU")
    private EntityManager em;

    private List /*<Reporte>*/ pacientesMes;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OdontologoFacade() {
        super(Odontologo.class);
    }

    public List findPacientesOdontologo() {
        try {
              pacientesMes= (List)em.createNativeQuery("select B.nombre nombre,  count(codpaciente) cantidad"
                                                      + " from cita AS A JOIN usuario AS B"
                                                      + " ON A.cododontologo = B.codigo"
                                                      + " group by B.nombre")
                      //.setParameter("idCita", idCita)
                                                      .getResultList();
              
              return (List)pacientesMes;

        } catch (NoResultException e) {
            return null;
        }
    }

}
