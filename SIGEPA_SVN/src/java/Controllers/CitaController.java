package Controllers;

import Entities.Cita;
import Controllers.util.JsfUtil;
import Controllers.util.PaginationHelper;
import Entities.Auxiliar;
import Entities.CitaAsignadaPor;
import Entities.CitaProcedimiento;
import Facades.CitaFacade;
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
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private CitaAsignadaPor entity_cita_asignada_por=new CitaAsignadaPor();
    private CitaProcedimiento entity_citaProcedimiento=new CitaProcedimiento();
    //private String observaciones;

    ///talvez se pueda borrar
    //private Auxiliar auxiliar;

    public CitaController() {


    }

    public CitaProcedimiento getEntity_citaProcedimiento() {
        return entity_citaProcedimiento;
    }

    public void setEntity_citaProcedimiento(CitaProcedimiento entity_citaProcedimiento) {
        this.entity_citaProcedimiento = entity_citaProcedimiento;
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
/*
    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliar auxiliar) {
        this.auxiliar = auxiliar;
    }*/

   /* public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }*/

    

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
        entity_cita_asignada_por=facade_citaAsignadaPor.findByCita(current);

        itemsProc=new ListDataModel(facade_citaProcedimiento.findProcByCita(current));


        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cita();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {

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
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CitaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cita)getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
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
