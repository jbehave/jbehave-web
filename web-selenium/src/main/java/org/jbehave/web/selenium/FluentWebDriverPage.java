package org.jbehave.web.selenium;

import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.FluentSelect;
import org.seleniumhq.selenium.fluent.FluentSelects;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.Period;
import org.seleniumhq.selenium.fluent.TestableString;
import org.seleniumhq.selenium.fluent.internal.NegatingFluentWebDriver;

public abstract class FluentWebDriverPage extends WebDriverPage {

    private ThreadLocal<FluentWebDriver> fluentWebDriver = new ThreadLocal<FluentWebDriver>();

    public FluentWebDriverPage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    private FluentWebDriver fluentWebDriver() {
        if (fluentWebDriver.get() == null) {
            fluentWebDriver.set(makeFluentWebDriver());
        }
        return fluentWebDriver.get();
    }

    protected FluentWebDriver makeFluentWebDriver() {
        return new FluentWebDriver(getDriverProvider().get());
    }

    public FluentWebElement span() {
        return fluentWebDriver().span();
    }

    public FluentWebElement span(By by) {
        return fluentWebDriver().span(by);
    }

    public FluentWebElements spans() {
        return fluentWebDriver().spans();
    }

    public FluentWebElements spans(By by) {
        return fluentWebDriver().spans(by);
    }

    public FluentWebElement div() {
        return fluentWebDriver().div();
    }

    public FluentWebElement div(By by) {
        return fluentWebDriver().div(by);
    }

    public FluentWebElements divs() {
        return fluentWebDriver().divs();
    }

    public FluentWebElements divs(By by) {
        return fluentWebDriver().divs(by);
    }

    public FluentWebElement button() {
        return fluentWebDriver().button();
    }

    public FluentWebElement button(By by) {
        return fluentWebDriver().button(by);
    }

    public FluentWebElements buttons() {
        return fluentWebDriver().buttons();
    }

    public FluentWebElements buttons(By by) {
        return fluentWebDriver().buttons(by);
    }

    public FluentWebElement link() {
        return fluentWebDriver().link();
    }

    public FluentWebElement link(By by) {
        return fluentWebDriver().link(by);
    }

    public FluentWebElements links() {
        return fluentWebDriver().links();
    }

    public FluentWebElements links(By by) {
        return fluentWebDriver().links(by);
    }

    public FluentWebElement input() {
        return fluentWebDriver().input();
    }

    public FluentWebElement input(By by) {
        return fluentWebDriver().input(by);
    }

    public FluentWebElements inputs() {
        return fluentWebDriver().inputs();
    }

    public FluentWebElements inputs(By by) {
        return fluentWebDriver().inputs(by);
    }

    public FluentSelect select() {
        return fluentWebDriver().select();
    }

    public FluentSelect select(By by) {
        return fluentWebDriver().select(by);
    }

    public FluentSelects selects() {
        return fluentWebDriver().selects();
    }

    public FluentSelects selects(By by) {
        return fluentWebDriver().selects(by);
    }

    public FluentWebElement li() {
        return fluentWebDriver().li();
    }

    public FluentWebElement li(By by) {
        return fluentWebDriver().li(by);
    }

    public FluentWebElements lis() {
        return fluentWebDriver().lis();
    }

    public FluentWebElements lis(By by) {
        return fluentWebDriver().lis(by);
    }

    public FluentWebElement h1() {
        return fluentWebDriver().h1();
    }

    public FluentWebElement h1(By by) {
        return fluentWebDriver().h1(by);
    }

    public FluentWebElements h1s() {
        return fluentWebDriver().h1s();
    }

    public FluentWebElements h1s(By by) {
        return fluentWebDriver().h1s(by);
    }

    public FluentWebElement h2() {
        return fluentWebDriver().h2();
    }

    public FluentWebElement h2(By by) {
        return fluentWebDriver().h2(by);
    }

    public FluentWebElements h2s() {
        return fluentWebDriver().h2s();
    }

    public FluentWebElements h2s(By by) {
        return fluentWebDriver().h2s(by);
    }

    public FluentWebElement h3() {
        return fluentWebDriver().h3();
    }

    public FluentWebElement h3(By by) {
        return fluentWebDriver().h3(by);
    }

    public FluentWebElements h3s() {
        return fluentWebDriver().h3s();
    }

    public FluentWebElements h3s(By by) {
        return fluentWebDriver().h3s(by);
    }

    public FluentWebElement h4() {
        return fluentWebDriver().h4();
    }

    public FluentWebElement h4(By by) {
        return fluentWebDriver().h4(by);
    }

    public FluentWebElements h4s() {
        return fluentWebDriver().h4s();
    }

    public FluentWebElements h4s(By by) {
        return fluentWebDriver().h4s(by);
    }

    public FluentWebElement p() {
        return fluentWebDriver().p();
    }

    public FluentWebElement p(By by) {
        return fluentWebDriver().p(by);
    }

    public FluentWebElements ps() {
        return fluentWebDriver().ps();
    }

