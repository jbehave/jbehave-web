package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.Condition;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.ConditionRunner.Context;
import com.thoughtworks.selenium.condition.Not;
import com.thoughtworks.selenium.condition.Presence;
import com.thoughtworks.selenium.condition.Text;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Abstract base class for pages that use the Selenium API. It contains common
 * page methods, with a view to implement the <a
 * href="http://code.google.com/p/selenium/wiki/PageObjects">Page Objects</a>
 * pattern.
 */
public class SeleniumPage implements Selenium {

    protected final Selenium selenium;
    protected final ConditionRunner conditionRunner;

    public SeleniumPage(Selenium selenium, ConditionRunner conditionRunner) {
        this.selenium = selenium;
        this.conditionRunner = conditionRunner;
    }

    public void open(String url) {
        selenium.open(url);
    }

    public void openWindow(String url, String windowID) {
        selenium.openWindow(url, windowID);
    }

    public void selectWindow(String windowID) {
        selenium.selectWindow(windowID);
    }

    public void selectPopUp(String windowID) {
        selenium.selectPopUp(windowID);
    }

    public void deselectPopUp() {
        selenium.deselectPopUp();
    }

    public void selectFrame(String locator) {
        selenium.selectFrame(locator);
    }

    public boolean getWhetherThisFrameMatchFrameExpression(String currentFrameString, String target) {
        return selenium.getWhetherThisFrameMatchFrameExpression(currentFrameString, target);
    }

