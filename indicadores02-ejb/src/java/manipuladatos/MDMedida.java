/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package manipuladatos;

import accesodatos.MedidaFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import modelo.Medida;

/**
 *
 * @author felip
 */
@Stateless
@LocalBean
public class MDMedida {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private MedidaFacade medidaFarcade;
    
    public void registrarMedida(Medida medida){
        medidaFarcade.create(medida);
    }
      public List<Medida> medidas() {
        return medidaFarcade.findAll();
    }
}
