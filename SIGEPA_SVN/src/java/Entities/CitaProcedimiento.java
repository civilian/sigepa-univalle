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
@Table(name = "cita_procedimiento")
@NamedQueries({
    @NamedQuery(name = "CitaProcedimiento.findAll", query = "SELECT c FROM CitaProcedimiento c"),
    @NamedQuery(name = "CitaProcedimiento.findByCodigo", query = "SELECT c FROM CitaProcedimiento c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CitaProcedimiento.findByObservaciones", query = "SELECT c FROM CitaProcedimiento c WHERE c.observaciones = :observaciones")})
public class CitaProcedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "codproced", referencedColumnName = "codigo")
    @ManyToOne
    private Procedimiento procedimiento;
    @JoinColumn(name = "codcita", referencedColumnName = "codigo")
    @ManyToOne
    private Cita cita;

    public CitaProcedimiento() {
    }

    public CitaProcedimiento(Integer codigo) {
        this.codigo = codigo;
    }

    public CitaProcedimiento(Integer codigo, String observaciones) {
        this.codigo = codigo;
        this.observaciones = observaciones;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
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
        if (!(object instanceof CitaProcedimiento)) {
            return false;
        }
        CitaProcedimiento other = (CitaProcedimiento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CitaProcedimiento[codigo=" + codigo + "]";
    }

}
