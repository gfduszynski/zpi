/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
@WebServlet(urlPatterns = "/UserServlet", asyncSupported = true)
public class UserServletAsync extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        Integer userId = (Integer) request.getAttribute("userid");
        
        User u = null;// new User(1,"daniel", "engel", "email", new Date(), new Date(), 1, "passwd", new Club(6,"name", new Date()));
        
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(u);
        response.getWriter().write(s);
    }
}
