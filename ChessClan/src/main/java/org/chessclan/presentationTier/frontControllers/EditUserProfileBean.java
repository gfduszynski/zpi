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
import org.chessclan.dataTier.models.Role;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "epBean")
@ViewScoped
public class EditUserProfileBean implements Serializable {

    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;
    @ManagedProperty("#{UserManagementBO}")
    UserManagementBO umBO;
    @ManagedProperty("#{ClubBO}")
    ClubBO clubBO;
    @ManagedProperty(value="#{loginBean.user}")
    private User user;
    
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String password;
    private int sex;
    private String clubName;
    private String clubDescription;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;
    private boolean validEmail;
    private boolean invalidEmail;
    private boolean occupiedEmail;
    private boolean invalidFN;
    private boolean invalidLN;
    private boolean invalidBD;
    private boolean invalidP;
    private boolean regError;
    private boolean invalidCN;
    private boolean actSucceded;
    private boolean updatePassword;

    public EditUserProfileBean() {
        validEmail = false;
        invalidEmail = false;
        this.invalidBD = false;
        this.invalidFN = false;
        this.invalidLN = false;
        this.invalidP = false;
        this.regError = false;
        this.updatePassword = false;
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @PostConstruct
    public void initialize() {
        this.firstName = loginBean.getUser().getFirstName();
        this.lastName = loginBean.getUser().getLastName();
        this.birthDate = loginBean.getUser().getBirthDate();
        this.email = loginBean.getUser().getEmail();
        this.sex = loginBean.getUser().getSex();
    }

    public boolean validateFirstName() {
        if (firstName != null) {
            if (firstName.length() > 2) {
                this.invalidFN = false;
                return true;
            } else {
                this.invalidFN = true;
                return false;
            }
        } else {
            this.invalidFN = true;
            return false;
        }
    }

    public boolean validateClubName() {
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

    public boolean validateLastName() {
        if (lastName != null) {
            if (lastName.length() > 2) {
                this.invalidLN = false;
                return true;
            } else {
                this.invalidLN = true;
                return false;
            }
        } else {
            this.invalidLN = true;
            return false;
        }
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

    public boolean validateBD() {

        if (this.birthDate == null) {
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
            if (!this.email.equals(loginBean.getUser().getEmail())) {
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

    private boolean validate(final String emailAddress) {
        matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    public void update() {

        boolean val1 = validateFirstName();
        boolean val2 = validateLastName();
        boolean val3 = validateBD();
        boolean val4 = validateEmail();
        System.out.print("updateing...");
        System.out.println("params: " + val1 + " : " + val2 + " : " + val3 + " : " + val4);
        if (val1 && val2 && val3 && val4) {
            if (this.updatePassword) {
                loginBean.getUser().setEmail(email);
                loginBean.getUser().setFirstName(firstName);
                loginBean.getUser().setLastName(lastName);
                loginBean.getUser().setPassword(password);
                loginBean.getUser().setBirthDate(birthDate);
                loginBean.getUser().setLogin(email);
                loginBean.getUser().setSex(sex);
                user = umBO.encodePassword(user);
                umBO.saveUser(user); 

            } else {
                loginBean.getUser().setEmail(email);
                loginBean.getUser().setFirstName(firstName);
                loginBean.getUser().setLastName(lastName);
                loginBean.getUser().setBirthDate(birthDate);
                loginBean.getUser().setLogin(email);
                loginBean.getUser().setSex(sex);
                umBO.saveUser(loginBean.getUser());

            }
            this.actSucceded = true;
        } else {
            this.regError = true;
        }

    }

    public void updatePassField() {
        this.invalidP = false;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public ClubBO getClubBO() {
        return clubBO;
    }

    public void setClubBO(ClubBO clubBO) {
        this.clubBO = clubBO;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public boolean isValidEmail() {
        return validEmail;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public boolean isInvalidEmail() {
        return invalidEmail;
    }

    public void setInvalidEmail(boolean invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public boolean isOccupiedEmail() {
        return occupiedEmail;
    }

    public void setOccupiedEmail(boolean occupiedEmail) {
        this.occupiedEmail = occupiedEmail;
    }

    public boolean isInvalidFN() {
        return invalidFN;
    }

    public void setInvalidFN(boolean invalidFN) {
        this.invalidFN = invalidFN;
    }

    public boolean isInvalidLN() {
        return invalidLN;
    }

    public void setInvalidLN(boolean invalidLN) {
        this.invalidLN = invalidLN;
    }

    public boolean isInvalidBD() {
        return invalidBD;
    }

    public void setInvalidBD(boolean invalidBD) {
        this.invalidBD = invalidBD;
    }

    public boolean isInvalidP() {
        return invalidP;
    }

    public void setInvalidP(boolean invalidP) {
        this.invalidP = invalidP;
    }

    public boolean isRegError() {
        return regError;
    }

    public void setRegError(boolean regError) {
        this.regError = regError;
    }

    public boolean isInvalidCN() {
        return invalidCN;
    }

    public void setInvalidCN(boolean invalidCN) {
        this.invalidCN = invalidCN;
    }

    public boolean isActSucceded() {
        return actSucceded;
    }

    public void setActSucceded(boolean actSucceded) {
        this.actSucceded = actSucceded;
    }

    public boolean isUpdatePassword() {
        return updatePassword;
    }

    public void setUpdatePassword(boolean updatePassword) {
        this.updatePassword = updatePassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User loggedUser) {
        this.user = loggedUser;
    }
}
