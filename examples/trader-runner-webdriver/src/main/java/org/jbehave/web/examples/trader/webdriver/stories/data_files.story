Scenario: User uploads data files

Given user is on Home page
When user opens Data Files page
Then Data Files page is shown 
When user uploads file src/main/resources/archive.zip
Then file archive.zip is uploaded