/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Round.State;
import org.chessclan.dataTier.models.Tournament;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "atvBean")
@ViewScoped
public class TournamentsViewBean {

    @Transient
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    private List<Tournament> allTournaments;
    private Tournament selectedTournament;
    private boolean joiningSucc;

    public TournamentsViewBean() {
    }

    @PostConstruct
    public void initialize() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String tournamentId = params.get("id");
        try {
            int ti = Integer.valueOf(tournamentId);
            this.selectedTournament = tmBO.findTournamentById(ti);
            if(this.selectedTournament == null){
                loadTournaments();
            }

        } catch (NumberFormatException e) {
            loadTournaments();
        }
    }

    private void loadTournaments() {
        this.allTournaments = new ArrayList<Tournament>();
        Iterator<Tournament> tournaments = tmBO.findAll().iterator();
        while (tournaments.hasNext()) {
            allTournaments.add(tournaments.next());
        }
    }
    
    public boolean isJoinable(Tournament tmt){
        if(tmt.getCurrentRound().getRoundState() == State.JOINING){
            return true;
        }
        return false;
    }
    
    public void joinTournament(Tournament tmt) throws Round.NotJoinableRound{
        tmBO.joinTournament(tmt);
    }

    public TournamentBO getTmBO() {
        return tmBO;
    }

    public void setTmBO(TournamentBO tmBO) {
        this.tmBO = tmBO;
    }

    public List<Tournament> getAllTournaments() {
        return allTournaments;
    }

    public void setAllTournaments(List<Tournament> allTournaments) {
        this.allTournaments = allTournaments;
    }

    public Tournament getSelectedTournament() {
        return selectedTournament;
    }

    public void setSelectedTournament(Tournament selectedTournament) {
        this.selectedTournament = selectedTournament;
    }

    public boolean isJoiningSucc() {
        return joiningSucc;
    }

    public void setJoiningSucc(boolean joiningSucc) {
        this.joiningSucc = joiningSucc;
    }
    
    
}
