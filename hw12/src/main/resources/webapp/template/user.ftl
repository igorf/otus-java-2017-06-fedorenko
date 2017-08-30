<#-- @ftlvariable name="user" type="com.otus.hw12.db.model.UserDataSet" -->

<h2>User info</h2>

<form method="get" action="/user">
    Find user: <input type="text" name="uid"/>
    <input type="submit" value="Find">
</form>

<#if user ??>
<table border="1">
    <tr>
        <td align="right">ID: </td>
        <td>${user.id}</td>
    </tr>
    <tr>
        <td align="right">Name: </td>
        <td>${user.name}</td>
    </tr>
    <tr>
        <td align="right">Age: </td>
        <td>${user.age}</td>
    </tr>
</table>
</#if>

<a href="/webapp">Cache info</a>