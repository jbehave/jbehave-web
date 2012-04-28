ARCHETYPES=`pwd`
TUTORIALS="$ARCHETYPES/../../jbehave-tutorial/etsy-selenium"

for NAME in "groovy-pico" "java-spring"
do
TUTORIAL="$TUTORIALS/$NAME"
cd $TUTORIAL
mvn clean archetype:create-from-project
ARCHETYPE="$ARCHETYPES/web-selenium-$NAME-archetype/"
GENERATED="$TUTORIAL/target/generated-sources/archetype"
perl -p -i -e 's/<fileSets>/<requiredProperties>\n<requiredProperty key="jbehaveCoreVersion">\n<defaultValue>\${jbehave.core.version}<\/defaultValue>\n<\/requiredProperty>\n<requiredProperty key="jbehaveWebVersion">\n<defaultValue>\${jbehave.web.version}<\/defaultValue>\n<\/requiredProperty>\n<requiredProperty key="jbehaveSiteVersion">\n<defaultValue>\${jbehave.site.version}<\/defaultValue>\n<\/requiredProperty>\n<\/requiredProperties>\n<fileSets>/g' $GENERATED/src/main/resources/META-INF/maven/archetype-metadata.xml 
perl -p -i -e 's/<jbehave.core.version>(.*)<\/jbehave.core.version>/<jbehave.core.version>\$jbehaveCoreVersion<\/jbehave.core.version>/g' $GENERATED/src/main/resources/archetype-resources/pom.xml
perl -p -i -e 's/<jbehave.web.version>(.*)<\/jbehave.web.version>/<jbehave.web.version>\$jbehaveWebVersion<\/jbehave.web.version>/g' $GENERATED/src/main/resources/archetype-resources/pom.xml 
perl -p -i -e 's/<jbehave.site.version>(.*)<\/jbehave.site.version>/<jbehave.site.version>\$jbehaveSiteVersion<\/jbehave.site.version>/g' $GENERATED/src/main/resources/archetype-resources/pom.xml 
cp -pr $GENERATED/src $ARCHETYPE
done 

cd $ARCHETYPES