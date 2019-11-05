<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#bntable").jqGrid({
            url:"${pageContext.request.contextPath}/banner/queyAll",
            editurl:"${pageContext.request.contextPath}/banner/edit",
            datatype:"json",
            rowNum: 2,
            autowidth:true,
            styleUI:"Bootstrap",
            rowList : [2,4,6,8],
            pager:"#bnpage",
            viewrecords:true,
            colNames:['id','标题','图片','描述','状态','创建日期'],
            colModel:[
                {name:'id',width:55,hidden:true},
                {name:'title',editable:true,width:50},
                {name:'imgPath',id:'cover',editable:true,width:100,edittype:"file",align:"center",
                    formatter:function(cellvalue){
                        return "<img src='${pageContext.request.contextPath}/upload/photo/"+cellvalue+"' style='width:180px;height:80px' />"
                    }
                },
                {name:'description',editable:true,width:50,},
                {name:'status',width:80,align:"center",edittype:"select",editoptions:{value:"展示:展示;冻结:冻结"},editable:true,
                    formatter:function (cellvalue) {
                        if(cellvalue=="展示"){
                            return "<button class='btn btn-success'>展示</button>"
                        }
                        if(cellvalue=="冻结"){
                            return "<button class='btn btn-danger'>冻结</button>"
                        }
                    }
                },
                {name:'createDate',width:80,align:"center"}
            ]
        }).jqGrid("navGrid","#bnpage",
            {edit:true,add:true,del:true,edittext:"编辑",addtext:"添加",deltext:"删除"},
             {
                closeAfterEdit:true,
                beforeShowForm:function (data) {
                    data.find("#cover").attr("disabled",true)
                }
            },
            {
                closeAfterAdd:true,
                afterSubmit:function (data) {
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        dataType:"JSON",
                        type:"post",
                        fileElementId:"cover",
                        data:{id:data.responseText},
                        success:function () {
                            $("#bntable").trigger("reloadGrid");
                        }
                    })
                    return "sg";
                }
            },
            { }
        )
    })
</script>

<div class="panel panel-info">
    <div class="panel panel-heading" align="center">
        <h2>轮播图信息</h2>
    </div>

    <table id="bntable"></table>
    <div id="bnpage"></div>
</div>