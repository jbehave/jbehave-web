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

        <div id="actions">
           <fieldset>
                <legend><@i.messageFor "actions" "Actions"/></legend>
               	<p>
                    <a class="buttonDelete" onclick="fireActionMethod('delete');"><@i.messageFor "delete" "Delete"/></a>        
                </p>
            </fieldset>
        </div>

    </form>
</div>
</body>
</html>