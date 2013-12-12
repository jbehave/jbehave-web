package org.jbehave.web.runner.wicket;

public class WikiConfiguration implements WikiConfigurer {

	private String uri;
	private String username;
	private String password;
	
	public WikiConfiguration(String uri) {
		this(uri, null, null);
	}

	public WikiConfiguration(String uri, String username, String password) {
		this.uri = uri;
		this.username = username;
		this.password = password;		
	}

	public String getURI() {
		return uri;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}