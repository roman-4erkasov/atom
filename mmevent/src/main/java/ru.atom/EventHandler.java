package ru.atom;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import ru.atom.controller.EventServerController;
import ru.atom.dbhackaton.mm.MatchMakerService;
import ru.atom.model.GameSession;
import ru.atom.network.Broker;
import ru.atom.network.ConnectionPool;
import ru.atom.network.Topic;

import javax.ws.rs.PathParam;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vladfedorenko on 02.05.17.
 */

public class EventHandler extends WebSocketAdapter {
    private static AtomicInteger playerIdGenerator = new AtomicInteger(0);

    public static int getPlayerIdGenerator() {
        return playerIdGenerator.intValue();
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        //ConnectionPool.getInstance().add(sess, playerToken);
        ConnectionPool.getInstance().add(sess, "player_" + playerIdGenerator.getAndIncrement());
        System.out.println("Socket Connected: " + sess);
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        Broker.getInstance().receive(super.getSession(), message);
        System.out.println("Received TEXT message: " + message);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}