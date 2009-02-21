<div id="navigation">
  <#if controller.menu??>
    <div id="menu">
        <#assign menu=controller.getMenu()>
        <#list menu.groups as group>
        <div class="menu-group menu-entry"><a>${group.title}</a>
            <div id="menu-group-content${group_index}" class="menu-group-content">
            <#list group.entries as entry>
                <div class="menu-entry"><a href="${base}/${entry.path}.action">${entry.title}</a></div>
            </#list>
            </div>
        </div>
        </#list>
    </div>    
    </#if>
</div>