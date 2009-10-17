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
        
        <#assign view = filesContext.view />
        <div id="files">
           <fieldset>
               <legend><@i.messageFor "dataFiles" "Data Files"/></legend>
			   <#assign files = filesContext.files />
		       <#if (files.size() > 0) >		       		
			   <p><@i.messageFor "view" "View"/>
                   <@w.selectSingle "filesContext.view" w.asNameableValues(filesContext.getViews(),"name()","name()") filesContext.getView() 
                   "onchange=\"fireActionMethod('showContent');\"" />
               </p>
	           <table>
	             <#list files as file>	
					<#assign path=p.unixPath(file.path)>	             
	                <tr>
	                    <td>${path}</td>
	                    <td><@w.checkbox "filesContext.selectedPaths" "${path}" /></td>
	                </tr>
	             </#list>
	           </table>
		       </#if>                   
            </fieldset>
        </div>

	    <#assign contentFiles = filesContext.contentFiles />
        <#if (contentFiles.size() > 0) >		       		
        <div id="contentFiles">
           <fieldset>
               <legend><@i.messageFor "contentFiles" "Content Files"/></legend>
	           <table>
	             <#list contentFiles.keySet() as directoryPath>	
	             	<#assign files=contentFiles.get(directoryPath)>
	             	<#list files as file>	
		                <tr>
		                	<td>
		                	<#if (p.isViewable(file)) >
		                	    <#assign selectedPath=p.urlPath(view, file.path, directoryPath)>
		                		<a class="buttonView" onclick="window.open('${base}/data/file.action?method=viewContent&selectedPath=${selectedPath}')"><@i.messageFor "viewContent" "View"/></a>
		                	</#if>
		                	</td>
		                	<#assign filePath=p.unixPath(file.path)>
		                	<td class="contentFilePath">${filePath}</td>
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