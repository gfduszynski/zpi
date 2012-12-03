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
@ManagedBean(name = "ltmtsBean")
@ViewScoped
public class LatestTournamentsBean implements Serializable {

    private List<Tournament> latestTmts;
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmtBO;

    public LatestTournamentsBean() {
    }

    @PostConstruct
    public void initialize() {
        this.latestTmts = tmtBO.findTournamentsWithClubAndRoundsAndPC();
    }

    public List<Tournament> getLatestTmts() {
        return latestTmts;
    }

    public void setLatestTmts(List<Tournament> latestTmts) {
        this.latestTmts = latestTmts;
    }

    public TournamentBO getTmtBO() {
        return tmtBO;
    }

    public void setTmtBO(TournamentBO tmtBO) {
        this.tmtBO = tmtBO;
    }
}
