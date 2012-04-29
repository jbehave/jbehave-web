#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.etsy;

import java.util.Arrays;

public class EtsyStepdocs extends EtsyDotComStories {

    @Override
    public void run() throws Throwable {
        configuredEmbedder().reportStepdocsAsEmbeddables(Arrays.asList(EtsyDotComStories.class.getName()));
    }

}
