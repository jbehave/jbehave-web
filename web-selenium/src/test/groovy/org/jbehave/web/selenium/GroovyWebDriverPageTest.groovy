package org.jbehave.web.selenium

import com.github.tanob.groobe.GrooBe
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import static org.mockito.Mockito.mock

public class GroovyWebDriverPageTest {

    private WebDriverProvider provider = mock(WebDriverProvider.class)
    private WebDriverPage page = makeGroovyWebDriverPage(provider)

    protected WebDriverPage makeGroovyWebDriverPage(WebDriverProvider webDriverProvider) {
        return new GroovyWebDriverPage(webDriverProvider)
    }

    private def exceptions = new Class<? extends RuntimeException>[1]
    def FOO_ID

    @Before
    def void before() {
        GrooBe.activate()
    }

    @Test
    def void shouldWaitTheRightishAmountOfTimeBeforeTimingOut() {

        StringBuilder sb = new StringBuilder();
        exceptions[0] = FooException.class

        FOO_ID = By.id("foo")
        try {
            page.waitFor(3, 995, exceptions) {
                sb.append(".")
                throw new FooException()
            }
        } catch (TimeoutException te) {
            def message = te.getMessage()
            sb.append(message.subSequence(0, message.indexOf(" seconds")))
            te.getCause().getMessage().shouldBe "foo :-("
        }
        sb.toString().shouldEndWith "...Timed out after 3"
    }

    @Test
    def void shouldWaitForNoSuchElementExceptionsAutomatically() {

        StringBuilder sb = new StringBuilder();
        exceptions[0] = FooException.class

        FOO_ID = By.id("foo")
        try {
            page.waitFor(3, 995, exceptions) {
                sb.append(".")
                throw new org.openqa.selenium.NoSuchElementException("oops")
            }
        } catch (TimeoutException te) {
            def message = te.getMessage()
            sb.append(message.subSequence(0, message.indexOf(" seconds")))
            te.getCause().getMessage().shouldStartWith "oops"
        }
        sb.toString().shouldEndWith "...Timed out after 3"

    }

    @Test
    def void shouldFastFailIfUnexpectedException() {

        StringBuilder sb = new StringBuilder();
        exceptions[0] = FooException.class

        try {
            page.waitFor(3, 995, exceptions) {
                sb.append(".")
                throw new NullPointerException()
            }
        } catch (NullPointerException npe) {
            sb.append("NPE")
        }

       sb.toString().shouldBe ".NPE"

    }

    class FooException extends RuntimeException {
        @Override
        String getMessage() {
            return "foo :-("
        }

    }

}