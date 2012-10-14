/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Xcays
 */
@Entity
@Table(name = "round")
@NamedQueries({
    @NamedQuery(name = "Round.findAll", query = "SELECT r FROM Round r")})
public class Round implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "round_id")
    private Integer roundId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roundId")
    private Collection<Category> categoryCollection;
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @ManyToOne(optional = false)
    private Game gameId;

    public Round() {
    }

    public Round(Integer roundId) {
        this.roundId = roundId;
    }

    public Round(Integer roundId, int number) {
        this.roundId = roundId;
        this.number = number;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Collection<Category> getCategoryCollection() {
        return categoryCollection;
    }

    public void setCategoryCollection(Collection<Category> categoryCollection) {
        this.categoryCollection = categoryCollection;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roundId != null ? roundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Round)) {
            return false;
        }
        Round other = (Round) object;
        if ((this.roundId == null && other.roundId != null) || (this.roundId != null && !this.roundId.equals(other.roundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.businessTier.businessObjects.Round[ roundId=" + roundId + " ]";
    }
    
}
