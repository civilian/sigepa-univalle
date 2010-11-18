/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author damstev
 */
@Entity
@Table(name = "procedimiento")
@NamedQueries({
    @NamedQuery(name = "Procedimiento.findAll", query = "SELECT p FROM Procedimiento p"),
    @NamedQuery(name = "Procedimiento.findByCodigo", query = "SELECT p FROM Procedimiento p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Procedimiento.findByNombre", query = "SELECT p FROM Procedimiento p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Procedimiento.findByDescripcion", query = "SELECT p FROM Procedimiento p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Procedimiento.findByTDuracion", query = "SELECT p FROM Procedimiento p WHERE p.tDuracion = :tDuracion"),
    @NamedQuery(name = "Procedimiento.findByCosto", query = "SELECT p FROM Procedimiento p WHERE p.costo = :costo")})
public class Procedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "t_duracion")
    @Temporal(TemporalType.TIME)
    private Date tDuracion;
    @Basic(optional = false)
    @Column(name = "costo")
    private float costo;
    @OneToMany(mappedBy = "procedimiento")
    private List<CitaProcedimiento> citaProcedimientoList;

    public Procedimiento() {
    }

    public Procedimiento(Integer codigo) {
        this.codigo = codigo;
    }

    public Procedimiento(Integer codigo, String nombre, String descripcion, Date tDuracion, float costo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tDuracion = tDuracion;
        this.costo = costo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getTDuracion() {
        return tDuracion;
    }

    public void setTDuracion(Date tDuracion) {
        this.tDuracion = tDuracion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public List<CitaProcedimiento> getCitaProcedimientoList() {
        return citaProcedimientoList;
    }

    public void setCitaProcedimientoList(List<CitaProcedimiento> citaProcedimientoList) {
        this.citaProcedimientoList = citaProcedimientoList;
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
        if (!(object instanceof Procedimiento)) {
            return false;
        }
        Procedimiento other = (Procedimiento) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigo+" - "+nombre;
    }

}
