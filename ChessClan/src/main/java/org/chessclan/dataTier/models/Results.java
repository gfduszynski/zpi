/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Xcays
 */
@Entity
@Table(name = "results")
@NamedQueries({
    @NamedQuery(name = "Results.findAll", query = "SELECT r FROM Results r")})
public class Results implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "results_id")
    private Integer resultsId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "player")
    private String player;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "category")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Column(name = "place")
    private int place;
    @JoinColumn(name = "tournament_id", referencedColumnName = "tournament_id")
    @ManyToOne(optional = false)
    private Tournament tournamentId;

    public Results() {
    }

    public Results(Integer resultsId) {
        this.resultsId = resultsId;
    }

    public Results(Integer resultsId, String player, String category, int place) {
        this.resultsId = resultsId;
        this.player = player;
        this.category = category;
        this.place = place;
    }

    public Integer getResultsId() {
        return resultsId;
    }

    public void setResultsId(Integer resultsId) {
        this.resultsId = resultsId;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Tournament getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Tournament tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultsId != null ? resultsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Results)) {
            return false;
        }
        Results other = (Results) object;
        if ((this.resultsId == null && other.resultsId != null) || (this.resultsId != null && !this.resultsId.equals(other.resultsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.businessTier.businessObjects.Results[ resultsId=" + resultsId + " ]";
    }
    
}
