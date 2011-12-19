package org.jbehave.web.selenium;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * <p>
 * Provides a {@link RemoteWebDriver} that connects to a URL specified by system
 * property "REMOTE_WEBDRIVER_URL" and allows to take screenshots.
 * </p>
 * <p>
 * The default {@link DesiredCapabilities}, specified by
 * {@link #defaultDesiredCapabilities()}, are for Windows Firefox 3.6 allowing
 * screenshots.
 * </p>
 */
public class RemoteWebDriverProvider extends DelegatingWebDriverProvider {

    protected DesiredCapabilities desiredCapabilities;
    private boolean verbose = false;

    /**
     * With default capabilities
     * @see RemoteWebDriverProvider#defaultDesiredCapabilities()
     */
    public RemoteWebDriverProvider() {
        this(defaultDesiredCapabilities());
    }

    /**
     * Default Desired Capabilities: Any-Platform,
     * Any Firefox Version, unless something is specified via a system-property "browser.version"
     * and 'Takes Screen-Shot'
     * @return a DesiredCapabilities matching the above.
     */
    public static DesiredCapabilities defaultDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        String bv = System.getProperty("browser.version");
        desiredCapabilities.setVersion(bv == null ? "3.6." : bv);
        return desiredCapabilities;
    }

    public RemoteWebDriverProvider(DesiredCapabilities desiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
    }

    public void initialize() {
        URL url = null;
        WebDriver remoteWebDriver;
        try {
            url = createRemoteURL();
            remoteWebDriver = new ScreenshootingRemoteWebDriver(wrapCommandExecutor(new HttpCommandExecutor(url)), desiredCapabilities);
        } catch (Throwable e) {
            if (verbose) {
                System.err.println("*********** Remote WebDriver Initialization Failure ************");
                e.printStackTrace(System.err);
            }
            throw new UnsupportedOperationException("Connecting to remote URL '" + url + "' failed: " + e.getMessage(),
                    e);
        }
        // Augmenter does not work. Resulting WebDriver is good for exclusive
        // screenshooting, but not normal operation as 'session is null'
        // remoteWebDriver = new Augmenter().augment(remoteWebDriver);
        // should allow instanceof TakesScreenshot.
        // To take out when this is fixed in Selenium 2.0b4 (beta 4)
        delegate.set(remoteWebDriver);
    }

    /**
     * Override this to instrument CommandExecutor
     * @return a CommandExecutor instance.
     * @param httpCommandExecutor the actual CommandExecutor that communicates over the wire.
     */
    protected HttpCommandExecutor wrapCommandExecutor(HttpCommandExecutor httpCommandExecutor) {
        return httpCommandExecutor;
    }

    public URL createRemoteURL() throws MalformedURLException {
        String url = System.getProperty("REMOTE_WEBDRIVER_URL");
        if (url == null) {
            throw new UnsupportedOperationException("REMOTE_WEBDRIVER_URL property not specified");
        }
        return new URL(url);
    }

    static class ScreenshootingRemoteWebDriver extends RemoteWebDriver implements TakesScreenshot {

        public ScreenshootingRemoteWebDriver(CommandExecutor commandExecutor, DesiredCapabilities desiredCapabilities) {
            super(commandExecutor, desiredCapabilities);
        }

        public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
            // Paul: Copied from FirefoxDriver.......
            // Get the screenshot as base64.
            String base64 = execute(DriverCommand.SCREENSHOT).getValue().toString();
            // ... and convert it.
            return target.convertFromBase64Png(base64);
        }
    }

    public void useVerbosity(boolean verbose) {
        this.verbose = verbose;
    }
}
