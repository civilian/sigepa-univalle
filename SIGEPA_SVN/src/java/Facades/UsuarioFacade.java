/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Facades;

import Entities.Usuario;
import java.math.BigDecimal;
import java.util.List;
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


}
