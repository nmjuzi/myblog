<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改人信息</title>
    <%@include file="../admin/common/head.jsp" %>
    <script type="text/javascript" charset="utf-8"
            src="${myblog}/static/ueditor1_4_3_3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${myblog}/static/ueditor1_4_3_3/ueditor.all.min.js">
    </script>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">

    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8"
            src="${myblog}/static/ueditor1_4_3_3/lang/zh-cn/zh-cn.js"></script>
     <script>
        function submitData() {
            $('#fm').form('submit',{
                url:'${myblog}/admin/blogger/save.do',
                onSubmit:function() {
                    $('#nickname').attr('required',true);
                    $('#sign').attr('required',true);
                    var profile = UE.getEditor("profile").getContent();
                    $("#pf").val(profile); //将UEditor编辑器中的内容放到隐藏域中提交到后台
                    return $(this).form("validate");
                },//进行验证，通过才让提交
                success:function (result) {
                    // var result = eval("(" + result + ")"); //将json格式的result转换成js对象
                    var result = JSON.parse(result);
                    if(result.success) {
                        $.messager.alert("系统提示", "博主信息更新成功");
                    } else {
                        $.messager.alert("系统提示", "博主信息更新失败");
                        return;
                    }
                }
            });
        }
    </script>
    <style type="text/css">
    	#xggrxxtable tr{
    		height:50px;
    		
    	}
    </style>
</head>
<body style="margin: 10px; font-family: microsoft yahei">
    <div id="p" class="easyui-panel" title="修改个人信息" style="padding: 10px;text-algin:center" >
        <form method="post" id="fm" enctype="multipart/form-data" >
        <table style="width:100%;margin-top:10px" id="xggrxxtable">
        <tr>
        	<td style="width:33%;">
        		<label class="col-sm-4 label-div">用户名：</label>
        		<div class="col-sm-7">
        			<input type="hidden" id="id" name="id" value="${blogger.id }"/>
        			<input type="text" class="form-control" id="userName" name="userName" 
   			       			readonly="readonly" value="${blogger.userName }">
   			    </div>
        	</td>
        	<td style="width:33%;">
        		<label class="col-sm-3 label-div">昵称：</label>
        		<div class="col-sm-7">
        			<input type="text" id="nickName" class="form-control" name="nickName"  placeholder="给自己起个响亮的昵称吧"/>
   			    </div>
        	</td>
        	<td></td>
        </tr>
        <tr>
        	<td colspan="2">
        		<label class="col-sm-2 label-div" style="padding-left: 30px;">签名：</label>
        		<div class="col-sm-9">
        			 <input type="text" id="sign" class="form-control" name="sign"  placeholder="不要偷懒，签名就要个性一点"/>
        		</div>
        	</td>
        </tr>
        <tr>
        	<td colspan="2">
        		<label class="col-sm-2 label-div">个人头像：</label>
        		<div class="col-sm-9">
        			<input type="file" id="imageFile" class="form-control" name="imageFile"/>
        		</div>
        	</td>
        </tr>
        <tr>
       	 <td colspan="3">
        		<label class="col-sm-2 label-div" style="width:120px">个人简介：</label>
        		<div class="col-sm-9">
        			<script id="profile" type="text/plain" style="width:95%; height:300px;"></script>
  	   				<input type="hidden" id="pf" name="profile">   <!-- UEditor不能作为表单的一部分提交，提交时将里面的内容放到pf中-->
        		</div>
        	</td>
        </tr>
	  </table>
	  	<div align="center" style="margin-top:20px">
	  			<a href="javascript:submitData()" class="easyui-linkbutton"
                           data-options="iconCls:'icon-submit'">提交</a>
	      </div>
        </form>
     
    </div>
<script>
    var ue = UE.getEditor('profile');
    ue.addListener('ready',function () {
        UE.ajax.request('${myblog}/admin/blogger/getBloggerInfo.do',{
            method: "post",
            async: false,
            data: {},
            onsuccess: function(result) {
                //result = eval("(" + result.responseText + ")");
                result = JSON.parse(result.responseText);
                $("#nickName").val(result.nickName);
                $("#sign").val(result.sign);
                UE.getEditor('profile').setContent(result.profile);
            }
        });
    });
</script>

</body>
</html>
