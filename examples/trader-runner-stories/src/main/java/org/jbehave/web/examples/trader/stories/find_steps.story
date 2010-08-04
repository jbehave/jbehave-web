Scenario: User searches for steps

Given user is on home page
When user clicks on Find Steps
Then text is shown: "Patterns and methods matching the textual step"
When user searches for "Given a threshold of 10.0"
Then search returns: "Given a threshold of $threshold"