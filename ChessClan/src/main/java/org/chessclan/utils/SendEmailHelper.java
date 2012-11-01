/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.utils;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
public class SendEmailHelper {

    public static boolean sendRemindPassword(User u, String password) {
        boolean success = true;
        try{
        String[] email = {u.getLogin()};
        sendEmail(email, "Przypomnienie hasła", "Twoje hasło to: '"+password+"'.");

        }catch(AddressException e){
            success=false;
        }catch(MessagingException e){
            success=false;
        }
        return success;
    }

    /**
     *
     * @param to
     * @param subject
     * @param content
     * @throws AddressException
     * @throws MessagingException
     */
    public static void sendEmail(String[] to, String subject, String content) throws AddressException, MessagingException {

        String host = "smtp.gmail.com";
        String username = "chessclan";
        String password = "chessclan11";
        Properties props = new Properties();
        props.put("mail.smtps.auth", "true");

        Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);

        Address[] addresses = new Address[to.length];

        for (int i = 0; i < to.length; i++) {
            addresses[i] = new InternetAddress(to[i]);
        }

        msg.addRecipients(Message.RecipientType.TO, addresses);
        msg.setSubject(subject);
        msg.setText(content);

        Transport t = session.getTransport("smtps");

        try {

            t.connect(host, username, password);
            t.sendMessage(msg, msg.getAllRecipients());

        } finally {

            t.close();

        }
    }
}
