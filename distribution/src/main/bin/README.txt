Deploy script of JBehave Webapp

1. Edit /webapp/WEB-INF/web.xml and configure the class name of the JBehaveRegistrar that registers the 
Steps instance used by the webapp.  E.g. if your Steps class is called MyOwnSteps, then the corresponding 
MyOwnRegistrar will look like 

public class MyOwnRegistrar extends JBehaveRegistrar {

	public TraderRegistrar(Registrar delegate) {
		super(delegate);
	}

	@Override
	protected void registerSteps() {
		registerInstance(new MyOwnSteps());
	}
	
}


2. Copy to /webapp/WEB-INF/lib any jars required by the user-defined MyOwnRegistrar

3. Run deploy Ant script: 

ant -f deploy.xml deploy:webapp 

By default, it will deploy to $JETTY_HOME/webapps, but any deploy dir can be configured for the war.

