<#include 'layout/layout.ftl'>
<@layout title="Login">
<script language="JavaScript" src="/js/userlogin.js"></script>

<div class="row">
    <div class="col-lg-4 col-lg-offset-4">
        <div class="well">
            <div class="form-horizontal">
                <fieldset>
                    <legend>Login</legend>

                    <div class="form-group">
                        <label for="username" class="col-lg-3 control-label">Login: </label>
                        <div class="col-lg-9">
                            <input class="form-control" id="username" placeholder="Login" name="login">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password" class="col-lg-3 control-label">Password: </label>
                        <div class="col-lg-9">
                            <input type="password" class="form-control" id="password" placeholder="Password" name="password">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <button class="btn btn-info" onclick="login(document.getElementById('username').value, document.getElementById('password').value); return false;">Login</button>
                        </div>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
</div>

<div class="row loginError" id="loginError">
    <div class="col-lg-4 col-lg-offset-4">
        <div class="alert alert-dismissible alert-danger">
            Username or password incorrect!
        </div>
    </div>
</div>

</@layout>