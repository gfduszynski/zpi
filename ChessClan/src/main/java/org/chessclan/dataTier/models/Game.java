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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Xcays
 */
@Entity
@Table(name = "game")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "game_id")
    private Integer gameId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "first_player")
    private String firstPlayer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "second_player")
    private String secondPlayer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "played_with_black")
    private String playedWithBlack;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "winner")
    private String winner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
    private Collection<Round> roundCollection;

    public Game() {
    }

    public Game(Integer gameId) {
        this.gameId = gameId;
    }

    public Game(Integer gameId, String firstPlayer, String secondPlayer, String playedWithBlack, String winner) {
        this.gameId = gameId;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.playedWithBlack = playedWithBlack;
        this.winner = winner;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public String getPlayedWithBlack() {
        return playedWithBlack;
    }

    public void setPlayedWithBlack(String playedWithBlack) {
        this.playedWithBlack = playedWithBlack;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Collection<Round> getRoundCollection() {
        return roundCollection;
    }

    public void setRoundCollection(Collection<Round> roundCollection) {
        this.roundCollection = roundCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.businessTier.businessObjects.Game[ gameId=" + gameId + " ]";
    }
    
}
