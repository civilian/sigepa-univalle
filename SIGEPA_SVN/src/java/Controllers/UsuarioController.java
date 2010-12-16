package Controllers;

import Entities.Usuario;
import Controllers.util.JsfUtil;
import Controllers.util.PaginationHelper;
import Entities.Auxiliar;
import Entities.Odontologo;
import Facades.UsuarioFacade;

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

@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController {

    private Usuario current;
    private Odontologo entity_odontologo = new Odontologo();
    private Auxiliar entity_auxiliar = new Auxiliar();
    private DataModel items = null;
    @EJB
    private Facades.UsuarioFacade ejbFacade;
    @EJB
    private Facades.OdontologoFacade facade_odontologo;
    @EJB
    private Facades.AuxiliarFacade facade_auxiliar;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String titulo;
    private String especialidad;
    private String[] rol = {"Auxiliar", "Odontologo"};
    private String sel_rol = "Odontologo";
    private String sexoUsuario="";

    public UsuarioController() {
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public String getSexoUsuario() {

        if(current.getSexo().equals('f'))
        {
            sexoUsuario="Femenino";
        }
        else if(current.getSexo().equals('m'))
        {
            sexoUsuario="Masculino";
        }

        return sexoUsuario;
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        current = (Usuario) getItems().getRowData();
        getSexoUsuario();
        if (current.getOdontologo() != null) {
            titulo = facade_odontologo.find(current.getCodigo()).getTitulo();
            especialidad = facade_odontologo.find(current.getCodigo()).getEspecialidad();
        }

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        resetValores();

        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);

            int id_last = current.getCodigo();
            if (sel_rol.equals("Odontologo")) {
                entity_odontologo.setCododontologo(id_last);
                entity_odontologo.setTitulo(titulo);
                entity_odontologo.setEspecialidad(especialidad);
                entity_odontologo.setUsuario(current);
                facade_odontologo.create(entity_odontologo);
            } else {
                entity_auxiliar.setCodauxiliar(id_last);
                entity_auxiliar.setUsuario(current);
                facade_auxiliar.create(entity_auxiliar);
            }

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        resetValores();

        if (current.getOdontologo() != null) {
            entity_odontologo = facade_odontologo.find(current.getCodigo());
            titulo = entity_odontologo.getTitulo();
            especialidad = entity_odontologo.getEspecialidad();
            sel_rol = "Odontologo";
        }
        if (current.getAuxiliar() != null) {
            entity_auxiliar = facade_auxiliar.find(current.getCodigo());
            sel_rol = "Auxiliar";
        }

        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public void resetValores() {
        entity_odontologo = new Odontologo();
        entity_auxiliar = new Auxiliar();
        titulo = "";
        especialidad = "";
    }

    public String update() {
        try {

            if (entity_auxiliar != null) {
                facade_auxiliar.remove(entity_auxiliar);
            }
            if (entity_odontologo != null) {
                facade_odontologo.remove(entity_odontologo);
            }

            int id_last = current.getCodigo();
            if (sel_rol.equals("Odontologo")) {

                entity_odontologo.setCododontologo(id_last);
                entity_odontologo.setTitulo(titulo);
                entity_odontologo.setEspecialidad(especialidad);
                entity_odontologo.setUsuario(current);
                facade_odontologo.create(entity_odontologo);
                current.setAuxiliar(null);
                current.setOdontologo(entity_odontologo);
            } else {
                entity_auxiliar.setCodauxiliar(id_last);
                entity_auxiliar.setUsuario(current);
                facade_auxiliar.create(entity_auxiliar);
                current.setOdontologo(null);
                current.setAuxiliar(entity_auxiliar);
                titulo = "";
                especialidad = "";
            }
            getFacade().edit(current);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();

        resetValores();

        if (current.getOdontologo() != null) {
            entity_odontologo = facade_odontologo.find(current.getCodigo());
            titulo = entity_odontologo.getTitulo();
            especialidad = entity_odontologo.getEspecialidad();
            sel_rol = "Odontologo";
        }
        else if(current.getAuxiliar() != null) {
            entity_auxiliar = facade_auxiliar.find(current.getCodigo());
            sel_rol = "Auxiliar";
        }
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
            if (current.getOdontologo() != null) {
                facade_odontologo.remove(entity_odontologo);
            }
            if (current.getAuxiliar() != null) {
                facade_auxiliar.remove(entity_auxiliar);
            }
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
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

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsuarioController.class.getName());
            }
        }
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String[] getRol() {
        return rol;
    }

    public void setRol(String[] rol) {
        this.rol = rol;
    }

    public String getSel_rol() {
        return sel_rol;
    }

    public void setSel_rol(String sel_rol) {
        this.sel_rol = sel_rol;
    }
}
