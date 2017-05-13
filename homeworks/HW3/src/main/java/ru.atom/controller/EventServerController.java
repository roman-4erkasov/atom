package ru.atom.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.mm.MatchMaker;
import ru.atom.dbhackaton.mm.ThreadSafeQueue;
import ru.atom.dbhackaton.mm.ThreadSafeStorage;
import ru.atom.dbhackaton.model.TokenStorage;
import ru.atom.model.GameSession;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kinetik on 13.05.17.
 */
@Path("/*")
public class EventServerController {
    private static final Logger log = LogManager.getLogger(EventServerController.class);
    private int gameObjectId = 1;

    @POST
    @Path("/start")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response startGame(@FormParam("token") String[] candidatesTockens) {
        ArrayList candidates = new ArrayList<String>(Arrays.asList(candidatesTockens));
        GameSession session = new GameSession(gameObjectId++);
        session.newConnection(candidates);
        session.setId(gameObjectId++);
        log.info(session);
        session.start();
        ThreadSafeStorage.put(session);
        return Response.ok().build();
    }
}
