Deploy script of JBehave Web Runner

1. Edit /webapp/WEB-INF/web.xml and configure the applicationClassName of your WebRunnerApplication.  
Refer to docs/customising-web-runner.html.

2. Copy to /webapp/WEB-INF/lib any jars required by the user-defined WebRunnerApplication.

3. Run deploy Ant script: 

ant -f deploy.xml deploy:webapp 

By default, it will deploy to $JETTY_HOME/webapps, but any deploy dir can be configured for the war.