    public FluentWebElements ps(By by) {
        return fluentWebDriver().ps(by);
    }

    public FluentWebElement img() {
        return fluentWebDriver().img();
    }

    public FluentWebElement img(By by) {
        return fluentWebDriver().img(by);
    }

    public FluentWebElements imgs() {
        return fluentWebDriver().imgs();
    }

    public FluentWebElements imgs(By by) {
        return fluentWebDriver().imgs(by);
    }

    public FluentWebElement table() {
        return fluentWebDriver().table();
    }

    public FluentWebElement table(By by) {
        return fluentWebDriver().table(by);
    }

    public FluentWebElements tables() {
        return fluentWebDriver().tables();
    }

    public FluentWebElements tables(By by) {
        return fluentWebDriver().tables(by);
    }

    public FluentWebElement fieldset() {
        return fluentWebDriver().fieldset();
    }

    public FluentWebElements fieldsets() {
        return fluentWebDriver().fieldsets();
    }

    public FluentWebElement fieldset(By by) {
        return fluentWebDriver().fieldset(by);
    }

    public FluentWebElements fieldsets(By by) {
        return fluentWebDriver().fieldsets(by);
    }

    public FluentWebElement legend() {
        return fluentWebDriver().legend();
    }

    public FluentWebElements legends() {
        return fluentWebDriver().legends();
    }

    public FluentWebElement legend(By by) {
        return fluentWebDriver().legend(by);
    }

    public FluentWebElements legends(By by) {
        return fluentWebDriver().legends(by);
    }

    public FluentWebElement tr() {
        return fluentWebDriver().tr();
    }

    public FluentWebElement tr(By by) {
        return fluentWebDriver().tr(by);
    }

    public FluentWebElements trs() {
        return fluentWebDriver().trs();
    }

    public FluentWebElements trs(By by) {
        return fluentWebDriver().trs(by);
    }

    public FluentWebElement td() {
        return fluentWebDriver().td();
    }

    public FluentWebElement td(By by) {
        return fluentWebDriver().td(by);
    }

    public FluentWebElements tds() {
        return fluentWebDriver().tds();
    }

    public FluentWebElements tds(By by) {
        return fluentWebDriver().tds(by);
    }

    public FluentWebElement th() {
        return fluentWebDriver().th();
    }

    public FluentWebElement th(By by) {
        return fluentWebDriver().th(by);
    }

    public FluentWebElements ths() {
        return fluentWebDriver().ths();
    }

    public FluentWebElements ths(By by) {
        return fluentWebDriver().ths(by);
    }

    public FluentWebElement ul() {
        return fluentWebDriver().ul();
    }

    public FluentWebElement ul(By by) {
        return fluentWebDriver().ul(by);
    }

    public FluentWebElements uls() {
        return fluentWebDriver().uls();
    }

    public FluentWebElements uls(By by) {
        return fluentWebDriver().uls(by);
    }

    public FluentWebElement ol() {
        return fluentWebDriver().ol();
    }

    public FluentWebElement ol(By by) {
        return fluentWebDriver().ol(by);
    }

    public FluentWebElements ols() {
        return fluentWebDriver().ols();
    }

    public FluentWebElements ols(By by) {
        return fluentWebDriver().ols(by);
    }

    public FluentWebElement form() {
        return fluentWebDriver().form();
    }

    public FluentWebElement form(By by) {
        return fluentWebDriver().form(by);
    }

    public FluentWebElements forms() {
        return fluentWebDriver().forms();
    }

    public FluentWebElements forms(By by) {
        return fluentWebDriver().forms(by);
    }

    public FluentWebElement textarea() {
        return fluentWebDriver().textarea();
    }

    public FluentWebElement textarea(By by) {
        return fluentWebDriver().textarea(by);
    }

    public FluentWebElements textareas() {
        return fluentWebDriver().textareas();
    }

    public FluentWebElements textareas(By by) {
        return fluentWebDriver().textareas(by);
    }

    public FluentWebElement option() {
        return fluentWebDriver().option();
    }

    public FluentWebElement option(By by) {
        return fluentWebDriver().option(by);
    }

    public FluentWebElements options() {
        return fluentWebDriver().options();
    }

    public FluentWebElements options(By by) {
        return fluentWebDriver().options(by);
    }

    public FluentWebElement map() {
        return fluentWebDriver().map();
    }

    public FluentWebElements maps() {
        return fluentWebDriver().maps();
    }

    public FluentWebElement map(By by) {
        return fluentWebDriver().map(by);
    }

    public FluentWebElements maps(By by) {
        return fluentWebDriver().maps(by);
    }

    public FluentWebElement address() {
        return fluentWebDriver().address();
    }

    public FluentWebElements addresses() {
        return fluentWebDriver().addresses();
    }

    public FluentWebElement address(By by) {
        return fluentWebDriver().address(by);
    }

    public FluentWebElements addresses(By by) {
        return fluentWebDriver().addresses(by);
    }

    public FluentWebElement header() {
        return fluentWebDriver().header();
    }

    public FluentWebElements headers() {
        return fluentWebDriver().headers();
    }

