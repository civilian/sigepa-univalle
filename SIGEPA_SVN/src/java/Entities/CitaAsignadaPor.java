/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author damstev
 */
@Entity
@Table(name = "cita_asignada_por")
@NamedQueries({
    @NamedQuery(name = "CitaAsignadaPor.findAll", query = "SELECT c FROM CitaAsignadaPor c"),
    @NamedQuery(name = "CitaAsignadaPor.findByCodigo", query = "SELECT c FROM CitaAsignadaPor c WHERE c.codigo = :codigo")})
    //@NamedQuery(name = "CitaAsignadaPor.findByCodigo", query = "SELECT c FROM CitaAsignadaPor c WHERE c.codigo = :codigo")})

public class CitaAsignadaPor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @JoinColumn(name = "codcita", referencedColumnName = "codigo")
    @ManyToOne
    private Cita cita;
    @JoinColumn(name = "codauxiliar", referencedColumnName = "codauxiliar")
    @ManyToOne
    private Auxiliar auxiliar;

    public CitaAsignadaPor() {
    }

    public CitaAsignadaPor(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliar auxiliar) {
        this.auxiliar = auxiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitaAsignadaPor)) {
            return false;
        }
        CitaAsignadaPor other = (CitaAsignadaPor) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CitaAsignadaPor[codigo=" + codigo + "]";
    }

}
