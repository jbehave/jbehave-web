Scenario:  We want show how the behaviour verification of a Flash app can be automated

Given a flash app
Then title is 'Clicking Colors'
And color is GREEN
And label is '(Click here)'
When user clicks on square
Then color is BLUE
And label is 'BLUE'
When user clicks on square
Then color is RED
And label is 'RED'
When user changes label to 'Done'
Then color is RED
And label is 'Done'
