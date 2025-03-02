import auth.AuthManager;
import requests.Queries;
import auth.sslPolicy;
import dataClasses.CachedActions;
import gui.Interface;

import java.util.Set;
import org.openqa.selenium.Cookie;

public class Main {

    public static void main(String[] args) {
        //TODO : Implement a way to get the username and password from the GUI
        String username = "suer";
        String password = "pass";

        System.out.println("Starting ...");
        Interface gui = new Interface();
        gui.setVisible(true);

        // Configure SSL
        gui.showLoading("Configuring SSL ...");
        safeWait(400);
        sslPolicy.configureSSL();
        safeWait(400);
        gui.showLoading("SSL policy set");

        //Authenticate
        safeWait(400);
        gui.showLoading("Authenticating ...");
        Set<Cookie> cookies = AuthManager.authenticate(username, password);
        if (cookies.isEmpty()) {
            gui.showError("An error occured while authenticating ... Aborting");
            safeWait(2000);
            CachedActions.shouldKillGui = true;
            return;
        }
        gui.showValid("Authenticated as " + username + " !");
        safeWait(2000);

        Queries queryHandler = new Queries(username, password, cookies);
        gui.setQueryHandler(queryHandler);

        return;
    }

    private static void safeWait(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}