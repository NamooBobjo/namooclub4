<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>에러페이지</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file = "/WEB-INF/views/common/common.jsp"  %>
<link rel="shortcut icon" href="${ctx}/resources/common/images/community.ico">


    <style type="text/css">
        body {
            padding-top: 100px;
            padding-bottom: 40px;
            background-color: #ecf0f1;
        }
        .error-header {
            max-width: 500px;
            padding: 15px 29px 25px;
            margin: 0 auto;
            background-color: #e74c3c;
            color: white;
            text-align: left;
            -webkit-border-radius: 15px 15px 0px 0px;
            -moz-border-radius: 15px 15px 0px 0px;
            border-radius: 15px 15px 0px 0px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .error-footer {
            max-width: 500px;
            margin: 0 auto 20px;
            padding-left: 10px;
        }
        .error-body {
            max-width: 500px;
            padding: 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            -webkit-border-radius: 0px 0px 15px 15px;
            -moz-border-radius: 0px 0px 15px 15px;
            border-radius: 0px 0px 15px 15px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .error-heading {
            margin-bottom: 15px;
        }
        .error-btn {
            text-align: center;
            padding-top: 20px;
        }

    </style>

</head>
<body>
<div class="container">

    <!-- header -->
    <div class="error-header">
        <h2 class="error-heading">에러</h2>
    </div>

    <!-- body -->
    <div class="error-body">

        <h3>에러 타이틀</h3>
        <p>아이디와 패스워드를 확인하세요.</p>

        <div class="row error-btn">
            <button class="btn btn-large btn-default" onclick = "location.href ='${ctx}/user/init' ">홈으로 이동</button>
        </div>
    </div>

    <!-- footer -->
    <div class="error-footer">
        <p>© NamooSori 2014.</p>
    </div>
</div>
</body>

</html>