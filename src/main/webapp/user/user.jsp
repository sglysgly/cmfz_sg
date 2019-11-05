<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){
        $("#ustable").jqGrid({
            url : "${pageContext.request.contextPath}/user/show",
            datatype : "json",
            colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','地址','签名' ],
            colModel : [
                {name : "id"},
                {name : "username"},
                {name : "nickname"},
                {name : "picImg",formatter:function (cellvalue) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/upload/photo/"+cellvalue+"'>";
                    }},
                {name : "phone"},
                {name : "sex"},
                {name : "province",formatter:function (cellValue,options,rows) {
                        return cellValue+"---"+rows.city
                    }},
                {name : "sign"}
            ],
            styleUI:"Bootstrap",
            rowNum : 2,
            rowList : [ 2, 4, 10],
            viewrecords : true,
            pager : uspager,
            autowidth:true,
            height : 'auto'
        });

        /*增删改查操作*/
        $("#ustable").jqGrid('navGrid', '#uspager',
            {edit : false,add : false,del : false}
        );
    });
    function downOut(){
        location.href="${path}/user/downOut";
    }
    function uploadOut(){
        //向后台提交
        $.ajax({
            url:"${path}/user/uploadOut",
            type:"post",
            dateType:"json",
            success:function(data){
                $("#ustable").trigger("reloadGrid");
            }
        });
    }
    $("#input1").click(function () {
        $.ajax({
            url:"${path}/user/upload",
            type:"post",
            data:$("#from1").serialize(),
            dataType:"json",
            success:function(result){
                $("#ustable").trigger("reloadGrid");//刷新jqGrid
            }
        });
    })


</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" id="btn" class="btn btn-info" onclick="downOut()" >导出用户信息</button>
        <%--<form action="" id="form1" class="form-inline">--%>
            <%--<div class="form-group">--%>
                <%--<div class="input-group">--%>
                    <%--<div class="input-group-addon">选择表格</div>--%>
                    <%--<input type="file" class="form-control" id="exampleInputAmount" placeholder="Amount">--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<button type="submit" id="input1" class="btn btn-primary">导入用户信息</button>--%>
        <%--</form>--%>
    </div>

    <%--提示信息--%>
    <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong id="showMsg"></strong>
    </div>

    <%--初始化表单--%>
    <table id="ustable" />

    <%--定义分页工具栏--%>
    <div id="uspager"></div>

</div>



