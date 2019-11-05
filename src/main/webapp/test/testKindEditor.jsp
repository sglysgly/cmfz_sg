<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.ready(function(K) {
        K.create('#editor_id', {
            width : '900px',
            height:"600px",
            minWidth:"300",
            minHeight:"500",
            uploadJson:"${path}/editor/upload",
            filePostName:"photo",
            allowFileManager:true,
            fileManagerJson:"${path}/editor/queryAllPhoto",
        });
    });
</script>

<%--初始化富文本编辑器--%>
<textarea id="editor_id" name="content" style="width:700px;height:300px;">
&lt;strong&gt;HTML内容&lt;/strong&gt;
</textarea>