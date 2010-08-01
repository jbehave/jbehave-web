To deploy:

- Using Maven with embedded Jetty

mvn jetty:run-war

- Using Maven with a webapp container (e.g. Jetty, Tomcat, etc ...)

mvn clean install
Copy target/trader-runner.war to container deploy dir and start container.

To use: 

Open webapp page at

http://localhost:8080/trader-runner (assuming normal port conventions)

Click on Run Story link in navigation menu.

Input textual story in textarea and click on Run Story link.

Examples of trader story to input (you can cut & paste) can be found in src/main/resources/
Or Copy/paste the following in:-

   Given a stock of prices 0.5,1.0 and a threshold of 10.0
   When the stock is traded at 5.0
   Then the alert status should be OFF
   When the stock is traded at 11.0
   Then the alert status should be ON

Note: src/main/webapp only contains the custom resources that override the default ones contained in 
jbehave-web-runner.  So, e.g. to change override default style, only src/main/webapp/style/jbehave.css needs
to be added. Similary, to change home page template, only src/main/webapp/ftl/home.ftl is needed.  If any of 
these are removed the defaults will apply.

