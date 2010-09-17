Scenario: User runs a single story

Given user is on home page
When user clicks on Run Story
Then text is shown: "Story Input"
When user runs story "Given a threshold of 10.0
    When the stock is traded at 5.0
    Then the alert status should be OFF
    When the stock is traded at 11.0
    Then the alert status should be ON"
Then run is successful
