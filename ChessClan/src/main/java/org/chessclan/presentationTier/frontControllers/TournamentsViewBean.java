/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Round.State;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "atvBean")
@ViewScoped
public class TournamentsViewBean implements Serializable {

    @Transient
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    private List<Tournament> allTournaments;
    private Tournament selectedTournament;
    private boolean joiningSucc;
    private List<Tournament> userTournaments;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;

    public TournamentsViewBean() {
    }

    @PostConstruct
    public void initialize() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String tournamentId = params.get("id");
        try {
            int ti = Integer.valueOf(tournamentId);
            this.selectedTournament = tmBO.findTournamentById(ti);
            if (this.selectedTournament == null) {
                loadTournaments();
            }

        } catch (NumberFormatException e) {
            loadTournaments();
        }

        userTournaments = tmBO.findUserTournaments(user);
    }

    private void loadTournaments() {
        this.allTournaments = tmBO.findTournamentsWithClubAndRoundsAndPC();
    }

    public boolean isJoinable(Tournament tmt) {
        if (tmt.getCurrentRound().getRoundState() == State.JOINING) {
            return true;
        }
        return false;
    }

    public void joinTournament(Tournament tmt) throws Round.NotJoinableRound {
        tmBO.joinTournament(tmt);
    }

    public boolean userIsMember(Tournament t) {
        boolean userInTmt = false;
        for (PairingCard pc : t.getPairingCardSet()) {
            if (pc.getPlayer().getId() == user.getId()) {
                userInTmt = true;
            }
        }
        return userInTmt;
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

    public List<Tournament> getUserTournaments() {
        return userTournaments;
    }

    public void setUserTournaments(List<Tournament> userTournaments) {
        this.userTournaments = userTournaments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
