/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import Entities.Cita;
import Entities.CitaAsignadaPor;
import Entities.CitaProcedimiento;
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
public class CitaAsignadaPorFacade extends AbstractFacade<CitaAsignadaPor> {

    @PersistenceContext(unitName = "SIGEPA_SVNPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CitaAsignadaPorFacade() {
        super(CitaAsignadaPor.class);
    }

    public CitaAsignadaPor findByCita(Cita idCita) {
        try {
              List<CitaAsignadaPor> lista= (List<CitaAsignadaPor>)em.createQuery("SELECT c FROM CitaAsignadaPor c WHERE c.cita = :idCita")
                      .setParameter("idCita", idCita)
                      .getResultList();

              return lista.get(0);

        } catch (NoResultException e) {
            return null;
        }
    }

   

}
