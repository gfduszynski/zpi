/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Giorgio
 */
@Entity
@Table(name = "clubs")
@NamedQueries({
    @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c")})
public class Club implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "club_id")
    private Integer clubId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Size(max = 1000)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "userClub")
    private Collection<User> userCollection;

    public Club() {
    }

    public Club(Integer clubId) {
        this.clubId = clubId;
    }

    public Club(Integer clubId, String name, Date creationDate) {
        this.clubId = clubId;
        this.name = name;
        this.creationDate = creationDate;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ( clubId != null ? clubId.hashCode() : 0 );
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!( object instanceof Club )) {
            return false;
        }
        Club other = (Club) object;
        if (( this.clubId == null && other.clubId != null ) || ( this.clubId != null && !this.clubId.equals(other.clubId) )) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.Club[ clubId=" + clubId + " ]";
    }
    
}
