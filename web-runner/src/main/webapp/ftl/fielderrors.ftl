<#list errors.allErrorMessages as error>
    <script type="text/javascript">
        $('${error.name}').value = '${error.value}';
        $('${error.name}').style.border = 'solid red 2px';
    </script>
</#list>