    public FluentWebElement header(By by) {
        return fluentWebDriver().header(by);
    }

    public FluentWebElements headers(By by) {
        return fluentWebDriver().headers(by);
    }

    public FluentWebElement footer() {
        return fluentWebDriver().footer();
    }

    public FluentWebElements footers() {
        return fluentWebDriver().footers();
    }

    public FluentWebElement footer(By by) {
        return fluentWebDriver().footer(by);
    }

    public FluentWebElements footers(By by) {
        return fluentWebDriver().footers(by);
    }

    public FluentWebElement b() {
        return fluentWebDriver().b();
    }

    public FluentWebElements bs() {
        return fluentWebDriver().bs();
    }

    public FluentWebElement b(By by) {
        return fluentWebDriver().b(by);
    }

    public FluentWebElements bs(By by) {
        return fluentWebDriver().bs(by);
    }

    public FluentWebElement object() {
        return fluentWebDriver().object();
    }

    public FluentWebElements objects() {
        return fluentWebDriver().objects();
    }

    public FluentWebElement object(By by) {
        return fluentWebDriver().object(by);
    }

    public FluentWebElements objects(By by) {
        return fluentWebDriver().objects(by);
    }

    public FluentWebElement pre() {
        return fluentWebDriver().pre();
    }

    public FluentWebElements pres() {
        return fluentWebDriver().pres();
    }

    public FluentWebElement pre(By by) {
        return fluentWebDriver().pre(by);
    }

    public FluentWebElements pres(By by) {
        return fluentWebDriver().pres(by);
    }

    public FluentWebElement figure() {
        return fluentWebDriver().figure();
    }

    public FluentWebElements figures() {
        return fluentWebDriver().figures();
    }

    public FluentWebElement figure(By by) {
        return fluentWebDriver().figure(by);
    }

    public FluentWebElements figures(By by) {
        return fluentWebDriver().figures(by);
    }

    public FluentWebElement acronym() {
        return fluentWebDriver().acronym();
    }

    public FluentWebElements acronyms() {
        return fluentWebDriver().acronyms();
    }

    public FluentWebElement acronym(By by) {
        return fluentWebDriver().acronym(by);
    }

    public FluentWebElements acronyms(By by) {
        return fluentWebDriver().acronyms(by);
    }

    public FluentWebElement abbr() {
        return fluentWebDriver().abbr();
    }

    public FluentWebElements abbrs() {
        return fluentWebDriver().abbrs();
    }

    public FluentWebElement abbr(By by) {
        return fluentWebDriver().abbr(by);
    }

    public FluentWebElements abbrs(By by) {
        return fluentWebDriver().abbrs(by);
    }

    public FluentWebElement blockquote() {
        return fluentWebDriver().blockquote();
    }

    public FluentWebElements blockquotes() {
        return fluentWebDriver().blockquotes();
    }

    public FluentWebElement blockquote(By by) {
        return fluentWebDriver().blockquote(by);
    }

    public FluentWebElements blockquotes(By by) {
        return fluentWebDriver().blockquotes(by);
    }

    public FluentWebElement area() {
        return fluentWebDriver().area();
    }

    public FluentWebElements areas() {
        return fluentWebDriver().areas();
    }

    public FluentWebElement area(By by) {
        return fluentWebDriver().area(by);
    }

    public FluentWebElements areas(By by) {
        return fluentWebDriver().areas(by);
    }

    public FluentWebElement label() {
        return fluentWebDriver().label();
    }

    public FluentWebElements labels() {
        return fluentWebDriver().labels();
    }

    public FluentWebElement label(By by) {
        return fluentWebDriver().label(by);
    }

    public FluentWebElements labels(By by) {
        return fluentWebDriver().labels(by);
    }

    public FluentWebElement nav() {
        return fluentWebDriver().nav();
    }

    public FluentWebElements navs() {
        return fluentWebDriver().navs();
    }

    public FluentWebElement nav(By by) {
        return fluentWebDriver().nav(by);
    }

    public FluentWebElements navs(By by) {
        return fluentWebDriver().navs(by);
    }

    public FluentWebElement tbody() {
        return fluentWebDriver().tbody();
    }

    public FluentWebElements tbodys() {
        return fluentWebDriver().tbodys();
    }

    public FluentWebElement tbody(By by) {
        return fluentWebDriver().tbody(by);
    }

    public FluentWebElements tbodys(By by) {
        return fluentWebDriver().tbodys(by);
    }

    public FluentWebDriver within(Period period) {
        return fluentWebDriver().within(period);
    }

    public NegatingFluentWebDriver without(Period period) {
        return fluentWebDriver().without(period);
    }

    public TestableString url() {
        return fluentWebDriver().url();
    }

    public TestableString title() {
        return fluentWebDriver().title();
    }

    public FluentWebDriver.BooleanResultsAdapter has() {
        return fluentWebDriver().has();
    }

    public FluentWebDriver.BooleanResultsAdapter hasMissing() {
        return fluentWebDriver().hasMissing();
    }

}
