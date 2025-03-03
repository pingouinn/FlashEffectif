import auth.AuthManager;
import requests.Queries;
import utils.ConfigManager;
import auth.sslPolicy;
import dataClasses.CachedActions;
import gui.Interface;

import java.util.Set;
import org.openqa.selenium.Cookie;

public class Main {

    public static void main(String[] args) {
        ConfigManager configManager = new ConfigManager();
        String username = configManager.getUser();
        String password = configManager.getPassword();
        if (username == null || password == null) {
            username = "";
            password = "";
        }
        CachedActions.username = username;
        CachedActions.password = password;

        Interface gui = new Interface();
        gui.setVisible(true);

        // Configure SSL
        gui.showLoading("Configuring SSL ...");
        safeWait(400);
        sslPolicy.configureSSL();
        safeWait(400);
        gui.showLoading("SSL policy set");
        safeWait(400);


        // Authenticate
        gui.showLoading("Authenticating ...");
        Set<Cookie> cookies = new java.util.HashSet<>();
        while (cookies.isEmpty()) {
            gui.showAuthInterface(CachedActions.username, CachedActions.password);
            while (!gui.isAuthDone()) {
                safeWait(200);
            }
            cookies = AuthManager.authenticate(CachedActions.username, CachedActions.password);
            if (cookies.isEmpty()) {
                gui.showError("An error occured while authenticating (Wrong Email / password OR Browser was closed) Please retry !");
                safeWait(2000);
                CachedActions.isAuthDone = false;
            }
        }

        //Finish app
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