/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Entities.Cita;
import Entities.Factura;
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
public class FacturaFacade extends AbstractFacade<Factura> {
    @PersistenceContext(unitName = "SIGEPA_SVNPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }

       public boolean findByCita(Cita idCita)
       {
        try {
              List<Factura> lista= (List<Factura>)em.createQuery("SELECT c FROM Factura c WHERE c.cita = :idCita")
                      .setParameter("idCita", idCita)
                      .getResultList();

               if(lista.size()==0)
               {
                return false;
               }
              else return true;

        } catch (NoResultException e) {
            return false;
        }
    }


        public Factura findFacturaByCita(Cita idCita)
       {
        try {
              List<Factura> lista= (List<Factura>)em.createQuery("SELECT c FROM Factura c WHERE c.cita = :idCita")
                      .setParameter("idCita", idCita)
                      .getResultList();

               return lista.get(0);

        } catch (NoResultException e) {
            return null;
        }
    }

}
