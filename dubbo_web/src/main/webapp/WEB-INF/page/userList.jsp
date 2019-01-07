<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<title>用户表</title>
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/paging.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/paging.js"></script>
<script type="text/javascript">
$(function(){
	var page = "${page}";
	if(page != ""){//不等于空满足条件
		$("#pageNum").val("${page.pageNum}");
		$("#pageSize").val("${page.pageSize}");
		$("#order").val("${page.t.order }");
		$("#userName").val("${page.t.user.name }");
		$("#startBirthday").val("${page.t.startBirthday }");
		$("#endBirthday").val("${page.t.endBirthday }");
		$("#userAddr").val("${page.t.user.addr }");
		tableData();
	}
	
	tableData();//调用方法查询数据 
	$("#searchs").click(function(){//当点击搜索按钮就进行搜索
		$("#pageNum").val(1);//默认第一页
		tableData();//查询出搜索的数据
	});
	
	//id顺序或id倒叙
	$("#asc,#desc").click(function(){
		var id = $(this).attr("id");
		/* var pageSize = $("#pageSize").val();
		var search = $("#search").val(); */
		if(id == "asc"){
			var order = $("#order").val();
			if(order != "0"){
				$("#order").val(0);//0为顺序
				tableData();
			}
		}else{
			var order = $("#order").val();
			if(order != "1"){
				$("#order").val(1);//1为倒序
				tableData();
			}
		}
	});
	
	$("#endBirthday").change(function(){
		var startBirthday = $("#startBirthday").val();
		var endBirthday = $(this).val();
		if(startBirthday > endBirthday){//当当前时间大于结束时间就满足条件
			$(this).val(startBirthday);//将开始时间设置为结束时间
		}
	});
	
});

//跳转修改页面
function update(data){
	$("#form").attr("action","${pageContext.request.contextPath }/user/user/"+data).submit();//修改action的值并提交form表单
}

//删除用户
function deletes(data){
	var id = $(data).parent("td").siblings("td:first").text();
	$.ajax({//用ajax静态访问servlet
		type : "POST",
		url : "${pageContext.request.contextPath }/user/user/"+id,
		async : false,//设置为同步请求
		data : {
			"_method":"DELETE"
		},
		success : function(data) {//成功则调用函数tableData添加数据到页面
			if($("#pageNum").val() == $("#pageCount").text() && Math.ceil(($("#totalCount").text()-1)/$("#pageSize").val()) != $("#pageNum").val()){//	向上取整（如果总记录数-1除以当前条数）   != 需要跳转的页面
				$("#pageNum").val($("#pageNum").val()-1);
			}
			tableData();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 alert("错误: "+XMLHttpRequest.status);
		}
	}); 
}

