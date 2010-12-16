package Reportes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.List;


/**
 *
 * @author damstev
 */
public class Reportes implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reporte> reporteList;

    public Reportes() {
    }

    public List<Reporte> getReporteList() {
        return reporteList;
    }

    public void setReporteList(List<Reporte> reporteList) {
        this.reporteList = reporteList;
    }
    
}
