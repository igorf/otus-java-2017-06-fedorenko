<#-- @ftlvariable name="caches" type="java.util.Map<String, net.sf.ehcache.Cache>" -->

<#include 'layout/layout.ftl'>
<@layout title="Cache summary">
<script language="JavaScript" src="/js/cachenotifier.js"></script>

<div class="row">
    <div class="col-lg-3">
        <h2>Cache summary</h2>

        <#list caches?keys as key>
            <#assign value=caches[key] />
            <div class="well">
                <legend>${key}</legend>
                <div id="cachesData_${key}" class="monospaced"></div>
                <form class="form-horizontal">
                    <input type="hidden" name="cache" value="${key}"/>

                    <div class="form-group">
                        <div class="col-lg-9">
                            <button type="submit" class="btn btn-danger btn-sm" onclick="cleanCache('${key}'); return false;">Clean</button>
                        </div>
                    </div>
                </form>
            </div>
        </#list>

        <a href="/user" target="_blank">Show sample user</a>
    </div>
</div>

<script language="JavaScript">
    cacheNotifierConnect();
</script>

</@layout>