/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.Tournament;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="utBean")
@ViewScoped
public class UserTournaments {

    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO umBO;
    
    private List<Tournament> tournaments;
    
    public UserTournaments() {
        //this.tournaments = umBO.getUserTournaments();
    }
    
    @PostConstruct
    public void initialize(){
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
    
    
}
