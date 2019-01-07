/**
 * 分页页码封装
 * 
 * @param pageCount
 *            总页数
 * @param pageNum
 *            当前页
 * @param totalCount           
 *            总条数
 * @param pageSize
 *            每页显示数据条数
 * @param num
 *            需要显示的页码个数
 * @param pageContent
 *            分页所放的ul的ID的值
 */

function myFunction(pageCount, pageNum, totalCount, pageSize, num, pageContent, search){
	$("#"+pageContent).append("<font>一共"+pageCount+"页</font>"); 
	$("#"+pageContent).append("<font>&nbsp;当前第"+pageNum+"页</font>");
	$("#"+pageContent).append("<font>&nbsp;一共"+totalCount+"条记录</font>");
	$("#"+pageContent).append("&nbsp;每页<select id='pageBar' onChange=changePageSize("+totalCount+","+pageNum+",'"+search+"') ><option value='3'>3</option><option value='5'>5</option><option value='10'>10</option></select>条数据");
	
	$("#pageBar option").each(function(){//进行迭代匹配pageSize，找到下拉框的值将该值设置为默认显示
		if($(this).val() == pageSize){
			$(this).attr("selected","selected");
		}
	});
	
	var nums = pageNum - 1;//当前页数减1等于0表示为首页,不能再上一页,通常情况下是可以一直上一页的
	if(pageNum == 1){
		$("#"+pageContent).append("&nbsp;<button>首页</button>");
		$("#"+pageContent).append("<button>上一页</button>");
	}else{
		$("#"+pageContent).append("&nbsp;<button onclick=tableData(1,"+pageSize+",'"+search+"');>首页</button>");
		$("#"+pageContent).append("<button onclick=tableData("+nums+","+pageSize+",'"+search+"');>上一页</button>");
	}
	
	if(pageCount < num){//如果总页数<页码数，就有多少显示多少个页码
		for (var int = 1; int <= pageCount; int++) {
			$("#"+pageContent).append("<button onclick=tableData("+int+","+pageSize+",'"+search+"');>"+int+"</button>");
		}
	}else{//如果不是则满足else条件
		if(pageNum <= 2){//当当前页数 <= 2 时前按钮不再变动（表示前2个按钮变动按钮）
			for (var int2 = 1; int2 <= num; int2++) {
				$("#"+pageContent).append("<button onclick=tableData("+int2+","+pageSize+",'"+search+"');>"+int2+"</button>");
			}
		}else if(pageNum > pageCount-2){//当当前页数 > 总页数-2 时前按钮不再变动（表示后2个按钮变动按钮）
			for (var int3 = pageCount-2; int3 <= pageCount; int3++) {
				$("#"+pageContent).append("<button onclick=tableData("+int3+","+pageSize+",'"+search+"');>"+int3+"</button>");
			}
		}else{//如果都不满足就表示需要根据不同的页数显示不同的页码数
			for (var int4 = pageNum-1; int4 <= pageNum+1; int4++) {//前两个页码数+当前页码数+加后两个页码数 需要动态
				$("#"+pageContent).append("<button onclick=tableData("+int4+","+pageSize+",'"+search+"');>"+int4+"</button>");
			}
		}
	}
	
	var nums1 = pageNum + 1;
	if(pageNum == pageCount){//当当前页数等于最后一页则不能触发函数
		$("#"+pageContent).append("<button>下一页</button>");
		$("#"+pageContent).append("<button>尾页</button>");
	}else{//否则下一页就为当前页码数加1,尾页为最后一页（pageCount就为最后一页）
		$("#"+pageContent).append("<button onclick=tableData("+nums1+","+pageSize+",'"+search+"');>下一页</button>");
		$("#"+pageContent).append("<button onclick=tableData("+pageCount+","+pageSize+",'"+search+"');>尾页</button>");
	}
}

function changePageSize(totalCount,pageNum,search){//改变每页多少条数据
	var pageSize = $("#pageBar :selected").val();//获取下拉框已经修改的值
	$("#pageSize").val(pageSize);//设置入隐藏的输入框
	var modulo = totalCount % pageSize == 0 ? totalCount/pageSize : Math.ceil(totalCount/pageSize);//取模如果总数据条数除以每页条数等于0表示为可以被整除，则直接除便好，如果不为0则表示不能被整除，则向上取整数
	pageNum > modulo ? tableData(modulo,pageSize,search) : tableData(pageNum,pageSize,search);//如果当前页数大于总页数则将总页数做为当前页数，如果不大于则将当前页数传入
}
