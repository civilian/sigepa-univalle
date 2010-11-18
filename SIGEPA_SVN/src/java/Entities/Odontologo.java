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
@Table(name = "odontologo")
@NamedQueries({
    @NamedQuery(name = "Odontologo.findAll", query = "SELECT o FROM Odontologo o"),
    @NamedQuery(name = "Odontologo.findByCododontologo", query = "SELECT o FROM Odontologo o WHERE o.cododontologo = :cododontologo"),
    @NamedQuery(name = "Odontologo.findByTitulo", query = "SELECT o FROM Odontologo o WHERE o.titulo = :titulo"),
    @NamedQuery(name = "Odontologo.findByEspecialidad", query = "SELECT o FROM Odontologo o WHERE o.especialidad = :especialidad")})
public class Odontologo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cododontologo")
    private Integer cododontologo;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "especialidad")
    private String especialidad;
    @JoinColumn(name = "cododontologo", referencedColumnName = "codigo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario;
    @OneToMany(mappedBy = "odontologo")
    private List<Cita> citaList;

    public Odontologo() {
    }

    public Odontologo(Integer cododontologo) {
        this.cododontologo = cododontologo;
    }

    public Odontologo(Integer cododontologo, String titulo, String especialidad) {
        this.cododontologo = cododontologo;
        this.titulo = titulo;
        this.especialidad = especialidad;
    }

    public Integer getCododontologo() {
        return cododontologo;
    }

    public void setCododontologo(Integer cododontologo) {
        this.cododontologo = cododontologo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cododontologo != null ? cododontologo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Odontologo)) {
            return false;
        }
        Odontologo other = (Odontologo) object;
        if ((this.cododontologo == null && other.cododontologo != null) || (this.cododontologo != null && !this.cododontologo.equals(other.cododontologo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Odontologo[cododontologo=" + cododontologo + "]";
    }

}
