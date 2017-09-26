<#-- @ftlvariable name="user" type="com.otus.hw15.data.model.User" -->
<#include 'layout/layout.ftl'>
<@layout title="Show user properties">

<div class="row">
    <div class="col-lg-12">
        <h2>Show user properties</h2>
        <div class="well col-lg-offset-4 col-lg-4">
            <legend>Find user by ID</legend>

            <form method="get" action="/user" class="form-horizontal">
                <div class="form-group">
                    <label for="username" class="col-lg-3 control-label">UID: </label>
                    <div class="col-lg-9">
                        <input class="form-control" id="uid" placeholder="User ID" name="uid">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-9 col-lg-offset-3">
                        <button type="submit" class="btn btn-info">Find</button>
                    </div>
                </div>
            </form>

            <#if user ??>
                <table class="table">
                    <thead>
                    <tr>
                        <th colspan="2">User data</th>
                    </tr>
                    </thead>
                    <tbody>
                        <tr class="active">
                            <td width="20%">ID: </td>
                            <td>${user.id}</td>
                        </tr>
                        <tr class="active">
                            <td>Name: </td>
                            <td>${user.name}</td>
                        </tr>
                        <tr class="active">
                            <td>Age: </td>
                            <td>${user.age}</td>
                        </tr>
                    </tbody>
                </table>
            </#if>
        </div>
    </div>
</div>

</@layout>