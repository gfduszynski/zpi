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
import javax.persistence.ManyToMany;
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
@Table(name = "users")
public class User implements Serializable {

    public enum FIDETitle {

        NO_TITLE, WCM, WFM, CM, WIM, FM, WGM, IM, GM;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "login")
    private String login;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled")
    private boolean enabled;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sex")
    private int sex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private float rating = 0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fide_title")
    @Enumerated(EnumType.ORDINAL)
    private FIDETitle fideTitle = FIDETitle.NO_TITLE;
    @ManyToMany(mappedBy = "userSet", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Role> roleSet;
    @JoinColumn(name = "user_club", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Club userClub;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Post> postSet;
    @OneToOne(mappedBy = "owner", fetch = FetchType.EAGER)
    private Club ownedClub;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "opponent", fetch = FetchType.LAZY)
    private Set<PairingCard> opponentPairingCardSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player", fetch = FetchType.LAZY)
    private Set<PairingCard> pairingCardSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Game> games;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(String login, String email, String password, String firstName, String lastName, Date birthDate, Date creationDate) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.creationDate = creationDate;
    }

    public User(Integer id, String login, String email, boolean enabled, String password, String firstName, String lastName, Date birthDate, Date creationDate, int sex) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.creationDate = creationDate;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Club getUserClub() {
        return userClub;
    }

    public void setUserClub(Club userClub) {
        this.userClub = userClub;
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.chessclan.dataTier.models.User[ id=" + id + " ]";
    }

    public Club getOwnedClub() {
        return ownedClub;
    }

    public void setOwnedClub(Club ownedClub) {
        this.ownedClub = ownedClub;
    }

    public Set<PairingCard> getOpponentPairingCardSet() {
        return opponentPairingCardSet;
    }

    public void setOpponentPairingCardSet(Set<PairingCard> pairingCardSet) {
        this.opponentPairingCardSet = pairingCardSet;
    }

    public Set<PairingCard> getPairingCardSet() {
        return pairingCardSet;
    }

    public void setPairingCardSet(Set<PairingCard> pairingCardSet) {
        this.pairingCardSet = pairingCardSet;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public FIDETitle getFideTitle() {
        return fideTitle;
    }

    public void setFideTitle(FIDETitle fideTitle) {
        this.fideTitle = fideTitle;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
    
    
}
