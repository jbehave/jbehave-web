
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
When user changes label to 'A new label'
Then label is 'A new label'
