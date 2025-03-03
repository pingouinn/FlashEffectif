package utils;

import java.io.IOException;

public class BrowserDetector {
    public static String getDefaultBrowser() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Windows : Check register keys
                ProcessBuilder processBuilder = new ProcessBuilder("reg", "query", "HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\Shell\\Associations\\UrlAssociations\\http\\UserChoice", "/v", "ProgId");
                Process process = processBuilder.start();
                java.util.Scanner s = new java.util.Scanner(process.getInputStream()).useDelimiter("\\A");
                String output = s.hasNext() ? s.next() : "";
                s.close();

                if (output.contains("Chrome")) return "chrome";
                if (output.contains("Firefox")) return "firefox";
                if (output.contains("Edge")) return "edge";
                if (output.contains("Opera")) return "opera";
            } else if (os.contains("mac")) {
                Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "defbrowser=$(python3 -c \"import webbrowser; print(webbrowser.get().name)\"); echo $defbrowser"});
                java.util.Scanner s = new java.util.Scanner(process.getInputStream()).useDelimiter("\\A");
                String output = s.hasNext() ? s.next() : "";
                s.close();
                return output.toLowerCase();
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux : (Tricky) open a URL and get the used browser
                ProcessBuilder processBuilder = new ProcessBuilder("xdg-settings", "get", "default-web-browser");
                Process process = processBuilder.start();
                java.util.Scanner s = new java.util.Scanner(process.getInputStream()).useDelimiter("\\A");
                String output = s.hasNext() ? s.next() : "";
                s.close();

                if (output.contains("chrome")) return "chrome";
                if (output.contains("firefox")) return "firefox";
                if (output.contains("edge")) return "edge";
            }   
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "chrome"; // return chrome dy default
    }
}
