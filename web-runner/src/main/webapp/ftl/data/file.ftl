<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "fileContent" "File Content"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">

        <div id="fileContent">    
           <fieldset>
                <legend><@i.messageFor "fileContent" "File Content"/></legend>
                <p><b>${selectedPath}</b></p>
			    <#if controller.fileContent?? >
			    	<#assign type="plain">
	           		<#if (selectedPath?ends_with("java"))>	           		
			    		<#assign type="java">
			        <#elseif (selectedPath?ends_with("xml"))>
			    		<#assign type="xml">
	           		</#if>
	            	<pre class="brush: ${type}">${controller.fileContent}</pre>
               <#else>
      		   		<p><@i.messageFor "fileContentNotFound" "File content not found"/></legend></p>
	           </#if>
            </fieldset>
         </div>        

</div>
</body>
</html>