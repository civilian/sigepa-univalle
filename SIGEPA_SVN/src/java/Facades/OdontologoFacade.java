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


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OdontologoFacade() {
        super(Odontologo.class);
    }

}
