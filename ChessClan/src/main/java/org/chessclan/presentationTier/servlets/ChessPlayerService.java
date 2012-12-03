/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.presentationTier.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.chessclan.businessTier.businessObjects.UserManagementBO;
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
    @Autowired
    UserManagementBO umBO;

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
    public Response saveExampleGame() {
        Game game = new Game("Mistrzostwa Polski 2012", "Wrocław", new Date(), 1, "Daniel Engel", "Michał Engel", Game.GameResult.WHITE_WON, null);
        game.addMove(new Move(1, 1, 1, 3, true));
        game.addMove(new Move(4, 6, 4, 4, true));
        game.addMove(new Move(1, 3, 1, 4, true));
        game.addMove(new Move(4, 4, 4, 3, true));
        gRepo.save(game);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
        return Response.status(200).entity(gson.toJson(game)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAll")
    public Response loadAll() {
        List<Game> games = gRepo.findAll();
        for(Game g: games){
            g.setMoves(null);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
        return Response.status(200).entity(gson.toJson(games)).build();
    }

    public GameRepository getgRepo() {
        return gRepo;
    }

    public void setgRepo(GameRepository gRepo) {
        this.gRepo = gRepo;
    }

    public UserManagementBO getUmBO() {
        return umBO;
    }

    public void setUmBO(UserManagementBO umBO) {
        this.umBO = umBO;
    }
}
