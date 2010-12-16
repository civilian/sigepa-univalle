package Controllers;

import Entities.Factura;
import Controllers.util.JsfUtil;
import Controllers.util.PaginationHelper;
import Entities.Auxiliar;
import Entities.Cita;
import Entities.CitaProcedimiento;
import Entities.Odontologo;
import Entities.Paciente;
import Facades.FacturaFacade;
import java.util.Date;

import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean (name="facturaController")
@SessionScoped
public class FacturaController {

    private Factura current;
    private DataModel items = null;
     private DataModel itemsProc = null;
    @EJB private Facades.FacturaFacade ejbFacade;
    @EJB private Facades.CitaFacade facade_cita;
    @EJB private Facades.CitaProcedimientoFacade facade_citaProcedimiento;

    private Cita entity_cita=new Cita();
    private Odontologo entity_odontologo=new Odontologo();
    private Paciente entity_paciente=new Paciente();
    


    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String idCita="";
    private Date fechaGeneracion=new Date();
    private String estadoFactura="";
    private double total=0.0;

    /*public String getIdCita() {
        return idCita;
    }
*/
    

    public FacturaController() {

    
    }

    public Factura getSelected() {
        if (current == null) {
            current = new Factura();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FacturaFacade getFacade() {
        return ejbFacade;
    }

    public Cita getEntity_cita() {
        return entity_cita;
    }

    public Paciente getEntity_paciente() {
        return entity_paciente;
    }

    public double getTotal() {
        return total;
    }

    

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public String getEstadoFactura() {

        if(current.getEstado()=='c')
        {
            estadoFactura="Cancelada";
        }
        else if(current.getEstado()=='s')
        {
             estadoFactura="Sin cancelar";
        }

        return estadoFactura;
    }

    

    public Odontologo getEntity_odontologo() {
        return entity_odontologo;
    }

    public DataModel getItemsProc() {
        return itemsProc;
    }


    

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()}));
                }
            };
        }
        return pagination;
    }

  

    

    public void crearFactura()
    {
            current=new Factura();
             current.setEstado('s');
            
             
             current.setTotal(Float.valueOf(Double.valueOf(total).floatValue()));
             current.setCita(entity_cita);
            getFacade().create(current);

            
    }


    public void cargarFactura()
    {
        current=getFacade().findFacturaByCita(entity_cita);                
    }

    public String generarFactura()
    {
         FacesContext context = FacesContext.getCurrentInstance();
         String value []= context.getExternalContext().getRequestParameterValuesMap().get("miIdCita");
         idCita=value[0];
         String requestFactura[]=context.getExternalContext().getRequestParameterValuesMap().get("facturaCreada");
         boolean facturaCreada=Boolean.parseBoolean(requestFactura[0]);

          entity_cita=facade_cita.find(Integer.parseInt(idCita));
          entity_odontologo=entity_cita.getOdontologo();
         entity_paciente=entity_cita.getPaciente();
         itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(entity_cita));
         total=0.0;
         for(int i=0; i<itemsProc.getRowCount() ; i++)
           {
               itemsProc.setRowIndex(i);
               if(itemsProc.isRowAvailable())
               {
                 total+=(((CitaProcedimiento)getItemsProc().getRowData()).getProcedimiento()).getCosto();
               }
           }

         if(facturaCreada)
         {
            cargarFactura();
         }
         else
         {
            crearFactura();            
         }

         return "../factura/View.xhtml";
         
    }

  

    public String prepareEdit() {
        //current = (Factura)getItems().getRowData();

         entity_cita=facade_cita.find(Integer.parseInt(idCita));
         entity_odontologo=entity_cita.getOdontologo();
         entity_paciente=entity_cita.getPaciente();
         itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(entity_cita));
         total=0.0;
         for(int i=0; i<itemsProc.getRowCount() ; i++)
           {
               itemsProc.setRowIndex(i);
               if(itemsProc.isRowAvailable())
               {
                 total+=(((CitaProcedimiento)getItemsProc().getRowData()).getProcedimiento()).getCosto();
               }
           }

         current.setTotal(Float.valueOf(Double.valueOf(total).floatValue()));

        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacturaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

  



   

    

    

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass=Factura.class)
    public static class FacturaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FacturaController controller = (FacturaController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "facturaController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Factura) {
                Factura o = (Factura) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+FacturaController.class.getName());
            }
        }

    }

}
