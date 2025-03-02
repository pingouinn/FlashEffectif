package requests;

import auth.AuthManager;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class RequestManager {

    private final String API_URL = "https://pegass.croix-rouge.fr/crf/rest/";
    private String USER;
    private String PASS;

    private Set<Cookie> cookieHeader;

    private WebDriver registeredDriver = null;
    private boolean cookieInjected = false;
    private boolean throwExept = false;

    public RequestManager(String user, String pass) {
        this.USER = user;
        this.PASS = pass;
    }

    public String performRequest(String requestTxt) throws IOException {

        // Checking for sesssion cookies
        authenticateIfCookiesEmpty();

        // If no headless driver is registered, register one
        if (this.registeredDriver == null) { this.registeredDriver = registerHeadlessWebdriver();}

        String json = null;
        
        // Perform the request
        try {
            registeredDriver.get(API_URL + requestTxt); //Preload the page to inject cookies, otherwise an error is thrown
            // Force load the current cookies
            if (!this.cookieInjected) {
                System.out.println("Cookies corrupted or missing, injecting ...");
                for (Cookie cookie : this.cookieHeader) {
                    this.registeredDriver.manage().addCookie(cookie);
                }
                this.cookieInjected = true;
            }

            // Getting the json data from the page body
            registeredDriver.get(API_URL + requestTxt);
            json = registeredDriver.findElement(By.tagName("body")).getText();

            //Get the 3 first characters of the json to check if it is a valid json
            if (json.substring(0, 3).equals(" Se")) { // If it matches the " Se" it means that it asks for a reconnect
                if (throwExept) {
                    this.throwExept = false;
                    throw new RuntimeException("Authentication failed: Invalid cookies. Abort.");
                } else {
                    System.out.println("Cookies corrupted or missing, reinjecting ...");
                    for (Cookie cookie : this.cookieHeader) {
                        this.registeredDriver.manage().addCookie(cookie);
                    }
                    this.cookieInjected = false;
                    this.throwExept = true;
                    json = performRequest(requestTxt);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public WebDriver registerHeadlessWebdriver() {
        System.setProperty("webdriver.opera.driver", "operadriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        return new ChromeDriver(options);
    }

    public void authenticateIfCookiesEmpty() {
        if (this.cookieHeader == null) {
            System.out.println("Performing Authentication");
            this.cookieHeader = AuthManager.authenticate(this.USER, this.PASS);
            if (this.cookieHeader.isEmpty()) {
                throw new RuntimeException("Login failed: No cookies received. Abort.");
            }
        }
    }

    public void setCookies(Set<Cookie> cookies) {
        this.cookieHeader = cookies;
    }
}