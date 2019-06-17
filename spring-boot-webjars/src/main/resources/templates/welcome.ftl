<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Welcome - Spring Boot Webjar Demo</title>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/css/bootstrap-theme.min.css'/>">
</head>
<body>
<div class="container">
<br/>
    <div class="alert alert-success">
        <a href="#" class="close">x</a>
        <strong>Date: ${time?date}</strong> ${message}
    </div>
</div>
</body>
<script src="<@spring.url '/webjars/jquery/jquery.min.js'/>"></script>
<script src="<@spring.url '/webjars/bootstrap/js/bootstrap.min.js'/>"></script>
<script src="<@spring.url '/foo.js'/>"></script>
</html>