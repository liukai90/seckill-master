<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <title>秒杀列表页</title>
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
    	<div class="panel panel-default">
    		<div class="panel-heading text-center">
    			<h2>秒杀列表</h2>
    		</div>
    		<div class="panel-body">
    			<table class="table table-hover">
    				<thead>
	    				<tr>
		    				<th>名称</th>
	    					<th>库存</th>
	    					<th>开始时间</th>
	    					<th>结束时间</th>
	    					<th>创建时间</th>
	    					<th>详情页</th>
	    				</tr>
    				</thead>
    				<tbody>
	    				<c:forEach  var="sk" items="${list}">
	    					<tr>
	    						<td>${sk.name }</td>
		    					<td>${sk.number}</td>
		    					<td>
		    						<fmt:formatDate value="${sk.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</td>
		    					<td>
		    						<fmt:formatDate value="${sk.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</td>
		    					<td>
		    						<fmt:formatDate value="${sk.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
		    					</td>
		    					<td>
		    						<a class="btn btn-info" href="${pageContext.request.contextPath }/seckill/${sk.seckillId }/detail">link</a>
		    					</td>
    						</tr>
	    				</c:forEach>
    					
    				</tbody>
    			</table>
    		</div>
    	</div>
    </div>

  </body>
   <script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="http://cdn.bootcss.com/twitter-bootstrap/3.0.3/js/bootstrap.min.js"></script>
</html>