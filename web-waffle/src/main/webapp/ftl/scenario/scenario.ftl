<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "scenarioRunner" "Scenario Runner"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/scenario/scenario.action">
    
        <#include "/ftl/errors.ftl" parse="true">
        <div id="scenarioInput">
           <fieldset>
                <legend><@i.messageFor "scenarioInput" "Scenario Input"/></legend>
                <p>
                	<@w.textarea "scenarioContext.input" "${scenarioContext.input}" "rows='25' cols='100'"/>
                </p>
 				<p><a href="javascript:fireActionMethod('run');"><@i.messageFor "runScenario" "Run Scenario"/></a></p> 
            </fieldset>
        </div>

        <#if scenarioContext.output?? >
        <div id="scenarioOutput">    
           <fieldset>
                <legend><@i.messageFor "scenarioOutput" "Scenario Output"/></legend>
                <p>
                    <pre class="brush: plain">
                    	${scenarioContext.output}
                    </pre>
                </p>
            </fieldset>
         </div>        
         </#if>        
                    
		<#assign failureMessages = scenarioContext.failureMessages />
        <#if (failureMessages.size() > 0) >
        <div id="scenariofailureMessages">  
	 		 <fieldset>
	             <legend><@i.messageFor "scenariofailureMessages" "Scenario Failure Messages"/></legend>
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
		
		<#assign failureStackTrace = scenarioContext.failureStackTrace />
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