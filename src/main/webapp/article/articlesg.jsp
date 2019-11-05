<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.create('#editor_id', {
        uploadJson:"${path}/editor/upload",
        filePostName:"photo",
        allowFileManager:true,
        fileManagerJson:"${path}/editor/queryAllPhoto",
        afterBlur:function (){  //编辑器失去焦点(blur)时执行的回调函数。
            this.sync();  //将编辑器中的内容同步到表单
        }
    });
</script>

<script>
    $(function () {
        $("#atctable").jqGrid({
            url:"${path}/article/show",
            editurl:"${path}/article/edit",
            datatype:"JSON",
            autowidth:true,
            height:'auto',
            rownumbers:true,
            styleUI:'Bootstrap', //使用BootStrap风格的样式
            rowNum : 2,
            rowList : [ 2,5,10, 20, 30 ],
            pager : '#atcpager',
            viewrecords:true,  //显示总条数
            colNames:['ID','标题','作者','简介','内容','创建时间'],
            colModel:[
                {name : 'id',width : 55,hidden:true},
                {name : 'title',width : 90,align : "center"},
                {name : 'author',width : 100,align : "center"},
                {name : 'synopsis',width : 80,align : "center"},
                {name : 'content',width : 80,hidden:true},
                {name : 'createDate',width : 80,align : "center"},
            ]
        }).jqGrid("navGrid","#atcpager",
            {edit : false,add : false,add : false,search:false,del : true,deltext:"删除"}
            )
    });
    /*点击展示详情*/
    $("#btn1").click(function () {
        //选中一行 获取行Id
        var rowId= $("#atctable").jqGrid("getGridParam","selrow");
        //判断是否选中一行
        if(rowId!=null){
            //根据行Id获取行数据
            var row= $("#atctable").jqGrid("getRowData",rowId);

            //展示模态框
            $("#myModal").modal("show");

            //给inout框设置内容
            $("#title").val(row.title);
            $("#author").val(row.author);
            $("#synopsis").val(row.synopsis);

            //给KindEditor框设置内容
            KindEditor.html("#editor_id",row.content);

            //添加按钮
            $("#modalFooter").html("<button type='button' onclick='updateArticle(\""+rowId+"\")' class='btn btn-default'>提交</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
        }else{
            alert("请选中一行数据");
        }
    });
    /*点击添加文章*/
    $("#btn2").click(function(){

        //给表单置空
        $("#articleFrom")[0].reset();

        //给KindEditor框置空
        KindEditor.html("#editor_id","");

        //展示模态框
        $("#myModal").modal("show");
        //添加按钮
        $("#modalFooter").html("<button type='button' onclick='addArticle()' class='btn btn-default'>提交" +
            "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
    });
    /*修改的提交按钮*/
    function updateArticle(rowId){

        //向后台提交
        $.ajax({
            url:"${path}/article/change?id="+rowId,
            type:"post",
            dateType:"json",
            data:$("#articleFrom").serialize(),
            success:function(){
                $("#myModal").modal('hide');//隐藏模态框
                $("#atctable").trigger("reloadGrid");//刷新jqGrid
            }
        });

    }
    /*点击添加按钮操作*/
    function addArticle(){
        //向后台提交
        $.ajax({
            url:"${path}/article/add",
            type:"post",
            dateType:"json",
            data:$("#articleFrom").serialize(),
            success:function(){
                $("#myModal").modal('hide');//隐藏模态框
                $("#atctable").trigger("reloadGrid");//刷新jqGrid
            }
        });
    }
</script>

<%--初始化一个面板--%>
<div class="panel panel-danger">
    <%--面板头--%>
        <div class="panel panel-heading" align="center">
            <h2>文章信息</h2>
        </div>

        <ul class="nav  nav-pills">
            <li class="active"><a href="#"><b>文章信息</b></a></li>
            <li id="btn1" role="presentation"><a href="#">修改文章</a></li>
            <li id="btn2" role="presentation"><a href="#">添加文章</a></li>
        </ul>

        <%--<div class="panel panel-body">--%>
            <%--<button type="button" id="btn1" class="btn btn-info">修改文章</button>--%>
            <%--<button type="button" id="btn2" class="btn btn-success" >添加文章</button>&emsp;--%>
        <%--</div>--%>

        <%--初始化表单--%>
        <table id="atctable" />

        <%--分页工具栏--%>
        <div id="atcpager" />
</div>

<%--初始化模态框--%>

<div id="myModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document" style="width: 730px">
        <div class="modal-content">
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel" align="center">文章操作</h4>
            </div>
            <%--模态框内容--%>
            <div class="modal-body">
                <form class="form-horizontal" id="articleFrom">

                    <div class="form-group">
                            <label for="title" class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-10">
                                <input type="text" name="title" class="form-control " id="title" placeholder="Text">
                            </div>
                            <%--<div class="input-group">--%>
                            <%--<div class="input-group-addon">标题</div>--%>
                            <%--<input id="title" name="title" type="text" class="form-control " ><br>--%>
                    </div>
                    <div class="form-group">
                        <label for="author" class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-10">
                            <input type="text" name="author" class="form-control " id="author" placeholder="Author">
                        </div>
                        <%--<div class="input-group">--%>
                                <%--<div class="input-group-addon">作者</div>--%>
                                <%--<input id="author" name="author" type="text" class="form-control" ><br>--%>
                            <%--</div>--%>
                    </div>
                    <div class="form-group">
                        <label for="synopsis" class="col-sm-2 control-label">简介</label>
                        <div class="col-sm-10">
                            <input type="text" name="synopsis" class="form-control " id="synopsis" placeholder="Synopsis">
                        </div>
                        <%--<div class="input-group">--%>
                            <%--<div class="input-group-addon">简介</div>--%>
                            <%--<input id="synopsis" name="synopsis" type="text" class="form-control"><br>--%>
                            <%--</div>--%>
                    </div><br>
                    <div class="input-group">
                        <%--初始化富文本编辑器--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                        </textarea>
                    </div>
                </form>
            </div>
            <%--  模态框按钮  --%>
            <div class="modal-footer" id="modalFooter">
                <%--<button type="button" class="btn btn-default">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->