<#-- @ftlvariable name="caches" type="java.util.Map<String, com.otus.hw.yace.CacheEngine>" -->
<html>
    <head>
        <title>Cache stats</title>
        <script language="JavaScript" src="/js/cachenotifier.js"></script>
    </head>
    <body onload="cacheNotifierConnect();">
    <h2>Cache info</h2>

    <#list caches?keys as key>
        <#assign value=caches[key] />
    <form action="/cleancache" method="post">
        <input type="hidden" name="cache" value="${key}"/>
        <div id="cachesData_${key}"></div>
        <input type="submit" value="Clean"/>
    </form>
    <hr />
    </#list>

    <a href="/user" target="_blank">Show sample user</a>
    </body>
</html>