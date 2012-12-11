/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.dataTier.models.PairingCard;
import org.chessclan.dataTier.models.Round;
import org.chessclan.dataTier.models.Round.NotJoinableRound;
import org.chessclan.dataTier.models.Tournament;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "atvBean")
@ViewScoped
public class TournamentsViewBean implements Serializable {

    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    private List<Tournament> allTournaments;
    private List<Integer> mapTournaments;
    private Tournament selectedTournament;
    private Integer page;
    private Map<Integer, ArrayList<Integer>> mapToPrevAndNext;
    private List<Tournament> userTournaments;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    private Tournament joinedTmt;
    private boolean showMineOnly;
    private List<Date> tmtDates;
    private List<Round> roundList = null;
    private Map<User, Float> results;

    public TournamentsViewBean() {
    }

    @PostConstruct
    public void initialize() {
        this.mapToPrevAndNext = new HashMap<Integer, ArrayList<Integer>>();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String tournamentId = params.get("id");
        page = 1;
        try {
            int ti = Integer.valueOf(tournamentId);
            this.selectedTournament = tmBO.fetchRelations(tmBO.findTournamentById(ti));
            if (this.selectedTournament == null) {
                loadTournaments();
            }

        }
        catch (NumberFormatException e) {
            loadTournaments();
        }
        helpMapping();
        loadMapping();
    }

    private void loadTournaments() {
        this.tmtDates = new ArrayList<Date>();
        this.allTournaments = tmBO.findTournamentsWithClubAndRoundsAndPC();
        this.userTournaments = new ArrayList<Tournament>();
        if (user != null) {
            for (Tournament t : allTournaments) {
                for (PairingCard pc : t.getPairingCardSet()) {
                    if (pc.getPlayer().getId() == user.getId()) {
                        userTournaments.add(t);
                        break;
                    }
                }
            }
        }

        //Sort posts by date
        Collections.sort(this.allTournaments);
        for (Tournament t : this.allTournaments) {
            if (!tmtDates.contains(t.getDate())) {
                tmtDates.add(t.getDate());
            }
        }
    }

    private void helpMapping() {
        this.mapTournaments = new ArrayList<Integer>();
        Iterator<Tournament> tournaments = tmBO.findAll().iterator();
        Integer index = 0;
        while (tournaments.hasNext()) {
            Tournament tmp = tournaments.next();
            tmp.getClub();
            mapTournaments.add(tmp.getId());
            ++index;
        }
    }

    private void loadMapping() {
        for (int i = 0; i <= mapTournaments.size() - 1; i++) {
            ArrayList<Integer> altmp = new ArrayList<Integer>();
            if (i == 0) {
                altmp.add(-1);
            } else {
                altmp.add(mapTournaments.get(i - 1));
            }
            if (i == mapTournaments.size() - 1) {
                altmp.add(-1);
            } else {
                {
                    altmp.add(mapTournaments.get(i + 1));
                }
            }
            this.mapToPrevAndNext.put(mapTournaments.get(i), altmp);
        }
    }
    
    public void selectTmt(Tournament t){
        this.roundList = tmBO.getRoundList(t);
        this.results = tmBO.getResults(t);
        this.selectedTournament = t;
    }

    public boolean isJoinable(Tournament tmt) {
        if (tmt.getState() == Tournament.State.NOT_STARTED) {
            return true;
        }
        return false;
    }

    public boolean isStarted(Tournament tmt) {
        if (tmt.getState() == Tournament.State.STARTED) {
            return true;
        }
        return false;
    }

    public boolean isFinished(Tournament tmt) {
        if (tmt.getState() == Tournament.State.FINISHED) {
            return true;
        }
        return false;
    }

    public void joinTournament(Tournament tmt) throws Round.NotJoinableRound {
        tmBO.joinTournament(tmt);
        this.joinedTmt = tmt;
        loadTournaments();
    }

    public void leaveTournament(Tournament tmt) throws NotJoinableRound {
        PairingCard pc = null;
        for (PairingCard it : tmt.getPairingCardSet()) {
            if (it.getPlayer().getId() == user.getId()) {
                pc = it;
            }
        }
        tmBO.leaveTournament(tmt, pc);
        loadTournaments();
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

    public Map<Integer, ArrayList<Integer>> getMapToPrevAndNext() {
        return this.mapToPrevAndNext;
    }

    public void setMapToPrevAndNext(Map<Integer, ArrayList<Integer>> tmpmap) {
        this.mapToPrevAndNext = tmpmap;
    }

    public Integer getPrevFromId(Integer id) {
        if (mapToPrevAndNext.get(id) != null) {
            return mapToPrevAndNext.get(id).get(0);
        }
        return -1;
    }

    public Integer incrPage() {
        int tmp = page;
        return ++tmp;
    }

    public Integer decrPage() {
        int tmp = page;
        return --tmp;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNextFromId(Integer id) {
        if (mapToPrevAndNext.get(id) != null) {
            return mapToPrevAndNext.get(id).get(1);
        }
        return -1;
    }

    public Integer getCurrentTnmIndex(Integer id) {
        return mapTournaments.indexOf(id);
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

    public Tournament getJoinedTmt() {
        return joinedTmt;
    }

    public void setJoinedTmt(Tournament joinedTmt) {
        this.joinedTmt = joinedTmt;
    }

    public boolean isShowMineOnly() {
        return showMineOnly;
    }

    public void setShowMineOnly(boolean showMineOnly) {
        this.showMineOnly = showMineOnly;
    }

    public List<Date> getTmtDates() {
        return tmtDates;
    }

    public void setTmtDates(List<Date> tmtDates) {
        this.tmtDates = tmtDates;
    }

    public List<Round> getRoundList() {
        return roundList;
    }

    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }

    public Map<User, Float> getResults() {
        return results;
    }

    public void setResults(Map<User, Float> results) {
        this.results = results;
    }
    
    
}