/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesodatos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Persona;

/**
 *
 * @author felip
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "indicadores02-ejbPU")
    private EntityManager em;
    private int id=0;
    private Persona personam=null;

    public Persona getPersonam() {
        return personam;
    }

    public void setPersonam(Persona personam) {
        this.personam = personam;
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
    public Persona persona_usu_pas_login(String usuario, String password) {
        Persona persona = null;
        System.out.println(usuario);
        System.out.println(password);
        try {
            Query consultaup = em.createNamedQuery("Persona.findByUsuarioPasswordLogin");
            consultaup.setParameter("usuario", usuario);
            consultaup.setParameter("password", password);
            persona = (Persona) consultaup.getSingleResult();
            setPersonam(persona);
            persona.setIdPersona(persona.getIdPersona());
            
            System.out.println("El id de la persona es no no: "+persona.getIdPersona());
            setId(persona.getIdPersona());
            
        } catch (Exception e) {return null;}
        return persona;
    }
     
    public Persona persona_usu_pas(String usuario) {
        Persona persona = null;
        try {
            Query consultaup = em.createNamedQuery("Persona.findByUsuarioPassword");
            consultaup.setParameter("usuario", usuario);
            persona = (Persona) consultaup.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return persona;
    }

    public Persona persona_usuario(String u) {
        Persona persona = null;
        try {
            Query consultaup = em.createNamedQuery("Persona.findByUsuario");
            consultaup.setParameter("usuario",u);
            persona = (Persona) consultaup.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return persona;

    }

    public PersonaFacade() {
        super(Persona.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 

}
