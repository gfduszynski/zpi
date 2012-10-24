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
import javax.persistence.Transient;
import org.chessclan.businessTier.businessObjects.TournamentBO;
import org.chessclan.dataTier.models.Tournament;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="utBean")
@ViewScoped
public class UserTournamentsBean implements Serializable {

    @Transient
    @ManagedProperty("#{TournamentBO}")
    private TournamentBO tmBO;
    private List<Tournament> userTournaments;

    public UserTournamentsBean() {
    }

    @PostConstruct
    public void initialize() {
        //userTournaments = tmBO.
    }
}
