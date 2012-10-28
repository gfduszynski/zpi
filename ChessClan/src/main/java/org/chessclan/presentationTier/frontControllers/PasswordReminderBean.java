/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.frontControllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang.RandomStringUtils;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
import org.chessclan.dataTier.models.User;
import org.chessclan.utils.SendEmailHelper;

/**
 *
 * @author Daniel
 */
@ManagedBean
@ViewScoped
public class PasswordReminderBean {

    private String email;
    private boolean emailDoesntExists;
    private boolean emailSent;
    private boolean emailSendingFailed;
    @ManagedProperty("#{UserManagementBO}")
    private UserManagementBO umBO;

    public PasswordReminderBean() {
        this.emailDoesntExists = false;
        this.emailSent = false;
        this.emailSendingFailed = false;
    }

    public void sendEmail() {
        this.emailDoesntExists = false;
        this.emailSent = false;
        this.emailSendingFailed = false;
        User u = umBO.findUserByEmail(email);
        if (u != null) {
            String newPass = RandomStringUtils.randomAlphanumeric(10);
            u = umBO.resetPassword(u, newPass);
            boolean sendRemindPassword = SendEmailHelper.sendRemindPassword(u, newPass);
            if (sendRemindPassword) {
                this.emailSent = true;
            } else {
                this.emailSendingFailed = true;
            }
        } else {
            this.emailDoesntExists = true;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailDoesntExists() {
        return emailDoesntExists;
    }

    public void setEmailDoesntExists(boolean emailDoesntExists) {
        this.emailDoesntExists = emailDoesntExists;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }

    public boolean isEmailSendingFailed() {
        return emailSendingFailed;
    }

    public void setEmailSendingFailed(boolean emailSendingFailed) {
        this.emailSendingFailed = emailSendingFailed;
    }
}
