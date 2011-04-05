# JBehave-Web

JBehave is a collection of extension for JBehave that extend its capabilities in ways related to HTTP and the web.

<img src="http://jbehave.org/reference/preview/images/jbehave-logo.png" alt="JBehave logo" align="right" />

## Modules

1. Web-Selenium.  Bindings to Selenium 1.0 and 2.0 allowing JBehave operate on web-sites.
2. Web-Runner.  A web-app that allow the synchronous experimental running of stories for non-developers.
3. Web-Queue.  A Web-interface for the enqueing of stories to be run later.

## Using

Canonical information for JBehave:

1. [News](http://jbehave.org).
2. [Documentation](http://jbehave.org/documentation/).
3. [User mail-list](http://xircles.codehaus.org/lists/user@jbehave.codehaus.org)
4. Jars in [Maven Repositories](http://mvnrepository.com/search.html?query=jbehave)

## Contributing and Developing

Please report issues, feature requests on the Codehaus [issue
tracker](http://jira.codehaus.org/browse/JBEHAVE) or discuss them on the
[dev mail-list](http://xircles.codehaus.org/lists/dev@jbehave.codehaus.org). 

Keep an eye on the  [Bamboo Continuous Integration](http://builds.codehaus.org/browse/JBEHAVE) server for JBehave builds.

### Depended-on Technologies

JDK required: 5.0 (or above)
[Maven](http://maven.apache.org) required (2.2.1 or above).

### Encoding

Configure IDE to use UTF-8 for all files
Configure Maven by adding "-Dfile.encoding=UTF-8" to $MAVEN_OPTS 
 
### IDE Integration

Maven is supported in Intellij IDEA out-of-the-box 
Maven is supported in Eclipse via [m2eclipse plugin](http://m2eclipse.sonatype.org/)

### Building

A regular Maven build will suffice:

    mvn install

### Maven Build Profiles

- default: builds all releasable modules
- examples: builds all headless examples

#### Maven Build Profiles used during release cycle

- reporting: builds reports
- distribution: builds distribution (documentation)

Note:  profiles are additive and the default profile is always active.

### Example Profile Usages

#### Build Core and all Examples

    mvn install -Pexamples

#### Build with Reporting and Distribution

    mvn install -Preporting,distribution 

#### Building a Release with Maven

    mvn release:prepare -Preporting,distribution 
    mvn release:perform -Preporting,distribution

## Related

See also the [jbehave-core](jbehave-core) sister project for web extensions to JBehave, and [jbehave-tutorial](jbehave-tutorial) for a decent example of JBehave testing of a web application.

## License

See LICENSE.txt in the source root (BSD).  