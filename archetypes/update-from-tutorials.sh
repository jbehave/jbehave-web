ARCHETYPES=`pwd`
TUTORIALS="$ARCHETYPES/../../jbehave-tutorial/etsy-selenium"

for NAME in "groovy-pico" "java-spring"
do
TUTORIAL="$TUTORIALS/$NAME"
cd $TUTORIAL
mvn archetype:create-from-project
ARCHETYPE="$ARCHETYPES/web-selenium-$NAME-archetype/"
cp -pr $TUTORIAL/target/generated-sources/archetype/src $ARCHETYPE
done 

cd $ARCHETYPES