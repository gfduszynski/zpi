/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "rounds")
public class Round implements Serializable {
    public static class NotFinished extends Exception{
        public NotFinished(String string) {
            super(string);
        }
    }
    public static class NoPlayers extends Exception{
        public NoPlayers(String string) {
            super(string);
        }
    }
    public static class NotJoinableRound extends Exception{}
    public enum State{ JOINING,NOT_STARTED,STARTED,FINISHED;}
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @NotNull
    @Column(name = "round_state")
    @Enumerated(EnumType.ORDINAL)
    private State roundState;
    @Column(name = "round_start")
    @Temporal(TemporalType.DATE)
    private Date roundStart;
    @Column(name = "round_end")
    @Temporal(TemporalType.DATE)
    private Date roundEnd;
    @JoinColumn(name = "prev_round", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Round prevRound;
    @JoinColumn(name = "next_round", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Round nextRound;
    @JoinColumn(name = "tournament", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Tournament tournament;
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.ALL}, mappedBy = "round", fetch = FetchType.LAZY)
    private Set<PairingCard> pairingCardSet;

    public Round() {
    }

    public Round(Integer id) {
        this.id = id;
    }

    public Round(Integer id, int number, State roundState) {
        this.id = id;
        this.number = number;
        this.roundState = roundState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public State getRoundState() {
        return roundState;
    }

    public void setRoundState(State roundState) {
        this.roundState = roundState;
    }

    public Date getRoundStart() {
        return roundStart;
    }

    public void setRoundStart(Date roundStart) {
        this.roundStart = roundStart;
    }

    public Date getRoundEnd() {
        return roundEnd;
    }

    public void setRoundEnd(Date roundEnd) {
        this.roundEnd = roundEnd;
    }

    public Round getPrevRound() {
        return prevRound;
    }

    public void setPrevRound(Round prevRound) {
        this.prevRound = prevRound;
    }
    
    public Round getNextRound() {
        return nextRound;
    }

    public void setNextRound(Round nextRound) {
        this.nextRound = nextRound;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
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
        if (!(object instanceof Round)) {
            return false;
        }
        Round other = (Round) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.Round[ id=" + id + " ]";
    }

    public Set<PairingCard> getPairingCardSet() {
        return pairingCardSet;
    }

    public void setPairingCardSet(Set<PairingCard> pairingCardSet) {
        this.pairingCardSet = pairingCardSet;
    }

}