    public boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString, String target) {
        return selenium.getWhetherThisWindowMatchWindowExpression(currentWindowString, target);
    }

    public void waitForPopUp(String windowID, String timeout) {
        selenium.waitForPopUp(windowID, timeout);
    }

    public void chooseCancelOnNextConfirmation() {
        selenium.chooseCancelOnNextConfirmation();
    }

    public void chooseOkOnNextConfirmation() {
        selenium.chooseOkOnNextConfirmation();
    }

    public void answerOnNextPrompt(String answer) {
        selenium.answerOnNextPrompt(answer);
    }

    public void goBack() {
        selenium.goBack();
    }

    public void refresh() {
        selenium.refresh();
    }

    public void close() {
        selenium.close();
    }

    public boolean isAlertPresent() {
        return selenium.isAlertPresent();
    }

    public boolean isPromptPresent() {
        return selenium.isPromptPresent();
    }

    public boolean isConfirmationPresent() {
        return selenium.isConfirmationPresent();
    }

    public String getAlert() {
        return selenium.getAlert();
    }

    public String getConfirmation() {
        return selenium.getConfirmation();
    }

    public String getPrompt() {
        return selenium.getPrompt();
    }

    public String getLocation() {
        return selenium.getLocation();
    }

    public String getTitle() {
        return selenium.getTitle();
    }

    public String getBodyText() {
        return selenium.getBodyText();
    }

    public String getValue(String locator) {
        return selenium.getValue(locator);
    }

    public String getText(String locator) {
        return selenium.getText(locator);
    }

    public void highlight(String locator) {
        selenium.highlight(locator);
    }

    public String getEval(String script) {
        return selenium.getEval(script);
    }

    public boolean isChecked(String locator) {
        return selenium.isChecked(locator);
    }

    public String getTable(String tableCellAddress) {
        return selenium.getTable(tableCellAddress);
    }

    public String[] getSelectedLabels(String selectLocator) {
        return selenium.getSelectedLabels(selectLocator);
    }

    public String getSelectedLabel(String selectLocator) {
        return selenium.getSelectedLabel(selectLocator);
    }

    public String[] getSelectedValues(String selectLocator) {
        return selenium.getSelectedValues(selectLocator);
    }

    public String getSelectedValue(String selectLocator) {
        return selenium.getSelectedValue(selectLocator);
    }

    public String[] getSelectedIndexes(String selectLocator) {
        return selenium.getSelectedIndexes(selectLocator);
    }

    public String getSelectedIndex(String selectLocator) {
        return selenium.getSelectedIndex(selectLocator);
    }

    public String[] getSelectedIds(String selectLocator) {
        return selenium.getSelectedIds(selectLocator);
    }

    public String getSelectedId(String selectLocator) {
        return selenium.getSelectedId(selectLocator);
    }

    public boolean isSomethingSelected(String selectLocator) {
        return selenium.isSomethingSelected(selectLocator);
    }

    public String[] getSelectOptions(String selectLocator) {
        return selenium.getSelectOptions(selectLocator);
    }

    public String getAttribute(String attributeLocator) {
        return selenium.getAttribute(attributeLocator);
    }

    public boolean isTextPresent(String pattern) {
        return selenium.isTextPresent(pattern);
    }

    public boolean isElementPresent(String locator) {
        return selenium.isElementPresent(locator);
    }

    public boolean isVisible(String locator) {
        return selenium.isVisible(locator);
    }

    public boolean isEditable(String locator) {
        return selenium.isEditable(locator);
    }

    public String[] getAllButtons() {
        return selenium.getAllButtons();
    }

    public String[] getAllLinks() {
        return selenium.getAllLinks();
    }

    public String[] getAllFields() {
        return selenium.getAllFields();
    }

    public String[] getAttributeFromAllWindows(String attributeName) {
        return selenium.getAttributeFromAllWindows(attributeName);
    }

    public void dragdrop(String locator, String movementsString) {
        selenium.dragdrop(locator, movementsString);
    }

    public void setMouseSpeed(String pixels) {
        selenium.setMouseSpeed(pixels);
    }

    public Number getMouseSpeed() {
        return selenium.getMouseSpeed();
    }

    public void dragAndDrop(String locator, String movementsString) {
        selenium.dragAndDrop(locator, movementsString);
    }

    public void dragAndDropToObject(String locatorOfObjectToBeDragged, String locatorOfDragDestinationObject) {
        selenium.dragAndDropToObject(locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
    }

    public void windowFocus() {
        selenium.windowFocus();
    }

    public void windowMaximize() {
        selenium.windowMaximize();
    }

    public String[] getAllWindowIds() {
        return selenium.getAllWindowIds();
    }

    public String[] getAllWindowNames() {
        return selenium.getAllWindowNames();
    }

    public String[] getAllWindowTitles() {
        return selenium.getAllWindowTitles();
    }

    public String getHtmlSource() {
        return selenium.getHtmlSource();
    }

    public void setCursorPosition(String locator, String position) {
        selenium.setCursorPosition(locator, position);
    }

    public Number getElementIndex(String locator) {
        return selenium.getElementIndex(locator);
    }

    public boolean isOrdered(String locator1, String locator2) {
        return selenium.isOrdered(locator1, locator2);
    }

    public Number getElementPositionLeft(String locator) {
        return selenium.getElementPositionLeft(locator);
    }

    public Number getElementPositionTop(String locator) {
        return selenium.getElementPositionTop(locator);
    }

    public Number getElementWidth(String locator) {
        return selenium.getElementWidth(locator);
    }

    public Number getElementHeight(String locator) {
        return selenium.getElementHeight(locator);
    }

    public Number getCursorPosition(String locator) {
        return selenium.getCursorPosition(locator);
    }

    public String getExpression(String expression) {
        return selenium.getExpression(expression);
    }

    public Number getXpathCount(String xpath) {
        return selenium.getXpathCount(xpath);
    }

    public Number getCssCount(String css) {
        return selenium.getCssCount(css);
    }

    public void assignId(String locator, String identifier) {
        selenium.assignId(locator, identifier);
    }

    public void allowNativeXpath(String allow) {
        selenium.allowNativeXpath(allow);
    }

    public void ignoreAttributesWithoutValue(String ignore) {
        selenium.ignoreAttributesWithoutValue(ignore);
    }

    public void waitForCondition(String script, String timeout) {
        selenium.waitForCondition(script, timeout);
    }

    public void setTimeout(String timeout) {
        selenium.setTimeout(timeout);
    }

    public void waitForPageToLoad(String timeout) {
        selenium.waitForPageToLoad(timeout);
    }

    public void waitForFrameToLoad(String frameAddress, String timeout) {
        selenium.waitForFrameToLoad(frameAddress, timeout);
    }

    public String getCookie() {
        return selenium.getCookie();
    }

    public String getCookieByName(String name) {
        return selenium.getCookieByName(name);
    }

    public boolean isCookiePresent(String name) {
        return selenium.isCookiePresent(name);
    }

    public void createCookie(String nameValuePair, String optionsString) {
        selenium.createCookie(nameValuePair, optionsString);
    }

    public void deleteCookie(String name, String optionsString) {
        selenium.deleteCookie(name, optionsString);
    }

    public void deleteAllVisibleCookies() {
        selenium.deleteAllVisibleCookies();
    }

    public void setBrowserLogLevel(String logLevel) {
        selenium.setBrowserLogLevel(logLevel);
    }

    public void runScript(String script) {
        selenium.runScript(script);
    }

    public void addLocationStrategy(String strategyName, String functionDefinition) {
        selenium.addLocationStrategy(strategyName, functionDefinition);
    }

    public void captureEntirePageScreenshot(String filename, String kwargs) {
        selenium.captureEntirePageScreenshot(filename, kwargs);
    }

    public void rollup(String rollupName, String kwargs) {
        selenium.rollup(rollupName, kwargs);
    }

    public void addScript(String scriptContent, String scriptTagId) {
        selenium.addScript(scriptContent, scriptTagId);
    }

    public void removeScript(String scriptTagId) {
        selenium.removeScript(scriptTagId);
    }

    public void useXpathLibrary(String libraryName) {
        selenium.useXpathLibrary(libraryName);
    }

    public void setContext(String context) {
        selenium.setContext(context);
    }

    public void attachFile(String fieldLocator, String fileLocator) {
        selenium.attachFile(fieldLocator, fileLocator);
    }

    public void captureScreenshot(String filename) {
        selenium.captureScreenshot(filename);
    }

    public String captureScreenshotToString() {
        return selenium.captureScreenshotToString();
    }

    public String captureNetworkTraffic(String type) {
        return selenium.captureNetworkTraffic(type);
    }

    public void addCustomRequestHeader(String key, String value) {
        selenium.addCustomRequestHeader(key, value);
    }

    public String captureEntirePageScreenshotToString(String kwargs) {
        return selenium.captureEntirePageScreenshotToString(kwargs);
    }

    public void shutDownSeleniumServer() {
        selenium.shutDownSeleniumServer();
    }

    public String retrieveLastRemoteControlLogs() {
        return selenium.retrieveLastRemoteControlLogs();
    }

    public void keyDownNative(String keycode) {
        selenium.keyDownNative(keycode);
    }

    public void keyUpNative(String keycode) {
        selenium.keyUpNative(keycode);
    }

    public void keyPressNative(String keycode) {
        selenium.keyPressNative(keycode);
    }

    public void click(String locator) {
        selenium.click(locator);
    }

    public void doubleClick(String locator) {
        selenium.doubleClick(locator);
    }

    public void contextMenu(String locator) {
        selenium.contextMenu(locator);
    }

    public void clickAt(String locator, String coordString) {
        selenium.clickAt(locator, coordString);
    }

    public void doubleClickAt(String locator, String coordString) {
        selenium.doubleClickAt(locator, coordString);
    }

    public void contextMenuAt(String locator, String coordString) {
        selenium.contextMenuAt(locator, coordString);
    }

    public void fireEvent(String locator, String eventName) {
        selenium.fireEvent(locator, eventName);
    }

    public void focus(String locator) {
        selenium.focus(locator);
    }

    public void keyPress(String locator, String keySequence) {
        selenium.keyPress(locator, keySequence);
    }

    public void shiftKeyDown() {
        selenium.shiftKeyDown();
    }

    public void shiftKeyUp() {
        selenium.shiftKeyUp();
    }

    public void metaKeyDown() {
        selenium.metaKeyDown();
    }

    public void metaKeyUp() {
        selenium.metaKeyUp();
    }

    public void altKeyDown() {
        selenium.altKeyDown();
    }

    public void altKeyUp() {
        selenium.altKeyUp();
    }

    public void controlKeyDown() {
        selenium.controlKeyDown();
    }

    public void controlKeyUp() {
        selenium.controlKeyUp();
    }

    public void keyDown(String locator, String keySequence) {
        selenium.keyDown(locator, keySequence);
    }

    public void keyUp(String locator, String keySequence) {
        selenium.keyUp(locator, keySequence);
    }

    public void mouseOver(String locator) {
        selenium.mouseOver(locator);
    }

    public void mouseOut(String locator) {
        selenium.mouseOut(locator);
    }

    public void mouseDown(String locator) {
        selenium.mouseDown(locator);
    }

    public void mouseDownRight(String locator) {
        selenium.mouseDownRight(locator);
    }

    public void mouseDownAt(String locator, String coordString) {
        selenium.mouseDownAt(locator, coordString);
    }

    public void mouseDownRightAt(String locator, String coordString) {
        selenium.mouseDownRightAt(locator, coordString);
    }

    public void mouseUp(String locator) {
        selenium.mouseUp(locator);
    }

    public void mouseUpRight(String locator) {
        selenium.mouseUpRight(locator);
    }

    public void mouseUpAt(String locator, String coordString) {
        selenium.mouseUpAt(locator, coordString);
    }

    public void mouseUpRightAt(String locator, String coordString) {
        selenium.mouseUpRightAt(locator, coordString);
    }

    public void mouseMove(String locator) {
        selenium.mouseMove(locator);
    }

    public void mouseMoveAt(String locator, String coordString) {
        selenium.mouseMoveAt(locator, coordString);
    }

    public void clickButton(String name) {
        click("//input[@value='" + name + "']");
    }

    public void clickLink(String name) {
        click("link=" + name + "");
    }

    public void select(String locator, String option) {
        selenium.select(locator, option);        
    }

    public void addSelection(String locator, String optionLocator) {
        selenium.addSelection(locator, optionLocator);
    }

    public void removeSelection(String locator, String optionLocator) {
        selenium.removeSelection(locator, optionLocator);
    }

    public void removeAllSelections(String locator) {
        selenium.removeAllSelections(locator);
    }

    public void submit(String formLocator) {
        selenium.submit(formLocator);
    }

    public void open(String url, String ignoreResponseCode) {
        selenium.open(url, ignoreResponseCode);
    }

    public void selectByLabel(String locator, String label) {
        select(locator, "label="+label);        
    }

    public void selectByValue(String locator, String value) {
        select(locator, "value="+value);        
    }

    
    public void type(String locator, String value) {
        selenium.type(locator, value);
    }

    public void typeKeys(String locator, String value) {
        selenium.typeKeys(locator, value);
    }

    public void setSpeed(String value) {
        selenium.setSpeed(value);
    }

    public String getSpeed() {
        return selenium.getSpeed();
    }

    public String getLog() {
        return selenium.getLog();
    }

    public void check(String locator) {
        selenium.check(locator);
    }

    public void uncheck(String locator) {
        selenium.uncheck(locator);
    }

    public String text(String locator) {
        return selenium.getText(locator);
    }

    public String value(String locator) {
        return selenium.getValue(locator);
    }

    public void textIsVisible(String text) {
        textIsVisible(text, null);
    }

    public void textIsVisible(String text, String locator) {
        waitFor(new Text(text, locator));
    }

    public void textIsNotVisible(String text) {
        textIsNotVisible(text, null);
    }

    public void textIsNotVisible(String text, String locator) {
        waitFor(new Not(new Text(text, locator)));
    }

    public void waitFor(Condition condition) {
        conditionRunner.waitFor(condition);
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(30);
    }

    public void waitForPageToLoad(int seconds) {
        String timeout = String.valueOf(seconds * 1000);
        selenium.waitForPageToLoad(timeout);
    }

    public static void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // continue
        }
    }

    public static class NonBlank extends Presence {
        private String locator;

        public NonBlank(String locator) {
            super(locator);
            this.locator = locator;
        }

        
        public boolean isTrue(Context context) {
            String text = null;
            if (super.isTrue(context)) {
                text = context.getSelenium().getText(locator);
            }
            return isNotBlank(text);
        }
    }

    public void setExtensionJs(String extensionJs) {
        selenium.setExtensionJs(extensionJs);
    }

    public void start() {
        throw new UnsupportedOperationException("managed by JBehave");
    }

    public void start(String optionsString) {
        throw new UnsupportedOperationException("managed by JBehave");
    }

    public void start(Object optionsObject) {
        throw new UnsupportedOperationException("managed by JBehave");
    }

    public void stop() {
        throw new UnsupportedOperationException("managed by JBehave");
    }

    public void showContextualBanner() {
        selenium.showContextualBanner();
    }

    public void showContextualBanner(String className, String methodName) {
        selenium.showContextualBanner(className, methodName);
    }
}
