<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
  <head>
    <title>秒杀详情页</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
   <link rel="stylesheet" href="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/css/bootstrap.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
        <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <!-- 页面显示部分 -->
    <div class="container">
    	<div class="panel panel-default text-center">
    		<div class="panel-heading">
    			<h1>${seckill.name}</h1>
    		</div>
    		<div class="panel-body">
		    	<h2 class="text-danger">
		                <%--显示time图标--%>
		                <span class="glyphicon glyphicon-time"></span>
		                <%--展示倒计时--%>
		                <span class="glyphicon" id="seckill-box"></span>
		          </h2>
    		</div>
    	</div>
    	
    </div>
    <%--登录弹出层 输入电话--%>
<div id="killPhoneModal" class="modal fade">

    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"> </span>秒杀电话:
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号^o^" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>

        </div>
    </div>

</div>
    
  </body>
   
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<%--使用CDN 获取公共js http://www.bootcdn.cn/--%>
	<%--jQuery Cookie操作插件--%>
	<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<%--jQuery countDown倒计时插件--%>
	<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
	<script src="${pageContext.request.contextPath }/resource/js/seckill.js"></script>
	<script type="text/javascript">
	 
	$(function () {
	        //使用EL表达式传入参数
	        var seckillId=${seckill.seckillId};
	        var startTime=${seckill.startTime.time};
	        var endTime=${seckill.endTime.time};
	        //var basePath=${pageContext.request.contextPath};
	        seckill.detail.init({'seckillId':seckillId,
                'startTime':startTime,//毫秒
                'endTime':endTime,
                //'basePath':basePath
                });
	        
	    });
	</script>
    
</html>