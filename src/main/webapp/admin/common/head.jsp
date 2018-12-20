<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set value="${pageContext.request.contextPath }" var="myblog" />
<!-- 下面是easyui的环境 -->
<link rel="stylesheet" href="${myblog }/static/jquery-easyui-1.6.10/themes/icon.css" type="text/css"></link>
<link rel="stylesheet" href="${myblog }/static/jquery-easyui-1.6.10/themes/bootstrap/easyui.css" type="text/css"></link>
<script type="text/javascript" src="${myblog}/static/jquery-easyui-1.6.10/jquery.min.js"></script>
<script type="text/javascript" src="${myblog}/static/jquery-easyui-1.6.10/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${myblog}/static/jquery-easyui-1.6.10/locale/easyui-lang-zh_CN.js"></script>