function tableData(){
	$("#tables,#paging").empty();//清空表的值和页码数
	var form = $('form').serializeArray();//获取表单所有值的json格式
	$.ajax({//用ajax静态访问servlet
		type : "GET",
		url : "${pageContext.request.contextPath }/user/users",
		dataType:"json",
		async : false,//设置为同步请求
		data : form,
		success : function(data) {//成功则调用函数tableData添加数据到页面
			if(data.dataList != "" ){
				if($("#wu").text() == "无数据"){//如果无数据则满足条件(也就是只报一次无数据)
					$("#wu").remove();
				} 
				for ( var i in data.dataList) {//循环出数据的数据添加到table表中
					if(data.dataList[i].sex == 2){
						data.dataList[i].sex = "女";
					}else if(data.dataList[i].sex == 1){
						data.dataList[i].sex = "男";
					}else{
						data.dataList[i].sex = "保密";
					}
					if(data.dataList[i].state == 0){
						data.dataList[i].state = "停用";
					}else{
						data.dataList[i].state = "启用";
					}
					var style1 = "";
					var style2 = "";
					if(parseInt(i)+1 == data.dataList.length){//设置样式	等于最大长度
						style1 = "style='border-bottom-left-radius: 2em;'";
						style2 = "style='border-bottom-right-radius: 2em;'";
					}
					$("#tables").append("<tr>"
							+"<td "+style1+">"+data.dataList[i].id+"</td>"
							+"<td>"+data.dataList[i].name+"</td>"
							+"<td>"+data.dataList[i].sex+"</td>"
							+"<td>"+data.dataList[i].addr+"</td>"
							+"<td>"+data.dataList[i].birthday+"</td>"
							+"<td>"+data.dataList[i].email+"</td>"
							+"<td>"+data.dataList[i].state+"</td>"
							+"<td "+style2+">"
							+"<input type='button' name='update' class='updatebutton' value='修改' onclick='update("+data.dataList[i].id+");'/>"
							+"<input type='button' onclick='deletes(this)' style='background-color:#FA5858;border: 1px solid #FA5858' class='updatebutton' value='删除' />"
							+"</td>"
							+"</tr>");
				}
				//参数依次为	总页数，当前页数，总条数，当前大小，页码数，分页所放的ul的ID的值 ，搜索框的值
				myFunction(data.pageCount, data.pageNum, data.totalCount, data.pageSize, 3, "paging", data.search);

				var pageNum = $("#pageNum").val();//当点击按钮页数，id为pageNumber就会获得点击的页数，用变量接住页数的值
				$("ul button").each(function(){//匹配ul下的button的值
					if(pageNum == $(this).text()){//当页数和该对象的文本值相等时
						$(this).attr("style","color: #2eccfa;background: #dddddd;border: 1px solid #dddddd;");//就改变该按钮的样式
						$(this).attr("disabled","disabled");//设置当前页数不能点击
					}
				});
			}else{
				if($("#wu").text() == ""){//如果无数据则满足条件(也就是只报一次无数据)
					$("#myTable").after("<h2 id='wu'>无数据</h2>");
				} 
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			 alert("错误: "+XMLHttpRequest.status);
		}
	});
}

</script>
<body style="background-color: #F0F0F0">
	<div align="center">
		<form action="" id="form">
			<input id="pageSize" name="pageSize" type="hidden" value="3"/><!-- 默认每页3条 -->
			<input id="pageNum" name="pageNum" type="hidden" value="1"/><!-- 默认第一页 -->
			<input id="order" name="order" type="hidden" value="1"/><!-- 默认排序倒序 -->
			<p>
				姓名:<input id="userName" style='border-radius:5px;' name="user.name" type="text">&nbsp;&nbsp;
				生日:<input id="startBirthday" style='border-radius:5px;' size="4" name="startBirthday" type="date">-<input size="4" style='border-radius:5px;' id="endBirthday" name="endBirthday" type="date">&nbsp;&nbsp;
				地址:<input id="userAddr" style='border-radius:5px;' name="user.addr">&nbsp;&nbsp;
				<input id="searchs" type="button" class='updatebuttons' value="确定">
				<input type="reset" class='updatebuttons' style="background-color:#C0C0C0;border: 1px solid #C0C0C0" value="清空">&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/user/user"><button type="button" class='updatebuttons' style="background-color:#ffaad5;border: 1px solid #ffaad5">添加</button></a>
				<button type="button" class='updatebuttons' id="asc" style="background-color:#ff7575;border: 1px solid #ff7575">id顺序</button>
				<button type="button" class='updatebuttons' id="desc" style="background-color:#d3a4ff;border: 1px solid #d3a4ff">id倒序</button>
			</p>
		</form>
		<div id="table_wrap">
			<table id="myTable" style="border-top-left-radius: 2em;border-top-right-radius: 2em;border-bottom-right-radius: 2em;border-bottom-left-radius: 2em;"  border="1" cellpadding="30" cellspacing="0">
				<!-- 表头 -->
				<thead>
					<tr align="center">
						<th style="border-top-left-radius: 2em;">id</th>
						<th>名字</th>
						<th>性别</th>
						<th>地址</th>
						<th>生日</th>
						<th>电子邮箱</th>
						<th>状态</th>
						<th style="border-top-right-radius: 2em;">操作</th>
					</tr>
				</thead>
				
				<!-- 主体 -->
				<tbody id="tables">
					
				</tbody>
				
				<!-- 页脚 -->
				<tfoot>
				</tfoot>
			</table>
		</div>
		<div class="pagination">
			<ul id="paging">
			</ul>
		</div>
	</div>
</body>
</html>