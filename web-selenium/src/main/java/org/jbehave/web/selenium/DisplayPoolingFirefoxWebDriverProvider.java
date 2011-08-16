package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxBinary;

public class DisplayPoolingFirefoxWebDriverProvider extends FirefoxWebDriverProvider {

    private DisplayIdPool displayIdPool;
    private ThreadLocal<String> displayScreenId = new ThreadLocal<String>();
    private int refCt = 0;
    public DisplayPoolingFirefoxWebDriverProvider(DisplayIdPool displayPool) {
        this.displayIdPool = displayPool;
    }

    @Override
    protected void decorateFirefoxBinary(FirefoxBinary binary) {
        super.decorateFirefoxBinary(binary);
        String displayID = displayIdPool.get();
        displayScreenId.set(displayID);
        binary.setEnvironmentProperty("DISPLAY", displayID);
        System.out.println(new StringBuffer("Displays used=").append(++refCt).toString());
    }

    @Override
    protected void ending() {
        super.ending();
        String displayId = displayScreenId.get();
        displayIdPool.returnToPool(displayId);
        System.out.println(new StringBuffer("Displays used=").append(--refCt).toString());
    }


}
