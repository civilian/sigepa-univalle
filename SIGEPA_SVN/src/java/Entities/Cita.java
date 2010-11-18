/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cita")
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findByCodigo", query = "SELECT c FROM Cita c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cita.findByFechaHora", query = "SELECT c FROM Cita c WHERE c.fechaHora = :fechaHora"),
    @NamedQuery(name = "Cita.findByEstado", query = "SELECT c FROM Cita c WHERE c.estado = :estado")})
public class Cita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.DATE)
    private Date fechaHora;
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "codpaciente", referencedColumnName = "codpaciente")
    @ManyToOne
    private Paciente paciente;
    @JoinColumn(name = "cododontologo", referencedColumnName = "cododontologo")
    @ManyToOne
    private Odontologo odontologo;
    @OneToMany(mappedBy = "cita")
    private List<CitaAsignadaPor> citaAsignadaPorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cita")
    private List<Factura> facturaList;
    @OneToMany(mappedBy = "cita")
    private List<HistoriaClinica> historiaClinicaList;
    @OneToMany(mappedBy = "cita")
    private List<CitaProcedimiento> citaProcedimientoList;

    public Cita() {
    }

    public Cita(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public List<CitaAsignadaPor> getCitaAsignadaPorList() {
        return citaAsignadaPorList;
    }

    public void setCitaAsignadaPorList(List<CitaAsignadaPor> citaAsignadaPorList) {
        this.citaAsignadaPorList = citaAsignadaPorList;
    }

    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    public List<HistoriaClinica> getHistoriaClinicaList() {
        return historiaClinicaList;
    }

    public void setHistoriaClinicaList(List<HistoriaClinica> historiaClinicaList) {
        this.historiaClinicaList = historiaClinicaList;
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
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Cita[codigo=" + codigo + "]";
    }

}
