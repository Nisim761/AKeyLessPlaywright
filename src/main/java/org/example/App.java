package org.example;

import com.microsoft.playwright.*;

public class App {

    final static String emailAddress = "nisim761+akeyless@gmail.com";
    final static String password = "PlaywrightExample3!";
    final static String staticSecretName = "test";
    final static String staticSecretValue = "test123";

    public static void main( String[] args ) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(150));
            Page page = browser.newPage();

            // navigate to the login page
            page.navigate("https://console.akeyless.io");
            // wait for login page to finish loading
            page.locator("xpath=//p[text()=\"Sign in to your account\"]").waitFor();
            // perform sign-in by entering email address
            page.locator("xpath=//input[@name=\"email\"]").fill(emailAddress);
            // click on "Sign In" button
            page.locator("xpath=//span[text()=\"Sign In\"]/..").click();
            // wait for next page to finish loading
            page.locator("xpath=//input[@name=\"password\"]").waitFor();
            // enter password
            page.locator("xpath=//input[@name=\"password\"]").fill(password);
            // click on "Sign In" button
            page.locator("xpath=//span[text()=\"Sign In\"]/..").click();

            // make sure we are in "Secrets & Keys" section
            page.locator("xpath=//a[@data-test=\"secretsAndKeys\"]").waitFor();
            page.locator("xpath=//a[@data-test=\"secretsAndKeys\"]").click();
            // click "New" button
            page.locator("xpath=//button[@data-test=\"testNewItemButton\"]").waitFor();
            page.locator("xpath=//button[@data-test=\"testNewItemButton\"]").click();
            // click option "Static Secret" from the menu
            page.locator("xpath=//div[@data-test=\"testNewStaticSecretButton\"]").waitFor();
            page.locator("xpath=//div[@data-test=\"testNewStaticSecretButton\"]").click();

            page.locator("xpath=//p[text()=\"Create Static Secret\"]").waitFor();
            // enter name for the new static secret
            page.locator("xpath=//input[@name=\"name\"]").waitFor();
            page.locator("xpath=//input[@name=\"name\"]").fill(staticSecretName);
            // enter value for the new static secret
            page.locator("xpath=//textarea[@name=\"value\"]").waitFor();
            page.locator("xpath=//textarea[@name=\"value\"]").fill(staticSecretValue);
            // click "Finish" button
            page.locator("xpath=//span[text()=\"Finish\"]/..").waitFor();
            page.locator("xpath=//span[text()=\"Finish\"]/..").click();
            // verify a Static Secret item was created with the name "test"
            page.locator("xpath=//span[text()=\"Static Secret\"]/..//h6[text()=\"test\"]").waitFor();
            // verify the value of the Static Secret named "test"
            page.locator("xpath=//button[@class=\"MuiButtonBase-root MuiIconButton-root MuiIconButton-colorPrimary\"]").waitFor();
            page.locator("xpath=//button[@class=\"MuiButtonBase-root MuiIconButton-root MuiIconButton-colorPrimary\"]").click();
            page.locator("xpath=//span[text()=\"Copy to clipboard\"]/../..//button").waitFor();
            String staticSecretValueFromWebUI = page.locator("xpath=//textarea[@data-test=\"testStaticSecretValueField\"]").getAttribute("text");
            // verify the value
            assert staticSecretValue.equals(staticSecretValueFromWebUI);
        } catch (Exception e) {
            System.out.println("Encountered an error: " + e.getMessage());
        }
    }
}
