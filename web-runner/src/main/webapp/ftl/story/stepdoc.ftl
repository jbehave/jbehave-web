<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "storyRunner" "Story Runner"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/story/stepdoc.action">

		<#assign stepdocs = stepdocContext.stepdocs />		
		<#assign view = stepdocContext.view />
        <#if (stepdocs.size() > 0) >
        <div id="stepdocs">  
	 		 <fieldset>
	             <legend><@i.messageFor "stepdocs" "Stepdocs"/></legend>
                 <p><@i.messageFor "stepdocsDescription" "The following stepdocs are automatically generated from the registered Java Steps class"/></p>
                 <p><@i.messageFor "view" "View"/>
                   <@w.selectSingle "stepdocContext.view" w.asNameableValues(stepdocContext.getViews(),"name()","name()") stepdocContext.getView() 
                   "onchange=\"fireActionMethod('toggle');\"" />
                 </p>
                 <p>
                    <pre class="brush: plain">
			            <#list stepdocs as stepdoc>
	    		            ${stepdoc.annotation.simpleName} ${stepdoc.pattern}
							<#assign aliases = stepdoc.aliasPatterns />
							<#if (aliases.size() > 0) >
								  <#list aliases as alias>
									  ${alias}
								  </#list>
							 </#if>
							 <#if (view == 'METHODS') >
	   		 		            ${stepdoc.methodSignature}
	    		            </#if>
	            		</#list>
                    </pre>
                </p>
	         </fieldset>           
         </div>
		</#if>	
         
    </form>
</div>
</body>
</html>