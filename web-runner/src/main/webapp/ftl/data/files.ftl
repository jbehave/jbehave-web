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
					<#assign path=p.normalise(file.path)>	             
	                <tr>
	                    <td>${path}</td>
	                    <td><@w.checkbox "selectedPaths" "${path}" /></td>
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
		                	    <#assign selectedPath=p.normalise(path)+"/"+p.normalise(file.path)>
		                		<a class="buttonView" onclick="window.open('${base}/data/file.action?method=viewContent&selectedPath=${selectedPath}')"><@i.messageFor "viewContent" "View"/></a>
		                	</#if>
		                	</td>
		                	<#assign relativePath=p.normalise(file.path)>
		                	<td class="contentFilePath">${relativePath}</td>
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