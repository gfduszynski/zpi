/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "tournaments")
public class Tournament implements Serializable {
    public enum State {
        NOT_STARTED, STARTED, FINISHED;
    }
    public class Exists extends Exception {}
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    private State state;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "points_for_bye")
    private float pointsForBye;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Size(min = 1, max = 2048)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "current_round", referencedColumnName = "id")
    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade={CascadeType.ALL, CascadeType.REMOVE})
    private Round currentRound;
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category category;
    @JoinColumn(name = "club", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Club club;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number_of_rounds")
    private int numberOfRounds = 7;
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, mappedBy = "tournament", fetch = FetchType.LAZY)
    private Set<Round> roundSet = new HashSet<Round>();
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "tournament", fetch = FetchType.LAZY)
    private Set<PairingCard> pairingCardSet = new HashSet<PairingCard>();

    public Tournament() {
    }

    public Tournament(Integer id) {
        this.id = id;
    }

    public Tournament(String name, Date date, String description, Club club, Category category, State state, int pointForBye, int numberOfRounds) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.club = club;
        this.category = category;
        this.state = state;
        this.pointsForBye = pointForBye;
        this.numberOfRounds = numberOfRounds;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Round> getRoundSet() {
        return roundSet;
    }

    public void setRoundSet(Set<Round> roundSet) {
        this.roundSet = roundSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.Tournament[ id=" + id + " ]";
    }

    public Set<PairingCard> getPairingCardSet() {
        return pairingCardSet;
    }

    public void setPairingCardSet(Set<PairingCard> pairingCardSet) {
        this.pairingCardSet = pairingCardSet;
    }

    public float getPointsForBye() {
        return pointsForBye;
    }

    public void setPointsForBye(float pointsForBye) {
        this.pointsForBye = pointsForBye;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }
    
}
