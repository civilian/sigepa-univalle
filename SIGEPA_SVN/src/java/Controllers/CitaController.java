package Controllers;

import Entities.Cita;
import Controllers.util.JsfUtil;
import Controllers.util.PaginationHelper;
import Entities.Auxiliar;
import Entities.CitaAsignadaPor;
import Entities.CitaProcedimiento;
import Facades.CitaFacade;
import java.util.Date;
import java.util.List;

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

@ManagedBean (name="citaController")
@SessionScoped
public class CitaController {

    private Cita current;
    private DataModel items = null;
    private DataModel itemsProc = null;
    @EJB private Facades.CitaFacade ejbFacade;
    @EJB private Facades.CitaAsignadaPorFacade facade_citaAsignadaPor;
    @EJB private Facades.CitaProcedimientoFacade facade_citaProcedimiento;
    @EJB private Facades.FacturaFacade facade_factura;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private CitaAsignadaPor entity_cita_asignada_por=new CitaAsignadaPor();
    private CitaProcedimiento entity_citaProcedimiento=new CitaProcedimiento();

    private String estadoCita="";
    private String facturaCita="";
    private boolean facturaCreada=false;


    public CitaController() {


    }

    public CitaProcedimiento getEntity_citaProcedimiento() {
        return entity_citaProcedimiento;
    }

    public void setEntity_citaProcedimiento(CitaProcedimiento entity_citaProcedimiento) {
        this.entity_citaProcedimiento = entity_citaProcedimiento;
    }

    public String getFacturaCita()
    {


    facturaCreada=facade_factura.findByCita(current);

    if(facturaCreada)
    {
        facturaCita="Ver Factura";
    }
    else
    {
         facturaCita="Generar Factura";
    }

        return facturaCita;
    }

    public boolean isFacturaCreada() 
    {
        facturaCreada=facade_factura.findByCita(current);
        return facturaCreada;
    }   

    public CitaAsignadaPor getEntity_cita_asignada_por() {
        return entity_cita_asignada_por;
    }

    public void setEntity_cita_asignada_por(CitaAsignadaPor entity_cita_asignada_por) {
        this.entity_cita_asignada_por = entity_cita_asignada_por;
    }

    public DataModel getItemsProc() {
        return itemsProc;
    }

    public void setItemsProc(DataModel itemsProc) {
        this.itemsProc = itemsProc;
    }



    public String getEstadoCita() {



        if((current.getEstado()).equals('c'))
        {
            estadoCita="Cancelada";
        }
        else if(current.getEstado().equals('r'))
        {
            estadoCita="Realizada";
        }
        else if(current.getEstado().equals('p'))
        {
            estadoCita="Programada";
        }

        return estadoCita;
    }



    public Cita getSelected() {
        if (current == null) {
            current = new Cita();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CitaFacade getFacade() {
        return ejbFacade;
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Cita)getItems().getRowData();
        getEstadoCita();
        getFacturaCita();
        entity_cita_asignada_por=facade_citaAsignadaPor.findByCita(current);

        itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(current));


        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cita();
        entity_cita_asignada_por=new CitaAsignadaPor();
        entity_citaProcedimiento=new CitaProcedimiento();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {

            Date fechaActual=new Date();

            if(!fechaActual.before(current.getFechaHora()))
            {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitaPasada"));
                return null;
            }

            getFacade().create(current);
                      
            //entity_cita_asignada_por.setAuxiliar(auxiliar);
            entity_cita_asignada_por.setCita(current);
            facade_citaAsignadaPor.create(entity_cita_asignada_por);

            //entity_citaProcedimiento.setObservaciones(observaciones);
            //entity_citaProcedimiento.set
            entity_citaProcedimiento.setCita(current);
            facade_citaProcedimiento.create(entity_citaProcedimiento);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cita)getItems().getRowData();
        getFacturaCita();
        entity_cita_asignada_por=facade_citaAsignadaPor.findByCita(current);
        itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(current));
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {

             Date fechaActual=new Date();

            if(!fechaActual.before(current.getFechaHora()))
            {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitaPasada"));
                return null;
            }
            getFacade().edit(current);
            int index=0;
           for(int i=0; i<itemsProc.getRowCount() ; i++)
           {
               itemsProc.setRowIndex(i);
               if(itemsProc.isRowAvailable())
               {
                 facade_citaProcedimiento.edit((CitaProcedimiento)getItemsProc().getRowData());
               }
           }

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String updateProc()
    {        
         try {
            facade_citaProcedimiento.edit((CitaProcedimiento)getItemsProc().getRowData());
            }
         catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));            
        }
         return "View";
    }

    public String destroyProc()
    {
        try {
            entity_citaProcedimiento=(CitaProcedimiento)getItemsProc().getRowData();
             facade_citaProcedimiento.remove(entity_citaProcedimiento);
             }
         catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return "List";
    }


    public String destroy() {

        current = (Cita)getItems().getRowData();

         entity_cita_asignada_por=facade_citaAsignadaPor.findByCita(current);
         itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(current));
         facade_citaAsignadaPor.remove(entity_cita_asignada_por);

         facade_factura.remove(facade_factura.findFacturaByCita(current));

         for(int i=0; i<itemsProc.getRowCount() ; i++)
           {
               itemsProc.setRowIndex(i);
               if(itemsProc.isRowAvailable())
               {
                 facade_citaProcedimiento.remove((CitaProcedimiento)getItemsProc().getRowData());
               }
           }
        
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView()
    {

         entity_cita_asignada_por=facade_citaAsignadaPor.findByCita(current);
         itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(current));
         facade_citaAsignadaPor.remove(entity_cita_asignada_por);
         facade_factura.remove(facade_factura.findFacturaByCita(current));

         for(int i=0; i<itemsProc.getRowCount() ; i++)
           {
               itemsProc.setRowIndex(i);
               if(itemsProc.isRowAvailable())
               {
                 facade_citaProcedimiento.remove((CitaProcedimiento)getItemsProc().getRowData());
               }
           }

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count-1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex+1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
        itemsProc=null;
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

    @FacesConverter(forClass=Cita.class)
    public static class CitaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CitaController controller = (CitaController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "citaController");
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
            if (object instanceof Cita) {
                Cita o = (Cita) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "+CitaController.class.getName());
            }
        }




    }

}
