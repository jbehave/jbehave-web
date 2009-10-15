<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<#import "/ftl/paths.ftl" as p>
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
	                    <td><@p.normalise file.path/></td>
	                    <td><@w.checkbox "selectedPaths" "${file.path}" /></td>
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
	             <#list contentFiles.keySet() as path>	
	             	<#assign files=contentFiles.get(path)>
	             	<#list files as file>	
		                <tr>
		                	<td>
		                	<#if (p.isViewable(file)) >
		                		<a class="buttonView" onclick="window.open('${base}/data/file.action?method=viewContent&selectedPath=<@p.normalise path/>/<@p.normalise file.path/>')"><@i.messageFor "viewContent" "View"/></a>
		                	</#if>
		                	</td>
		                	<td class="contentFilePath"><@p.normalise file.path/></td>
		                </tr>
	                </#list>
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
                    <a class="buttonDelete" onclick="fireActionMethod('showContent');"><@i.messageFor "showContent" "Show Content"/></a>        
                    <a class="buttonDelete" onclick="fireActionMethod('hideContent');"><@i.messageFor "hideContent" "Hide Content"/></a>        
                </p>
            </fieldset>
        </div>

    </form>
</div>
</body>
</html>