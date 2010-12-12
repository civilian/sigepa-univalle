/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Entities.CitaAsignadaPor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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

}