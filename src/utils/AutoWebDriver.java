package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class AutoWebDriver {
    public static WebDriver getDriver() {
        String browser = BrowserDetector.getDefaultBrowser();
        WebDriver driver = null;

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", "assets/drivers/operadriver.exe");
                driver = new ChromeDriver(); // Because opera is chromium based
                break;
            default:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
        }

        return driver;
    }

    public static WebDriver registerHeadlessWebdriver() {
        String browser = BrowserDetector.getDefaultBrowser();
        WebDriver driver = null;

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless", "--disable-gpu");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "opera":
                System.setProperty("webdriver.opera.driver", "assets/drivers/operadriver.exe");
                ChromeOptions operaOptions = new ChromeOptions();
                operaOptions.addArguments("--headless", "--disable-gpu");
                driver = new ChromeDriver(operaOptions);
                break;

            default:
                System.out.println("Navigateur non pris en charge, lancement de Edge headless par défaut.");
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptionsDef = new EdgeOptions();
                edgeOptionsDef.addArguments("--headless", "--disable-gpu");
                driver = new EdgeDriver(edgeOptionsDef);
        }

        return driver;
    }
}
