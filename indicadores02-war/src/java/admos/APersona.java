/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package admos;

import accesodatos.PersonaFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import manipuladatos.MDPersona;
import modelo.Persona;

/**
 *
 * @author felip
 */
@Named(value = "aPersona")
@SessionScoped
public class APersona implements Serializable {

    @EJB
    private MDPersona mDPersona;
    private Persona perosna;
    private List<Persona> personas;
    //@ManagedProperty("#{buttonView}")  
    private boolean validacionRegistro;
    FacesMessage message;
    private int idP;

    public void autenticar() {
    }

    public List<Persona> getPersonas() {
        return mDPersona.personas();
    }

    /**
     * Creates a new instance of APersona
     */
    public APersona() {
        perosna = new Persona();

    }

    public MDPersona getmDPersona() {
        return mDPersona;
    }

    public void setmDPersona(MDPersona mDPersona) {
        this.mDPersona = mDPersona;
    }

    public Persona getPerosna() {
        return perosna;
    }

    public void setPerosna(Persona perosna) {
        this.perosna = perosna;
    }

    public void registro() {
        calcularEdadDO(perosna);
        System.out.println(registrado());
        System.out.println(validacionRegistro);
        if (registrado() == false) {

            personas = getPersonas();
            if (validacionRegistro == true) {
                mDPersona.registrarPersona(perosna);
                perosna = new Persona();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El usuario registrado correctamente.",
                        null);
                FacesContext.getCurrentInstance().addMessage(null, message);

                FacesContext.getCurrentInstance().validationFailed();
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El usuario ya se encuentra registrado.",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, message);

            FacesContext.getCurrentInstance().validationFailed();

        }

    }

    public void eliminarPersona(Persona p) {
        System.out.println(p.toString());
        message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Eliminacion correcta del usuario",
                null);
        FacesContext.getCurrentInstance().addMessage(null, message);

        FacesContext.getCurrentInstance().validationFailed();
        mDPersona.eliminarP(p);
    }

    public boolean registrado() {
        personas = getPersonas();
        boolean esta = false;
        for (Persona p : personas) {
            esta = perosna.getUsuario().equals(p.getUsuario());
            // esta = perosna.getUsuario().equals(p.getUsuario()) && perosna.getPassword().equals(p.getPassword());

            if (esta) {
                return esta;
            }
        }
        System.out.println(esta);
        return esta;
    }

    public void registroA() {
        if (!registrado()) {
            mDPersona.registrarPersona(perosna);
        }
    }

    public void calcularEdadDO(Persona pe) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = convertToLocalDate(pe.getFechaNac());
        if (fechaNacimiento.isAfter(fechaActual)) {

            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "La fecha de nacimiento no debe ser mayor a la fecha actual.",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, message);

            FacesContext.getCurrentInstance().validationFailed();
            validacionRegistro = false;
        } else {

            Period periodo = Period.between(fechaNacimiento, fechaActual);
            pe.setEdad(Double.parseDouble(String.valueOf(periodo.getYears())));
            validacionRegistro = true;

        }
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public boolean registrado2() {
        return mDPersona.personaUp(perosna) != null;
    }

    public void creaPersona() {
        perosna = new Persona();
    }

    public Boolean registradoLogin() {
        return mDPersona.personaUpLogin(perosna) == null;
    }

    public void registroPersona() {
        if (!registrado2()) {
            calcularEdadDO(perosna);
            if (validacionRegistro == true) {
                mDPersona.registrarPersona(perosna);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El usuario ha sido registrado",
                        null);
                FacesContext.getCurrentInstance().addMessage(null, message);

                FacesContext.getCurrentInstance().validationFailed();
                System.out.println("Usuario registrado jjeje");
                creaPersona();
            }
        } else {
            System.out.println("El usuario ya existe");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El usuario ya existe",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext.getCurrentInstance().validationFailed();

        }
    }

    public void autenticacion() {

        System.out.println(perosna.getUsuario());
        System.out.println(perosna.getPassword());
        System.out.println(registradoLogin());
        if (!registradoLogin()) {
            AMedida medidas = new AMedida();

            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Bienvenido",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext.getCurrentInstance().validationFailed();
            //
            perosna = mDPersona.mandarPersonaId();
            setPerosna(perosna);
            // setIdP(mDPersona.obtenerId());
            //medidas.setNumero(mDPersona.mandarPersonaId().getIdPersona());

            //guardar al empleado
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("empleadoLogueado", perosna);
            System.out.println("Empleado guardado en la sesión: " + perosna.getIdPersona());


            //System.out.println("ID DE LA PERSONA DESDE Persona: " + perosna.getIdPersona());
            //System.out.println("Usuario registrado jojojojo");
            creaPersona();

            //navegacion
            NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "home?faces-redirect=true");

        } else {
            System.out.println("Usuario o contraseña incorrectos");
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Usuario o contraseña incorrectos",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, message);

            FacesContext.getCurrentInstance().validationFailed();

        }
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }
    
    

}
