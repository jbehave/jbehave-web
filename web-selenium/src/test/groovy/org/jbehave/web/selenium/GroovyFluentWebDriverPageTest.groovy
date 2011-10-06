package org.jbehave.web.selenium

class GroovyFluentWebDriverPageTest extends GroovyWebDriverPageTest {

    @Override
    protected WebDriverPage makeGroovyWebDriverPage(WebDriverProvider webDriverProvider) {
        return new GroovyFluentWebDriverPage(webDriverProvider)
    }

}
