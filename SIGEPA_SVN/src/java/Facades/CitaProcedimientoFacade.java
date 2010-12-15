/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Entities.Cita;
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
public class CitaProcedimientoFacade extends AbstractFacade<CitaProcedimiento> {
    @PersistenceContext(unitName = "SIGEPA_SVNPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CitaProcedimientoFacade() {
        super(CitaProcedimiento.class);
    }

     public List<CitaProcedimiento> findProcByCita(Cita cita)
    {
               try {
              List<CitaProcedimiento> lista= (List<CitaProcedimiento>)em.createQuery("SELECT c FROM CitaProcedimiento c WHERE c.cita = :idCita")
                      .setParameter("idCita", cita)
                      .getResultList();

              return lista;

        } catch (NoResultException e) {
            return null;
        }

    }

}
