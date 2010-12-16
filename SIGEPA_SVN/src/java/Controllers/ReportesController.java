/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Facades.AuxiliarFacade;
import Reportes.Reporte;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author damstev
 */
@ManagedBean
@RequestScoped
public class ReportesController {

    private List datos;
    private ArrayList<Reporte> reportesitos;
    private String mes="12/2010";
    @EJB
    private Facades.UsuarioFacade facade;


    /** Creates a new instance of ReportesController */
    public ReportesController() {
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
    public ArrayList<Reporte> getReportesitos() {
        
        return reportesitos;
    }

    public void setReportesitos(ArrayList<Reporte> reportesitos) {
        this.reportesitos = reportesitos;
    }

    public String cantidadPacientesXOdontologo(){
        datos = facade.findPacientesOdontologo(mes);

        Vector x = (Vector) datos;
        reportesitos = new ArrayList<Reporte>();

        for (Iterator it = x.iterator(); it.hasNext();) {

            Object[] reporte = (Object[]) it.next();
            String nombre = (String) reporte[0];
            int cantidad = ((Long) reporte[1]).intValue();

            Reporte r = new Reporte(nombre, cantidad);
            reportesitos.add(r);
        }

        return "List";
    }

    public String cantidadPacientesXAuxiliar(){
        datos = facade.findPacientesAuxiliar(mes);

        Vector x = (Vector) datos;
        reportesitos = new ArrayList<Reporte>();

        for (Iterator it = x.iterator(); it.hasNext();) {

            Object[] reporte = (Object[]) it.next();
            String nombre = (String) reporte[0];
            int cantidad = ((Long) reporte[1]).intValue();

            Reporte r = new Reporte(nombre, cantidad);
            reportesitos.add(r);
        }

        return "List";
    }

    public String cantidadPacientesXProcedimiento(){
        datos = facade.findPacientesProcedimiento(mes);

        Vector x = (Vector) datos;
        reportesitos = new ArrayList<Reporte>();

        for (Iterator it = x.iterator(); it.hasNext();) {

            Object[] reporte = (Object[]) it.next();
            String nombre = (String) reporte[0];
            int cantidad = ((Long) reporte[1]).intValue();

            Reporte r = new Reporte(nombre, cantidad);
            reportesitos.add(r);
        }

        return "List";
    }
}
