<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<title>添加博客</title>
<%@include file="../admin/common/head.jsp" %>
<style type="text/css">
	#writeblogtable tr{
		height:50px;
	}
</style>
</head>
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
<body>
<body style="margin: 10px; font-family: microsoft yahei">

    <div id="p" class="easyui-panel" title="编写博客" style="padding: 10px;">
        <table cellspacing="20px" id="writeblogtable">
            <tr>
                <td width="80px">博客标题：</td>
                <td><input type="text" id="title" class="form-control" name="title" style="width:400px" /></td>
            </tr>
            <tr>
                <td>所属类别：</td>
                <td><select id="blogTypeId"  name="blogType.id" style="width:154px" class="form-control"
                    ></select>
                 </td>
                <td></td>
            </tr>
            <tr>
                <td valign="top">博客内容：</td>
                <td><script id="editor" name="content" type="text/plain"
                        style="width:95%; height:200px;"></script></td>
            </tr>
            <tr>
				<td>关键字：</td>
				<td><input type="text" id="keyWord" name="keyWord"
					style="width:400px" />&nbsp;&nbsp;&nbsp;多个关键字的话请用空格隔开</td>
			</tr>
            <tr style="text-align:center">
                <td></td>
                <td><a href="javascript:submitData()" class="easyui-linkbutton"
                    data-options="iconCls:'icon-submit'">发布博客</a></td>
            </tr>
        </table>
    </div>
 <!-- 实例化编辑器 -->
    <script type="text/javascript">
        var ue = UE.getEditor('editor');
    </script>
    <script type="text/javascript">
        /**
         * 发布博客
          */
        function submitData() {
            //获取博客标题
            var title = $("#title").val();
            //获取博客类别id
            var blogTypeId = $("#blogTypeId").val();
            //获取博客内容 带标记
            var content = UE.getEditor('editor').getContent();
            //截取博客前155字符 作为博客简介
            var summary = UE.getEditor('editor').getContentTxt().substr(0, 155);
            //博客关键词
            var keyWord = $("#keyWord").val();
            //获取博客内容  不带标签 纯文本
            var contentNoTag = UE.getEditor('editor').getContentTxt();
            //校验
            if (title == null || title == '') {
                $.messager.alert("系统提示", "请输入标题！");
            } else if (blogTypeId == null || blogTypeId == '') {
                $.messager.alert("系统提示", "请选择博客类型！");
            } else if (content == null || content == '') {
                $.messager.alert("系统提示", "请编辑博客内容！");
            } else {
               //ajax请求 请求后台写博客接口
                $.post("${myblog}/admin/blog/save.do",
                        {
                            'title' : title,
                            'blogType.id' : blogTypeId,
                            'content' : content,
                            'summary' : summary,
                            'keyWord' : keyWord,
                            'contentNoTag' : contentNoTag
                        }, function(result) {
                            if (result.success) {
                                $.messager.alert("系统提示", result.msg);
                                clearValues();
                            } else {
                                $.messager.alert("系统提示", result.msg);
                            }
                        }, "json");
            }
        }
        //清空功能
        function clearValues() {
            $("#title").val("");
            $("#blogTypeId").val("");
            UE.getEditor("editor").setContent("");
            $("#keyWord").val("");
        }
        
        
        
       /*  $.post("${myblog}/admin/blogType/getblogtype.do",{}, function(result) {
                   console.log(result);
                }); */
        
         $.ajax({
			url : "${myblog}/admin/blogType/getblogtype.do",
			type : "post",
			timeout : 10000,
			cache : false,
			success : function(result) {
				$(
						'<option value="' + '' + '">'
								+ '请选择...' + '</option>')
						.appendTo($("#blogTypeId"));
				if(result.success){
					for (var i = 0; i < result.totalcount; i++) {
						$(
								'<option value="' + result.results[i].id + '">'
										+ result.results[i].typeName + '</option>')
								.appendTo($("#blogTypeId"));
					}
				}
			}
		}); 
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    </script>
</body>
</html>