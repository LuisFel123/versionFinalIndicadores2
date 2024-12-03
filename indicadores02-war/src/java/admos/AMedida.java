/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package admos;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import manipuladatos.MDMedida;
import modelo.Medida;
import modelo.Persona;

/**
 *
 * @author felip
 */
@Named(value = "aMedida")
@SessionScoped
public class AMedida implements Serializable {

    /**
     * Creates a new instance of AMedida
     */
    @EJB
    private MDMedida mDMedida;
    @Inject
    private APersona aPersona; // Asegúrate de que aPersona esté correctamente inyectado
    private Medida medida;
    private List<Medida> medidas;
    //quitar esto si no
    private int numero = 0;
    private List<Medida> medidasFiltradas;

    FacesMessage message;

    public int getNumero() {
        medida.setIdMedida(numero);
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public AMedida() {
        medida = new Medida();
    }

    public MDMedida getmDMedida() {
        return mDMedida;
    }

    public void setmDMedida(MDMedida mDMedida) {
        this.mDMedida = mDMedida;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public List<Medida> getMedidas() {
        return mDMedida.medidas();

    }

    public void setMedidas(List<Medida> medidas) {
        this.medidas = medidas;
    }

    public List<Medida> filtrarMedidasPorId() {
        int i = 0;
        medidasFiltradas = new ArrayList<>();
        for (Medida medida : getMedidas()) {
            //if (medida.getIdPersona().getIdPersona().equals(aPersona.getPerosna().getIdPersona())) {
            if (medida.getIdPersona().getIdPersona().equals(aPersona.getPerosna().getIdPersona())) {
                medidasFiltradas.add(medida);
                System.out.println("mostrando id medida: " + medida.getIdPersona());
                i += 1;
                System.out.println("Primera vez entrando " + i);
            } else {
                System.out.println("La medida no pertenece al id actual");
            }

        }
        if (!medidasFiltradas.isEmpty()) {
            Medida ultimaMedida = medidasFiltradas.get(medidasFiltradas.size() - 1);
            System.out.println("Última medida filtrada: " + ultimaMedida);
            setMedida(ultimaMedida);
        } else {
            System.out.println("No se encontraron medidas para este id.");
        }
        //limpiarMedida();
        return medidasFiltradas;
    }

    public void registroMedida(Persona idPersona) {
        //medidas = filtrarMedidasPorId();
        try {
            medida.setIdPersona(idPersona);
            mDMedida.registrarMedida(medida);
            medida = new Medida();

            System.out.println("Medida registrada correctamente");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Medida Registrada correctamente",
                    null);
            FacesContext.getCurrentInstance().addMessage(null, message);
            FacesContext.getCurrentInstance().validationFailed();

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    public void limpiar() {
        medidasFiltradas = new ArrayList<>();
        //navegacion
        //NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        //navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "index?faces-redirect=true");
    }

    public void calcularIMC() {
        Double IMC = 0.0;
        Double ICC = 0.0;
        Double estatura = 0.0;
        Double peso = 0.0;
        Double cintura = 0.0;
        Double cadera = 0.0;
        filtrarMedidasPorId();
        try {

            peso = getMedida().getPeso();
            cintura = getMedida().getCintura();
            cadera = getMedida().getCadera();
            estatura = aPersona.getPerosna().getEstatura();
            IMC = peso / (estatura * estatura);
            ICC = cintura / cadera;
        } catch (Exception ex) {
            System.err.println(ex);

        }

        System.out.println("PESO " + peso);
        System.out.println("CINTURA " + cintura);
        System.out.println("CADERA " + cadera);
        System.out.println("ESTATURA: " + estatura);

        System.out.println("Calculo de IMC " + IMC);
        System.out.println("Calculo de ICC " + ICC);

        FacesContext facesContextIMC = FacesContext.getCurrentInstance();
        HttpSession sessionIMC = (HttpSession) facesContextIMC.getExternalContext().getSession(true);
        sessionIMC.setAttribute("IMC", IMC);
        
        
        FacesContext facesContextICC = FacesContext.getCurrentInstance();
        HttpSession sessionICC = (HttpSession) facesContextICC.getExternalContext().getSession(true);
        sessionICC.setAttribute("ICC", ICC);

        NavigationHandler navigationHandler = FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(FacesContext.getCurrentInstance(), null, "caluloIMC?faces-redirect=true");
    }

    public void limpiarMedida() {
        medida = new Medida();
        aPersona.getPerosna().setEstatura(0.0);
    }

}
