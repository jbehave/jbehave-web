<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${title}</title>
    <#include "/ftl/resources.ftl" parse="true">
</head>
<body>
<div id="view">
<#include "/ftl/header.ftl" parse="true">
${body}
<#include "/ftl/footer.ftl" parse="true">
</div>
<#include "/ftl/sh.ftl" parse="true">
</body>
</html>