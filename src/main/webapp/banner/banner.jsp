<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script>
    $(function(){
        $("#bntable").jqGrid({
            url : "${path}/banner/queyAll",
            editurl:"${path}/banner/edit",
            datatype : "json",
            rowNum : 2,
            autowidth:true,
            height:"auto",
            styleUI:"Bootstrap",
            rowList : [ 2,4, 6, 8 ],
            pager : '#bnpager',
            viewrecords : true,  //是否展示总条数
            colNames : [ 'Id', '名称', '图片', '描述', '状态','上传时间' ],
            colModel : [
                {name : 'id',width : 55,hidden:true},
                {name : 'title',editable:true,width : 90},
                {name : 'imgPath', id:"imgPath",width : 100,editable:true,edittype:"file",align : "center",
                    formatter:function(cellvalue){
                        return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px' />";
                    },// 返回图片。
                },
                {name : 'description',editable:true,width : 80,align : "center"},
                {name : 'status',width : 80,editable:true,align : "center",edittype:"select",editoptions:{value:"展示:展示;冻结:冻结"},
                    formatter:function(cellvalue,option,row){
                         if(cellvalue=="展示"){
                             //展示状态
                             return "<button class='btn btn-success'>展示</button>";
                         }if(cellvalue=="冻结"){
                            //展示状态
                            return "<button class='btn btn-danger'>冻结</button>";
                        }
                    },
                },
                {name : 'createDate',width : 80,align : "center"}
            ]

        });

        /*增删改查操作*/
        $("#bntable").jqGrid('navGrid', '#bnpager',
            {edit : true,add : true,del : true,search:false,addtext:"添加",edittext:"编辑",deltext:"删除"},
            {
                closeAfterEdit:true,
                beforeShowForm:function (fmt){
                    fmt.find("#imgPath").attr("disabled",true)//禁用input框
                }
            },
            {
                afterSubmit:function(data){
                    //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/banner/uploadBanner",
                        type:"post",
                        dataType:"JSON",
                        fileElementId:"imgPath",
                        data:{id:data.responseText},
                        success:function(){
                            //刷新表单
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    //一定要有返回值，返回什么都行
                    return "hello";
                },
                closeAfterAdd:true
            },
            {}
        );
    });


</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">轮播图信息</a></li>
    </ul>


    <%--初始化表单--%>
    <table id="bntable" />

    <%--定义分页工具栏--%>
    <div id="bnpager"></div>

</div>



