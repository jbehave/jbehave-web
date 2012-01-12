# JBehave Web Runner Trader Example

## To deploy:

1. Using Maven with embedded Jetty

mvn jetty:run-war -Djbehave.webrunner.version=x.y.z

2. Using Maven with a webapp container (e.g. Jetty, Tomcat, etc ...)

mvn clean install -Djbehave.webrunner.version=x.y.z
Copy target/trader-runner.war to container deploy dir and start container.

## Using Web Runner

### Run Story

The Run Story page allows the user to run synchronously any textual story, waiting for it to complete, and
displaying the result in the same page.

1. From Menu, click on <b>Run Story</b>, which will take you to a
    page where you can enter your textual story.
2. Input textual story in textarea (e.g you can cut & paste
    the sample scenarios found in the https://github.com/jbehave/jbehave-web/blob/master/examples/trader-runner/src/main/resources/trader.story)
        and click on <b>Run</b> button.
3. Optionally, a meta filter can be specified.
4. Upon execution, the output of the story is shown below. By
    default the same output is shown as the one you would see when running
    JBehave via a command-line interface. This includes any details of
    failed and pending steps. If any failures occur, the user has the stack
    trace in an expandable section below.

### Submit Story

The Submit Story page allows the user to run asynchronously any textual story.  The result can be then viewed (once completed) in the View Story page.

1. From Menu, click on <b>Submit Story</b>, which will take you to a
    page where you can enter your textual story.
2. Input textual story in textarea (e.g you can cut & paste
    the sample scenarios found in the https://github.com/jbehave/jbehave-web/blob/master/examples/trader-runner/src/main/resources/trader.story)
        and click on <b>Submit</b> button.
3. Optionally, a meta filter can be specified.
4. Upon execution, a story path (a timestamped id) is generated and an clickable link is provided to the View Story page.

### View Story

The View Story page allows the user to view all the stories run (synchronously or asynchronously) and view the output as text.

1. From Menu, click on <b>View Story</b>, which will take you to a
    page where all the stories run as displayed.
2. The columns of the table are sortable by clicking on the header links.
3. Each story output can be viewed as text by clicking on the corresponding 'txt' link.

### Explore Steps

The Find Steps page allows the user to explore available steps pattern by auto-completion and to display the patterns 
in a drop-down list.

1. From Menu, click on <b>Find Steps</b>, which will take you to
    a page where you have an <b>Explore</b> panel.
2. Start typing in the input field (e.g you can type "Given" to display all the Given type step patterns).
3. The auto-completed list will include any step pattern that matches in part the input text.

### Match Steps

The Find Steps page allows the user to find methods in steps classes that match a given complete textual step and 
to display them.

1. From Menu, click on <b>Find Steps</b>, which will take you to a page where you can enter your complete textual step.
2. Input the complete textual step in textarea (e.g you can cut & paste a
    step from the sample scenarios found in the
    https://github.com/jbehave/jbehave-web/blob/master/examples/trader-runner/src/main/resources/trader.story)
       and click on <b>Find</b> button.
3. A blank input will simply show all the methods that are configured.
4. Upon execution, the steps found are shown below. By default,
    only the patterns are shown, but you can choose to also display
    associated methods.
5. The final section shows the steps instances that were
    configured and were used to find the steps.

### Data Files

The Data Files page allows the user to upload and view any data
file that may be used during the execution of the story. Data files can
be either single text files or zip archives. If archives, these are
automatically unzipped when upload to the server.

1. From Menu, click on <b>Data Files</b>, which will take you to
    a page where you can view the files already uploaded to the upload
    directory (defaults to "/tmp/upload" but is configurable via the WebRunnerApplication#uploadDirectory())
2. Use the <b>Browse</b> and <b>Upload</b> buttons to upload any
    file to the upload directory.
3. Use the <b>Show Content</b> and <b>Hide Content</b> buttons to
    toggle display of uploaded file content.
4. Click on <b>View</b> link next to the files to view their
    content.
5. Use the <b>Delete</b> button to delete any selected files.

### Styling

src/main/webapp only contains the custom resources that override the default ones contained in 
jbehave-web-runner.  So, e.g. to change override default style, only src/main/webapp/style/jbehave.css needs
to be added.


