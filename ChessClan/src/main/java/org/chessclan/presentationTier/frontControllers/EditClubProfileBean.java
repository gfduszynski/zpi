/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.chessclan.businessTier.businessObjects.ClubBO;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "ecpBean")
@ViewScoped
public class EditClubProfileBean implements Serializable {

    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO umBO;
    @ManagedProperty(value = "#{loginBean.user}")
    private User user;
    @ManagedProperty("#{ClubBO}")
    ClubBO clubBO;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;
    private String clubName;
    private boolean invalidCN;
    private String clubDesc;
    private boolean invalidBD;
    private Date creationDate;
    private boolean validEmail;
    private boolean invalidEmail;
    private boolean occupiedEmail;
    private String email;
    private boolean updatePassword;
    private boolean invalidP;
    private String password;
    private boolean actSucceded;
    private boolean regError;

    public EditClubProfileBean() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @PostConstruct
    public void initialize() {
        this.email = user.getEmail();
        this.clubName = user.getOwnedClub().getName();
        this.clubDesc = user.getOwnedClub().getDescription();
        this.creationDate = user.getOwnedClub().getCreationDate();
    }

    public boolean checkClubName() {
        if (clubName != null) {
            if (clubName.length() > 0) {
                this.invalidCN = false;
                return true;
            } else {
                this.invalidCN = true;
                return false;
            }
        } else {
            this.invalidCN = true;
            return false;
        }
    }

    public boolean validateBD() {

        if (this.creationDate == null) {
            this.invalidBD = true;
            return false;
        } else {
            this.invalidBD = false;
            return true;
        }
    }

    public boolean validateEmail() {
        if (!umBO.isEmailRegistered(this.email)) {
            if (validate(this.email)) {
                this.validEmail = true;
                this.invalidEmail = false;
                this.occupiedEmail = false;
                return true;
            } else {
                this.validEmail = false;
                this.invalidEmail = true;
                this.occupiedEmail = false;
                return false;
            }
        } else {
            if (!this.email.equals(getUser().getEmail())) {
                this.validEmail = false;
                this.invalidEmail = false;
                this.occupiedEmail = true;
                return false;
            } else {
                this.validEmail = false;
                this.invalidEmail = false;
                this.occupiedEmail = false;
                return true;

            }
        }
    }

    public String updatePassField() {
        this.invalidP = false;
        return "changed";
    }

    public boolean validatePassword() {
        if (password != null) {
            if (password.length() > 4) {
                this.invalidP = false;
                return true;
            } else {
                this.invalidP = true;
                return false;
            }
        } else {
            this.invalidP = true;
            return false;
        }
    }

    public String updateClub() {

        boolean val1 = checkClubName();
        boolean val2 = validateBD();
        boolean val3 = validateEmail();
        if (val1 && val2 && val3) {
            if (this.updatePassword) {
                getUser().setEmail(email);
                getUser().setPassword(password);
                getUser().getOwnedClub().setCreationDate(creationDate);
                getUser().setLogin(email);
                getUser().getOwnedClub().setName(clubName);
                getUser().getOwnedClub().setDescription(clubDesc);
                user = umBO.encodePassword(user);
                clubBO.saveClub(getUser().getOwnedClub());
                umBO.saveUser(user);

            } else {
                getUser().setEmail(email);
                getUser().getOwnedClub().setCreationDate(creationDate);
                getUser().setLogin(email);
                getUser().getOwnedClub().setName(clubName);
                getUser().getOwnedClub().setDescription(clubDesc);
                clubBO.saveClub(getUser().getOwnedClub());
                umBO.saveUser(getUser());

            }
            this.actSucceded = true;
        } else {
            this.regError = true;
        }
        return "update";
    }

    private boolean validate(final String emailAddress) {
        matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean getInvalidCN() {
        return invalidCN;
    }

    public void setInvalidCN(boolean invalidCN) {
        this.invalidCN = invalidCN;
    }

    public String getClubDesc() {
        return clubDesc;
    }

    public void setClubDesc(String clubDesc) {
        this.clubDesc = clubDesc;
    }

    public boolean getInvalidBD() {
        return invalidBD;
    }

    public void setInvalidBD(boolean invalidBD) {
        this.invalidBD = invalidBD;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getValidEmail() {
        return validEmail;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public boolean getInvalidEmail() {
        return invalidEmail;
    }

    public void setInvalidEmail(boolean invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public boolean getOccupiedEmail() {
        return occupiedEmail;
    }

    public void setOccupiedEmail(boolean occupiedEmail) {
        this.occupiedEmail = occupiedEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(boolean updatePassword) {
        this.updatePassword = updatePassword;
    }

    public boolean getInvalidP() {
        return invalidP;
    }

    public void setInvalidP(boolean invalidP) {
        this.invalidP = invalidP;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getActSucceded() {
        return actSucceded;
    }

    public void setActSucceded(boolean actSucceded) {
        this.actSucceded = actSucceded;
    }

    public boolean getRegError() {
        return regError;
    }

    public void setRegError(boolean regError) {
        this.regError = regError;
    }

    public ClubBO getClubBO() {
        return clubBO;
    }

    public void setClubBO(ClubBO clubBO) {
        this.clubBO = clubBO;
    }
    
    
}
