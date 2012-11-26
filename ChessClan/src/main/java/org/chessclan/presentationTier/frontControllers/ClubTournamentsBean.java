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
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "ctBean")
@ViewScoped
public class ClubTournamentsBean implements Serializable {

    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    List<Tournament> clubTournaments;

    public ClubTournamentsBean() {
    }

    @PostConstruct
    public void initialize() {
        this.clubTournaments = tmBO.findTournamentsByClub(user.getOwnedClub());
    }

    public void removeTmt(Tournament t) {
        this.clubTournaments.remove(t);
        tmBO.deleteTournament(t);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tournament> getClubTournaments() {
        return clubTournaments;
    }

    public void setClubTournaments(List<Tournament> clubTournaments) {
        this.clubTournaments = clubTournaments;
    }

    public TournamentBO getTmBO() {
        return tmBO;
    }

    public void setTmBO(TournamentBO tmBO) {
        this.tmBO = tmBO;
    }
}
