<#-- @ftlvariable name="user" type="com.otus.hw15.data.model.User" -->
<#include 'layout/layout.ftl'>
<@layout title="Show user properties">
<script language="JavaScript" src="/js/userdata.js"></script>

<div class="row">
    <div class="col-lg-12">
        <h2>Show user properties</h2>
        <div class="well col-lg-offset-4 col-lg-4">
            <legend>Find user by ID</legend>

            <form class="form-horizontal">
                <fieldset>
                    <div class="form-group">
                        <label for="uid" class="col-lg-3 control-label">UID:</label>
                        <div class="col-lg-9">
                            <input type="number" class="form-control" id="uid" placeholder="UID" name="uid">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <button class="btn btn-info" onclick="showUser(document.getElementById('uid').value); return false;">Find</button>
                        </div>
                    </div>
                </fieldset>
            </form>

            <div id="userPlaceholder" class="monospaced userPlaceholder"></div>
        </div>
    </div>
</div>

<script language="JavaScript">
    userDataConnect();
</script>
</@layout>