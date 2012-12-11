/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Round.NoPlayers;
import org.chessclan.dataTier.models.Round.NotFinished;
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
    private boolean notValidCriteria;
    private String search;
    private Tournament currentTmt;
    private List<Round> roundList = null;
    
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;
    private List<User> foundUsers;
    private Map<User, Float> results;
    
    public ClubTournamentCreatorBean() {
        this.nowInMods = false;
    }

    public void loadTmt(Tournament t) {

        this.currentTmt = tmBO.fetchRelations(t);
        nowInMods = true;
        if(currentTmt.getState() == Tournament.State.FINISHED)
        {
            this.roundList = tmBO.getRoundList(currentTmt);;
            this.results = tmBO.getResults(currentTmt);
        }
    }
    
    public int getNumberOfPlayers(){
        return tmBO.filterUniquePairingCards(currentTmt.getCurrentRound()).size();
    }
    
    public void winner(PairingCard pc){
        switch((int)pc.getScore()){
            case 1:
                pc.setScore(currentTmt.getPointsForBye());
                pc.getOpponent().setScore(0);
                break;
            case 0:
                pc.setScore(currentTmt.getPointsForBye()/2);
                pc.getOpponent().setScore(currentTmt.getPointsForBye()/2);
                break;
            case -1:
                pc.setScore(0);
                pc.getOpponent().setScore(currentTmt.getPointsForBye());
                break;
        }
        tmBO.savePairingCard(pc);
        tmBO.savePairingCard(pc.getOpponent());
    }
        
    public void addPlayer(User user) throws NotJoinableRound {
        tmBO.joinTournament(currentTmt, user);
    }

    public void removeUser(PairingCard pc) throws NotJoinableRound {
        tmBO.leaveTournament(currentTmt, pc);
    }

    public void findUsers() {
        if (search == null || search.isEmpty()) {
            this.notValidCriteria = true;
            return;
        } else {
            this.foundUsers = umBO.findByFirstNameContainingOrLastNameContaining(search, search);
            Iterator<User> it = foundUsers.iterator();
            while (it.hasNext()) {
                User u = it.next();
                boolean isUser = false;
                for (Role r : u.getRoleSet()) {
                    if ("USER".equals(r.getRoleName())) {
                        isUser = true;
                    }
                }
                if (!isUser) {
                    foundUsers.remove(u);
                }
            }
        }
        if (foundUsers.size() > 0) {
            this.notValidCriteria = false;
        } else {
            this.notValidCriteria = true;
        }
    }

    public boolean isMember(User u) {
        for (PairingCard pc : currentTmt.getPairingCardSet()) {
            if (pc.getPlayer().getId() == u.getId()) {
                return true;
            }
        }
        return false;
    }

    public void goToNextRound() {
        if(this.currentTmt.getState() == Tournament.State.STARTED){
            for(PairingCard pc:getFilteredCurrentRoundPC()){
                if(pc.getOpponent()!=null){
                    winner(pc);
                }else{
                    pc.setScore(currentTmt.getPointsForBye());
                }
            }
        }
        try {
            this.currentTmt = tmBO.goToNextRound(currentTmt);
            this.currentTmt = tmBO.fetchRelations(currentTmt);
        } catch (NotFinished ex) {
            Logger.getLogger(ClubTournamentCreatorBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoPlayers ex) {
            Logger.getLogger(ClubTournamentCreatorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(currentTmt.getState() == Tournament.State.FINISHED)
        {
            this.results = tmBO.getResults(currentTmt);
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public boolean isNotValidCriteria() {
        return notValidCriteria;
    }

    public void setNotValidCriteria(boolean notValidCriteria) {
        this.notValidCriteria = notValidCriteria;
    }

    public Map<User, Float> getResults() {
        return results;
    }

    public void setResults(Map<User, Float> results) {
        this.results = results;
    }

    public List<Round> getRoundList() {
        return roundList;
    }

    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }
   
    public Set<PairingCard> getFilteredCurrentRoundPC(){
        return this.tmBO.filterUniquePairingCards(this.currentTmt.getCurrentRound());
    }
    public Set<PairingCard> getFilteredRoundPC(Round r){
        return this.tmBO.filterUniquePairingCards(r);
    }
}
