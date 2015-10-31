package org.jbehave.web.selenium;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Response;

/**
 * Provides Firefox WebDriver instances, using a profile if the name 
 * is provided via the system property {@link #FIREFOX_PROFILE}.
 */
public class FirefoxWebDriverProvider extends DelegatingWebDriverProvider {

    private final WebDriverJournal journal = new WebDriverJournal();
    private static final String JOURNAL_FIREFOX_COMMANDS = System.getProperty("JOURNAL_FIREFOX_COMMANDS", "false");

    public static final String FIREFOX_PROFILE = "JBEHAVE_WEBDRIVER_FIREFOX_PROFILE";
    public void initialize() {
        String profileName = System.getProperty(FIREFOX_PROFILE);
        final FirefoxBinary binary = new FirefoxBinary();
        decorateFirefoxBinary(binary);
        OverriddenFirefoxDriver firefoxDriver = null;
        if (profileName != null) {
            ProfilesIni allProfilesIni = new ProfilesIni();
            FirefoxProfile profile = allProfilesIni.getProfile(profileName);
            profile.setAcceptUntrustedCertificates(false);
            firefoxDriver = new OverriddenFirefoxDriver(binary, profile);
            delegate.set(firefoxDriver);
        } else {
            firefoxDriver = null;

            final WebDriver[] fireFoxDriverz = new WebDriver[1];
            firefoxDriver = new DoublyOverriddenFirefoxDriver(binary, fireFoxDriverz);

            fireFoxDriverz[0] = firefoxDriver;

            delegate.set(firefoxDriver);

        }
        firefoxDriver.setCommandExecutor(new OverridableCommandExecutor(firefoxDriver.getCommandExecutor()));
    }

    public WebDriverJournal getJournal() {
        return journal;
    }

    public void clearJournal() {
        journal.clear();
    }
    
    protected void ending() {
    }
    
    protected void decorateFirefoxBinary(FirefoxBinary binary) {
    }

    protected static class OverriddenFirefoxDriver extends FirefoxDriver {
        public OverriddenFirefoxDriver(FirefoxBinary binary, FirefoxProfile profile) {
            super(binary, profile);
        }

        @Override
        public void setCommandExecutor(CommandExecutor executor) {
            super.setCommandExecutor(executor);
        }

        @Override
        protected void stopClient() {
            ((OverridableCommandExecutor) this.getCommandExecutor()).stopClient();
        }

    }

    private class DoublyOverriddenFirefoxDriver extends OverriddenFirefoxDriver {

        private final WebDriver[] fireFoxDriverz;

        public DoublyOverriddenFirefoxDriver(FirefoxBinary binary, WebDriver[] fireFoxDriverz) {
            super(binary, null);
            this.fireFoxDriverz = fireFoxDriverz;
        }

        @Override
        protected void stopClient(){
            super.stopClient();
            FirefoxWebDriverProvider.this.ending();
        }

        @Override
        public void close() {
            super.close();
        }

    }

    public static class WebDriverJournal extends ArrayList<WebDriverJournal.Entry> {

        long start = System.currentTimeMillis();
        
        public void add(long when, long dur, Command command, Response response) {
            if (!JOURNAL_FIREFOX_COMMANDS.equals("false")) {
                add(new Entry(when, dur, command, response));
            }
        }
        
        public class Entry {

            private final long when;
            private final long dur;
            private final Command command;
            private final Response response;

            public Entry(long when, long dur, Command command, Response response) {
                this.when = when;
                this.dur = dur;
                this.command = command;
                this.response = response;
            }

            @Override
            public String toString() {
                return "" + (when - start) + " (" + dur + "): " + command + " -> " + response;
            }
        }
    }

    private class OverridableCommandExecutor implements CommandExecutor {
        private final CommandExecutor realExecutor;
        private Method quitMethod;

        public OverridableCommandExecutor(CommandExecutor realExecutor) {
            this.realExecutor = realExecutor;
            try {
                Class<? extends CommandExecutor> aClass = realExecutor.getClass();
                quitMethod = aClass.getDeclaredMethod("quit");
                quitMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
            }
        }

        public Response execute(Command command) throws IOException {
            long when = System.currentTimeMillis();
            Response execution = null;
            try {
                execution = realExecutor.execute(command);
            } finally {
                long dur = System.currentTimeMillis() - when;
                synchronized (journal) {
                    journal.add(when, dur, command, execution);
                }
            }
            return execution;
        }

        protected void stopClient() {
            try {
                quitMethod.invoke(realExecutor);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }


    }
}