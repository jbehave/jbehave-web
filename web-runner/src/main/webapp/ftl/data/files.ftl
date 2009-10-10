<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "dataFiles" "Data Files"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/data/files.action">
    
        <div id="files">
           <fieldset>
               <legend><@i.messageFor "dataFiles" "Data Files"/></legend>
			   <#assign files = controller.files />
		       <#if (files.size() > 0) >		       		
	           <table>
	             <#list files as file>	
	                <tr>
	                    <td>${file.absolutePath}</td>
	                    <td><@w.checkbox "selectedPaths" "${file.absolutePath}" /></td>
	                </tr>
	             </#list>
	           </table>
		       </#if>                   
            </fieldset>
        </div>

	    <#assign contentFiles = controller.contentFiles />
        <#if (contentFiles.size() > 0) >		       		
        <div id="contentFiles">
           <fieldset>
               <legend><@i.messageFor "contentFiles" "Content Files"/></legend>
	           <table>
	             <#list contentFiles as file>	
	                <tr>
	                    <td>${file.absolutePath}</td>
	                </tr>
	             </#list>
	           </table>
            </fieldset>
        </div>
		</#if>                   
 		
        <div id="actions">
           <fieldset>
                <legend><@i.messageFor "actions" "Actions"/></legend>
               	<p>
                    <a class="buttonDelete" onclick="fireActionMethod('delete');"><@i.messageFor "delete" "Delete"/></a>        
                    <a class="buttonDelete" onclick="fireActionMethod('listContent');"><@i.messageFor "listContent" "List Content"/></a>        
                    <a class="buttonDelete" onclick="fireActionMethod('hideContent');"><@i.messageFor "hideContent" "Hide Content"/></a>        
                </p>
            </fieldset>
        </div>

    </form>
</div>
</body>
</html>