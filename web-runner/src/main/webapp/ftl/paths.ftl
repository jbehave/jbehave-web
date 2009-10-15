<#ftl strip_whitespace=true>
<#--
 * Normalises path by replacing '\' with '/'
 *
 * @param path the path
 -->
<#macro normalise path>
    <#if path?exists><#assign path=path.replace("\\","/")></#if>${path}
</#macro>

<#--
 * Determines if file is viewable by looking for a path name with an extension
 *
 * @param file the file
 -->
<#function isViewable file>
    <#if file.path.matches(".*\\.[A-Za-z]+")><#return true></#if>
    <#return false>
</#function>