<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "storyRunner" "Story Runner"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/story/story.action" method="${storyContext.method!"POST"}">
    
        <#include "/ftl/errors.ftl" parse="true">
        <div id="storyInput">
           <fieldset>
                <legend><@i.messageFor "storyInput" "Story Input"/></legend>
                <p>
                	<@w.textarea "storyContext.input" "${storyContext.input}" "rows='25' cols='100'"/>
                </p>
 				<p><a href="javascript:fireActionMethod('run');"><@i.messageFor "runStory" "Run Story"/></a> (Method 
 				   <@w.selectSingle "storyContext.method" w.asNameableValues(storyContext.getMethods(),"name()","name()") storyContext.getMethod() 
                   "onchange=\"fireActionMethod('show');\"" />)				
 				</p> 
            </fieldset>
        </div>

        <#if storyContext.output?? >
        <div id="storyOutput">    
           <fieldset>
                <legend><@i.messageFor "storyOutput" "Story Output"/></legend>
                <p>
                    <pre class="brush: plain">
                    	${storyContext.output}
                    </pre>
                </p>
            </fieldset>
         </div>        
         </#if>        
                    
		<#assign failureMessages = storyContext.failureMessages />
        <#if (failureMessages.size() > 0) >
        <div id="storyfailureMessages">  
	 		 <fieldset>
	             <legend><@i.messageFor "storyfailureMessages" "Story Failure Messages"/></legend>
				 <p>
                    <pre class="brush: plain">
			            <#list failureMessages as message>
	    		            ${message}
	            		</#list>
                    </pre>
                </p>
	         </fieldset>           
         </div>
		</#if>
		
		<#assign failureStackTrace = storyContext.failureStackTrace />
        <#if (failureStackTrace.length() > 0) >
        <div id="failureStackTrace">  
	 		 <fieldset>
	             <legend><@i.messageFor "failureStackTrace" "Failure Stack Trace"/></legend>
				<p>
					<pre class="brush: java; gutter: false; collapse: true">
                    	${failureStackTrace}
                    </pre>
				</p>
	         </fieldset>           
         </div>
		</#if>

         
    </form>
</div>
</body>
</html>