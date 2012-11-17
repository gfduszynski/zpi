/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import org.chessclan.dataTier.models.Game;
import org.chessclan.dataTier.models.Move;
import org.chessclan.dataTier.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Path("/cpService")
@Component
public class ChessPlayerService {

    @Autowired
    GameRepository gRepo;

    @PostConstruct
    public void initialize() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{param}")
    public Response getGameById(@PathParam("param") String msg) {
        int id = Integer.parseInt(msg);
        Game foundGame = gRepo.findOne(id);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
        return Response.status(200).entity(gson.toJson(foundGame)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addExample")
    public Response saveExampleGame(){
        Game game = new Game("Mistrzostwa Polski 2012", "Wrocław", new Date(), 1, "Daniel Engel", "Michał Engel", Game.GameResult.WHITE_WON, null);
        game.addMove(new Move(1, 2, 1, 3, true));
        game.addMove(new Move(1, 7, 1, 6, true));
        game.addMove(new Move(8, 2, 8, 3, true));
        game.addMove(new Move(8, 7, 8, 6, true));
        gRepo.save(game);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
        return Response.status(200).entity(gson.toJson(game)).build();
    }

    public GameRepository getgRepo() {
        return gRepo;
    }

    public void setgRepo(GameRepository gRepo) {
        this.gRepo = gRepo;
    }
}
