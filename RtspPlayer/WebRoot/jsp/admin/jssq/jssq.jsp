<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String ctx = request.getContextPath();

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript" src="<%=ctx%>/jsp/admin/jssq/jssq.js"></script>
<style type="text/css">
#Container{
    width:1000px;
    /*margin:0 auto;设置整个容器在浏览器中水平居中*/
}

#Content{
    /*height:600px;
    此处对容器设置了高度，一般不建议对容器设置高度，一般使用overflow:auto;属性设置容器根据内容自适应高度，如果不指定高度或不设置自适应高度，容器将默认为1个字符高度，容器下方的布局元素（footer）设置margin-top:属性将无效*/
    margin-top:0px;/*此处讲解margin的用法，设置content与上面header元素之间的距离*/
   
}
#Content-Left{
	width:70%;
    /*此处对容器设置了高度，一般不建议对容器设置高度，一般使用overflow:auto;属性设置容器根据内容自适应高度，如果不指定高度或不设置自适应高度，容器将默认为1个字符高度，容器下方的布局元素（footer）设置margin-top:属性将无效*/
    margin-top:0px;/*此处讲解margin的用法，设置content与上面header元素之间的距离*/
	float:left;
}
#Left-Top{
    /*height:40px;*/
    width:100%;
    margin:0px;/*设置元素跟其他元素的距离为20像素*/
    float:left;/*设置浮动，实现多列效果，div+Css布局中很重要的*/
}
#Left-Bottom{
    /*height:360px;*/
    width:100%;
    margin:0px;/*设置元素跟其他元素的距离为20像素*/
    float:left;/*设置浮动，实现多列效果，div+Css布局中很重要的*/
}
#Content-Right{
    /*height:440px;*/
    width:30%;
    margin:0px;/*设置元素跟其他元素的距离为20像素*/
    float:left;/*设置浮动，实现多列效果，div+Css布局中很重要的*/	
}
/*注：Content-Left和Content-Main元素是Content元素的子元素，两个元素使用了float:left;设置成两列，这个两个元素的宽度和这个两个元素设置的padding、margin的和一定不能大于父层Content元素的宽度，否则设置列将失败*/
#Footer{
    height:40px;
    background:#90C;
    margin-top:20px;
	border:1px solid red;
}
.Clear{
    clear:both;
}
</style>

</head>
<body>
<div id="Container">

    <div id="Content">
		<div id="Content-Left">
			<div id="Left-Top"><div id='js_form' ></div></div>
			<div id="Left-Bottom"><div id='js_grid'></div></div>
		</div>
		<div id="Content-Right">
			<div>
				<a id="save" href="#" onclick="return false;">保存</a>
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
    </div>
	
    <div class="Clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
</div>
</body>
</html>
