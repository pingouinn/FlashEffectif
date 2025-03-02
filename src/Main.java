import auth.authManager;
import requests.queries;
import utils.dateParser;
import auth.sslPolicy;
import dataClasses.ActivityList;
import gui.Interface;
import dataClasses.Activity;

import java.util.Set;
import org.openqa.selenium.Cookie;

public class Main {

    public static void main(String[] args) {
        //TODO : Implement a way to get the username and password from the GUI
        String username = "user";
        String password = "pass";

        System.out.println("Starting ...");
        Interface gui = new Interface();
        gui.setVisible(true);

        // Configure SSL
        gui.updateTextCentered("Configuring SSL ...");
        safeWait(400);
        sslPolicy.configureSSL();
        safeWait(400);
        gui.updateTextCentered("SSL policy set");

        //Authenticate
        safeWait(400);
        gui.showLoading("Authenticating ...");
        Set<Cookie> cookies = authManager.authenticate(username, password);
        if (cookies.isEmpty()) {
            gui.showTextCentered("An error occured while authenticating ... Aborting");
            safeWait(2000);
            return;
        }
        gui.showTextCentered("Authenticated as " + username + " !");

        queries queryHandler = new queries(username, password, cookies);
        try {
            gui.showLoading("Requesting activities from server ...");
            ActivityList res = queryHandler.APIgetActivitiesList(18, dateParser.stringToDate("2025-02-28"), dateParser.stringToDate("2025-03-05"));
            String loaded = "Loaded activities : ";
            for (Activity activity : res.getActivities().values()) {
                loaded += activity.getName() + ", ";
            }
            gui.showTextCentered(loaded);
        } catch (Exception e) {
            System.out.println("An error occured while performing the request");            
            e.printStackTrace();
        }
    }

    private static void safeWait(Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}