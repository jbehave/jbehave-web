<#import "/ftl/waffle/i18n.ftl" as i>
<html>
<head>
  <title><@i.messageFor "storyRunner" "Story Runner"/></title>
</head>
<body>
    <#include "/ftl/navigation.ftl" parse="true">
    <div id="content">
        <h1><@i.messageFor "storyRunner" "Story Runner"/></h1>
        <p><@i.messageFor "selectActionFromMenu" "You can start by selecting an action from the navigation menu."/></p>
    </div>
</body>
</html>