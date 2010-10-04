Scenario: User searches for a single step

Given user is on Home page
When user opens Find Steps page
Then Find Steps page is shown
When user searches for "Given a threshold of 10.0"
Then search returns: "Given a threshold of $threshold"
When user views with methods
Then search returns: "Given a threshold of $threshold,
    [org.jbehave.web.examples.trader.steps.TraderSteps.aThreshold(double)]"
And steps instances include: "TraderSteps,StockExchangeSteps"

Scenario: User searches and sorts all steps

Given user is on Home page
When user opens Find Steps page
Then Find Steps page is shown
When user searches for all steps
Then search returns: "Given a threshold of $threshold,
    Given a limit of $threshold,
    When the stock is traded at $price,
    When a trading step fails,
    When a step with <markup>,
    Then the alert status should be $status,
    Then the trader sells all stocks,
    Then the trader liquidates stocks,
    Given a resource root directory $rootDirectory,
    Given a stock exchange $stockExchange,
    When the stock exchange is opened,
    Then the stock exchanges opened are as contained in $resource"
When user sorts by pattern
Then search returns: "Given a limit of $threshold,
    Given a resource root directory $rootDirectory,
    Given a stock exchange $stockExchange,
    Given a threshold of $threshold,
    When a step with <markup>,
    When a trading step fails,
    When the stock exchange is opened,
    When the stock is traded at $price,
    Then the alert status should be $status,
    Then the stock exchanges opened are as contained in $resource,
    Then the trader liquidates stocks,
    Then the trader sells all stocks"
And steps instances include: "TraderSteps,StockExchangeSteps"