Please log in

<form action="/login" method="post">
    <table border="0">
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Login" />
            </td>
        </tr>
    </table>
</form>

<#if loginFailed?? >
<b>Username or password incorrect!</b>
</#if>
