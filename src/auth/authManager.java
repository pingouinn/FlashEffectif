package auth;

import java.util.Set;
import java.util.HashSet;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthManager {

    private static final String HOME_URL = "https://connect.croix-rouge.fr/app/UserHome";
    private static final String AUTH_URL = "https://connect.croix-rouge.fr/signin";

    public static Set<Cookie> authenticate(String username, String password) {
        if (true) {
            System.setProperty("webdriver.opera.driver", "operadriver.exe");
        } 
        //else {
        //     System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        // }

        //TODO : Add support for other browsers

        WebDriver driver = new ChromeDriver();
        Set<Cookie> cookieSet = new HashSet<>();

        try {
            driver.get(AUTH_URL);

            WebElement usernameField = driver.findElement(By.id("okta-signin-username"));
            WebElement passwordField = driver.findElement(By.id("okta-signin-password"));
            WebElement submitButton = driver.findElement(By.id("okta-signin-submit"));

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            submitButton.click();

            // Get the current page URL and check if its the authenticated page 

            while (!driver.getCurrentUrl().equals(HOME_URL)) {
                Thread.sleep(1000);
            }
            
            cookieSet = driver.manage().getCookies();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return cookieSet;
    }
}
