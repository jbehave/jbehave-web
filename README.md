# JBehave-Web

JBehave is a collection of extension for JBehave that extend its capabilities in ways related to HTTP and the web.

<img src="http://jbehave.org/reference/preview/images/jbehave-logo.png" alt="JBehave logo" align="right" />

## Modules

1. Web-Selenium.  Bindings to Selenium 1.0 and 2.0 allowing JBehave operate on web-sites.
2. Web-Runner.  A web-app that allow the synchronous experimental running of stories for non-developers.
3. Web-Queue.  A Web-interface for the enqueing of stories to be run later.

## Using

Canonical information for JBehave:

1. [Documentation](http://jbehave.org/).
2. [User mailing list](http://jbehave.org/mailing-lists.html)

## Contributing and Developing

Please report issues, feature requests on JIRA [issue tracker](http://jbehave.org/issue-tracking.html) or discuss them on the
[dev mailing list](http://jbehave.org/mailing-lists.html). 

### Depended-on Technologies

JDK required: 1.7 (or above)
[Maven](http://maven.apache.org) required (3.0 or above).

### Encoding

Configure IDE to use UTF-8 for all files
Configure Maven by adding "-Dfile.encoding=UTF-8" to $MAVEN_OPTS 
 
### IDE Integration

Maven is supported in Intellij IDEA out-of-the-box 
Maven is supported in Eclipse out-of-the-box

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

See also the [jbehave-core](https://github.com/jbehave/jbehave-core) sister project for web extensions to JBehave, and [jbehave-tutorial](https://github.com/jbehave/jbehave-tutorial) for a decent example of JBehave testing of a web application.

## License

See LICENSE.txt in the source root (BSD).  