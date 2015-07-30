<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	/**
	 * 流程监控
	 * 作者：zengziwen  zengziwen@foresee.cn
	 * 时间:  2012-07-29
	 **/
	String ctx = request.getContextPath();
	String msg = (String) request.getAttribute("msg");
	String processDefId = (String) request.getAttribute("processDefId");
	String executionId = (String) request.getAttribute("executionId");
%>
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>流程图</title>
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
</head>

<body onClick="showoff();" onload="fresh();" style="margin:0px;padding:0px">
	<div>
		<img alt=""
			src="<%=ctx%>/public-access/workflow/processManage.do?pic&processDefId=<%=processDefId%>&executionId=<%=executionId%>" />

		<%
			String picture = (String) request.getAttribute("actBorder");
			out.println(picture == null ? "" : picture);
		%>
	</div>
	<div id="myTip"
		style="z-index:99;visibility:hidden;position:absolute;left:10px;top:10px;heigth:60px; width:70px;;background:url('<%=ctx%>/resources/images/tip.gif') ;width:auto;height:97px;"
		onMousemove='p_move(this)'>
		<div
			style="height: auto;; position: relative; left: 25px; top: 0px; right: 25px; width: auto; background-color: #e0e05f">
			<br>&nbsp;<font size=2></font>
		</div>
	</div>
	<div id="myTip2"
		style="z-index: 999; visibility: hidden; position: absolute; left: 10px; top: 10px; heigth: 60px; width: 70px; width: auto; height: 97px;"
		onMousemove='p_move(this)'>
		<div id="myTipCnt"
			style="height: auto;; position: relative; left: 25px; top: 0px; right: 25px; width: auto; background-color: #e0e05f">
			&nbsp;&nbsp;<font size=2></font>
		</div>
	</div>
	
		
	<div id="mlay"
		style="z-index: 1; position: absolute; display: none; cursor: default;"
		onMousemove='p_move(this)' onClick="return false;"></div>
		
	<script type="text/javascript">
		var $myTip = $('#myTip');
		var $myTip2 = $('#myTip2');
		var $mlay = $('#mlay');
		var c = 0;
		setInterval(function() {
			for (var i = 0; i < 20; i++) {
				var $image = $('#myImage' + i);
				if ($image != null) {

					if (c % 2 == 0)
						$image.css({borderColor:'yellow'});
					else
						$image.css({borderColor:'red'});
				}
			}
			c++;
		}, 600);

		function p_move(e) {
			var $cnt = $('#myTipCnt');
			var $e = $(e);
			if ($e != null) {
				var arrayAll = $e.attr("info").split('#$#');
				var temp = "<div style='position:relative;top:5px;' >";
				for (var i = 0; i < arrayAll.length; i++) {
					var array = arrayAll[i].split('#@#');
					if (array == null || array == '') {
						continue;
					}
					if (i == 0)
						temp += "<font size=2><b>" + array[0]
								+ "</b></font></div><hr style='width:80'>";
					temp += "<font size=2>" + array[1] + "<br>" + array[2]
							+ "<br>" + array[3] + "<br>" + array[4]
							+ "</font><br>";
					if (i < arrayAll.length - 2)
						temp += "<br>";
				}
				$cnt.html(temp + '</div>');
			}
			
			var	left = $e.css('left').replace('px', '');
			var	top = $e.css('top').replace('px', '');
			$myTip.css({left : parseInt(left) + 100,top : parseInt(top) - 10,visibility : 'visible'});
			$myTip2.css({left : parseInt(left) + 100,top : parseInt(top) - 10,visibility : 'visible'});
			
		}
		function p_out() {
			$myTip.css({visibility : 'hidden'});
			$myTip2.css({visibility : 'hidden'});
		}

		var mname = new Array("复制信息 ");

		var ph = 18, mwidth = 50;//每条选项的高度,菜单的总宽度 
		var bgc = "#eee", txc = "black";//菜单没有选中的背景色和文字色 
		var cbgc = "darkblue", ctxc = "white";//菜单选中的选项背景色和文字色 

		var mover = "this.style.background='" + cbgc + "';this.style.color='"
				+ ctxc + "';";
		var mout = "this.style.background='" + bgc + "';this.style.color='"
				+ txc + "';";

		function oncontextmenu1(event) {
			$mlay.css({display : "",pixelTop:event.clientY,pixelLeft:event.clientX});
			return false;
		}
		function showoff() {
			$mlay.css({display : "none"});
			$myTip.css({visibility : 'hidden'});
			$myTip2.css({visibility : 'hidden'});
		}

		function fresh() {
			$mlay.css({background : bgc,color:txc,width:mwidth,height:mname.length * ph});
			var h = "<table width=100% height=" + mname.length * ph
					+ "px cellpadding=0 cellspacing=0 border=0>";
			var i = 0;
			for (i = 0; i < mname.length; i++) {
				h += "<tr align=center height=" + ph
						+ " onclick='alert();' onMouseover=\"" + mover
						+ "\" onMouseout=\"" + mout
						+ "\"><td style='font-size:8pt;'>" + mname[i]
						+ "</td></tr>";
			}
			h += "</table>";
			$mlay.html(h);
		}

	/**
	 * 流程跟踪Javascript实现
	 
	$(function () {*/

	    /**
	     * 获取元素的outerHTML
	     
	    $.fn.outerHTML = function () {

	        // IE, Chrome & Safari will comply with the non-standard outerHTML, all others (FF) will have a fall-back for cloning
	        return (!this.length) ? this : (this[0].outerHTML ||
	            (function (el) {
	                var div = document.createElement('div');
	                div.appendChild(el.cloneNode(true));
	                var contents = div.innerHTML;
	                div = null;
	                return contents;
	            })(this[0]));

	    };

	    if ($('#processDiagram').length == 1) {
	        showActivities();
	    }

	    // 解决坐标错误问题
	    $('#changeToAutoDiagram').click(function() {
	        $('.activity-attr,.activity-attr-border').remove();
	        $('#processDiagram').attr('src', ctx + '/chapter13/process/trace/data/auto/' + processInstanceId);
	    });

	});

	function showActivities() {
	    $.getJSON(ctx + '/chapter13/process/trace/data/' + executionId, function (infos) {

	        var positionHtml = "";

	        var diagramPositon = $('#processDiagram').position();
	        var varsArray = new Array();
	        $.each(infos, function (i, v) {
	            var $positionDiv = $('<div/>', {
	                'class': 'activity-attr'
	            }).css({
	                    position: 'absolute',
	                    left: (v.x - 1),
	                    top: (v.y - 1),
	                    width: (v.width - 2),
	                    height: (v.height - 2),
	                    backgroundColor: 'black',
	                    opacity: 0
	                });

	            // 节点边框
	            var $border = $('<div/>', {
	                'class': 'activity-attr-border'
	            }).css({
	                    position: 'absolute',
	                    left: (v.x - 1),
	                    top: (v.y - 1),
	                    width: (v.width - 4),
	                    height: (v.height - 3)
	                });

	            if (v.currentActiviti) {
	                $border.css({
	                    border: '3px solid red'
	                }).addClass('ui-corner-all-12');
	            }
	            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
	            varsArray[varsArray.length] = v.vars;
	        });

	        $(positionHtml).appendTo('body').find('.activity-attr-border');

	        // 鼠标移动到活动上提示
	        $('.activity-attr-border').each(function (i, v) {
	            var tipContent = "<table class='table table-bordered'>";
	            $.each(varsArray[i], function(varKey, varValue) {
	                if (varValue) {
	                    tipContent += "<tr><td>" + varKey + "</td><td>" + varValue + "</td></tr>";
	                }
	            });
	            tipContent += "</table>";
	            $(this).data('vars', varsArray[i]).data('toggle', 'tooltip').data('placement', 'bottom').data('title', '活动属性').attr('title', tipContent);
	        }).tooltip();
	    });
	}
	*/
	</script>
</body>
</html>
