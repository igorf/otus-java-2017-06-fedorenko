<#macro layout title>
<html>
    <head>
        <meta charset="utf-8" />
        <title>${title}</title>
        <link rel="stylesheet" href="/css/bootstrap.min.css" />
        <link rel="stylesheet" href="/css/app.css" />
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">&nbsp;</div>
            </div>
            <#nested>
        </div>
    </body>
</html>
</#macro>