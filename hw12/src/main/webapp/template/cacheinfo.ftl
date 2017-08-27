<#-- @ftlvariable name="caches" type="java.util.Map<String, com.otus.hw.yace.CacheEngine>" -->

<h2>Cache info</h2>

<#list caches?keys as key>
    <#assign value=caches[key] />
    <form action="/cleancache" method="post">
        <input type="hidden" name="cache" value="${key}"/>
        <table border="1">
            <tr>
                <td align="right">Cache: </td>
                <td>${key}</td>
            </tr>
            <tr>
                <td align="right">Max size: </td>
                <td>${value.maxSize}</td>
            </tr>
            <tr>
                <td align="right">Global TTL: </td>
                <td>${value.elementTTL}</td>
            </tr>
            <tr>
                <td align="right">Idle TTL: </td>
                <td>${value.idleTTL}</td>
            </tr>
            <tr>
                <td align="right">Current size: </td>
                <td>${value.size()}</td>
            </tr>
            <tr>
                <td align="right">Misses: </td>
                <td>${value.misses()}</td>
            </tr>
            <tr>
                <td align="right">Hits: </td>
                <td>${value.hits()}</td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Clean"/>
                </td>
            </tr>
        </table>
    </form>
</#list>

<a href="/user">Show sample user</a>