<#ftl strip_whitespace=true>
<#--
 * Returns the URL path based on the view
 *
 * @param view the FilesContext.View
 * @param path the path
 * @param directoryPath the directory path to use with relative views
 * @return the url path
 -->
<#function urlPath view path directoryPath>
    <#if (view == 'FULL_PATH')><#return unixPath(path)?url('UTF-8')></#if>
    <#assign full = unixPath(directoryPath)+"/"+unixPath(path)>
    <#return full?url('UTF-8')>
</#function>

<#--
 * Ensures path uses unix separators by replacing '\' with '/'
 *
 * @param path the path
 * @return the unix path
 -->
<#function unixPath path>
    <#assign unix = path.replace("\\","/")>
    <#return unix>
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