<#ftl strip_whitespace=true>
<#--
 * Normalises path by replacing '\' with '/'
 *
 * @param path the path
 * @return the normalised path
 -->
<#function normalise path>
    <#assign normalised = path.replace("\\","/")>
    <#return normalised>
</#function>

<#--
 * Determines if file is viewable by looking for a path name with an extension
 *
 * @param file the file
 * @return boolean true if file path has extension
 -->
<#function isViewable file>
    <#if file.path.matches(".*\\.[A-Za-z]+")><#return true></#if>
    <#return false>
</#function>