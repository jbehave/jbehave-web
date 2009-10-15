<#import "/ftl/waffle/i18n.ftl" as i>
<#import "/ftl/waffle/form.ftl" as w>
<#import "/ftl/paths.ftl" as p>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<head>
    <title><@i.messageFor "dataUpload" "Data Upload"/></title>
</head>
<#include "/ftl/navigation.ftl" parse="true">
<div id="content">
    <form action="${base}/data/upload.action">
    
        <div id="upload">
           <fieldset>
                <legend><@i.messageFor "uploadData" "Upload Data"/></legend>
                <p>
					<#list 1..filesToUpload as count>
						<#assign uploadFile= "uploadFile"+ count>
						<@w.file "${uploadFile}" />
    				</#list>  
               	</p>
               	<p>
                    <a class="buttonUpload" onclick="fireMultipartActionMethod('upload');"><@i.messageFor "upload" "Upload"/></a>        
                </p>
            </fieldset>
        </div>

       <#assign errors = controller.errors />
       <#if (errors.size() > 0) >
	   <div id="errors">  
	 	 <fieldset>       
            <legend><@i.messageFor "errors" "Errors"/></legend>
	         <table>
	            <#list errors as error>
	                <tr>
	                    <td>${error}</td>
	                </tr>
	            </#list>
	         </table>   
	     </fieldset>
       </div>
       </#if>

	   <#assign uploadedFiles = controller.uploadedFiles />
       <#if (uploadedFiles.size() > 0) >
	   <div id="uploadedFiles">  
	 	 <fieldset>
		     <legend><@i.messageFor "uploadedFiles" "Uploaded Files"/></legend>
	         <table>
	            <#list uploadedFiles as file>
	                <tr>
						<#assign path=p.normalise(file.path)>	             
	                    <td>${path}</td>
	                </tr>
	            </#list>
	         </table>   
         </fieldset>
       </div>
       </#if>                   
    </form>
</div>
</body>
</html>