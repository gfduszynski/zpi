/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(null);
        response.getWriter().write(s);
    }
}
