/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author damstev
 */
@Entity
@Table(name = "auxiliar")
@NamedQueries({
    @NamedQuery(name = "Auxiliar.findAll", query = "SELECT a FROM Auxiliar a"),
    @NamedQuery(name = "Auxiliar.findByCodauxiliar", query = "SELECT a FROM Auxiliar a WHERE a.codauxiliar = :codauxiliar")})
public class Auxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codauxiliar")
    private Integer codauxiliar;
    @OneToMany(mappedBy = "auxiliar")
    private List<CitaAsignadaPor> citaAsignadaPorList;
    @JoinColumn(name = "codauxiliar", referencedColumnName = "codigo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;

    public Auxiliar() {
    }

    public Auxiliar(Integer codauxiliar) {
        this.codauxiliar = codauxiliar;
    }

    public Integer getCodauxiliar() {
        return codauxiliar;
    }

    public void setCodauxiliar(Integer codauxiliar) {
        this.codauxiliar = codauxiliar;
    }

    public List<CitaAsignadaPor> getCitaAsignadaPorList() {
        return citaAsignadaPorList;
    }

    public void setCitaAsignadaPorList(List<CitaAsignadaPor> citaAsignadaPorList) {
        this.citaAsignadaPorList = citaAsignadaPorList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codauxiliar != null ? codauxiliar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auxiliar)) {
            return false;
        }
        Auxiliar other = (Auxiliar) object;
        if ((this.codauxiliar == null && other.codauxiliar != null) || (this.codauxiliar != null && !this.codauxiliar.equals(other.codauxiliar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuario.getCedula()+" - "+usuario.getNombre()+" "+usuario.getApellido();
    }

}
