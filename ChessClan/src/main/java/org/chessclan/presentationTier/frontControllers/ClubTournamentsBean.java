/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.dataTier.models.Tournament;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="ctBean")
@ViewScoped
public class ClubTournamentsBean implements Serializable {
    
    @ManagedProperty("#TournamentBO")
    private TournamentBO tmBO;
    
    List<Tournament> clubTournaments;

    public ClubTournamentsBean() {
    }
    
    @PostConstruct
    public void initialize(){
        //this.clubTournaments = tmBO.findTournamentByClub(id);
    }

    public TournamentBO getTmBO() {
        return tmBO;
    }

    public void setTmBO(TournamentBO tmBO) {
        this.tmBO = tmBO;
    }
    
    
}
