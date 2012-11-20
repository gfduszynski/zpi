/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Round.NotJoinableRound;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "cTmtCBean")
@ViewScoped
public class ClubTournamentCreatorBean implements Serializable {

    private boolean nowInMods;
    private String searchFN;
    private String searchLN;
    private Tournament currentTmt;
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;
    private List<User> foundUsers;

    public ClubTournamentCreatorBean() {
        this.nowInMods = false;
    }

    public void loadTmt(Tournament t) {

        this.currentTmt = tmBO.fetchRelations(t);

        nowInMods = true;
    }

    public void addPlayer(PairingCard pc) throws NotJoinableRound {
        tmBO.joinTournament(currentTmt, pc.getPlayer());
    }

    public void removeUser(PairingCard pc) throws NotJoinableRound {
        tmBO.leaveTournament(currentTmt, pc);
    }

    public void findUsers() {
        if (searchFN == null || searchFN.isEmpty()) {
            if (searchLN == null || searchLN.isEmpty()) {
            } else {
                this.foundUsers = umBO.findByFirstname(searchFN);
            }
        } else {
            if (searchLN == null || searchLN.isEmpty()) {
                this.foundUsers = umBO.findByLastname(searchFN);
            } else {
                this.foundUsers = umBO.findByFirstnameAndLastname(searchFN, searchLN);
            }
        }
    }

    public boolean isNowInMods() {
        return nowInMods;
    }

    public void setNowInMods(boolean nowInMods) {
        this.nowInMods = nowInMods;
    }

    public Tournament getCurrentTmt() {
        return currentTmt;
    }

    public void setCurrentTmt(Tournament currentTmt) {
        this.currentTmt = currentTmt;
    }

    public TournamentBO getTmBO() {
        return tmBO;
    }

    public void setTmBO(TournamentBO tmBO) {
        this.tmBO = tmBO;
    }

    public String getSearchFN() {
        return searchFN;
    }

    public void setSearchFN(String searchFN) {
        this.searchFN = searchFN;
    }

    public String getSearchLN() {
        return searchLN;
    }

    public void setSearchLN(String searchLN) {
        this.searchLN = searchLN;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public List<User> getFoundUsers() {
        return foundUsers;
    }

    public void setFoundUsers(List<User> foundUsers) {
        this.foundUsers = foundUsers;
    }
}