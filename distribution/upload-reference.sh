
VERSION=$1

if [ "$VERSION" == "" ]; then
  echo "usage: upload-reference.sh <version>"
  exit;
fi

scp target/jbehave-web-$VERSION-bin.zip jbehave.org:

ssh jbehave.org "rm -r jbehave-web-$VERSION; unzip jbehave-web-$VERSION-bin.zip; rm -r /var/www/jbehave.org/reference/web/$VERSION;  mv jbehave-web-$VERSION/docs/ /var/www/jbehave.org/reference/web/$VERSION"
