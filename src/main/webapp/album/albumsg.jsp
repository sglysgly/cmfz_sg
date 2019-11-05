<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<script>
   $(function () {
       jQuery("#abtable").jqGrid(
           {
               url :"${path}/album/show",
               datatype : "json",
               editurl:"${path}/album/edit",
               autowidth:true,
               height : "auto",
               styleUI:"Bootstrap",
               rowNum : 2,
               rowList : [ 2, 4, 6, 8 ],
               pager : '#abpager',
               sortname : 'id',
               viewrecords : true,
               multiselect : false,
               colNames : [ 'Id', '名字', '作者', '封面','集数','内容', '发布时间'],
               colModel : [
                   {name : 'id',align : "center",index : 'id',  width : 55},
                   {name : 'title',align : "center",editable:true,index : 'invdate',width : 90},
                   {name : 'author',align : "center",editable:true,index : 'name',width : 100,edittype:"select",editoptions:{
                           dataUrl:"${path}/album/showList"
                       }
                   },
                   {name : 'cover',id:"cover",edittype:"file",editable:true,index : 'amount',width : 80,align : "center",
                       formatter:function (cellvalue) {
                           return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/upload/photo/"+cellvalue+"'>";
                       }
                   },
                   {name : 'counts',editable:true,index : 'tax',width : 80,align : "center"},
                   {name : 'content',editable:true,index : 'total',width : 80,align : "center"},
                   {name : 'createDate',index : 'total',width : 80,align : "center"}
               ],
               subGrid: true,//开启子表格支持
               subGridRowExpanded : function(subgridId, rowId) {
                   //调用子表函数
                   addSubGrid(subgridId,rowId);
               }

           }).jqGrid("navGrid","#abpager",
           {add:true,edit:true,del:true,addtext:"添加",edittext:"修改",deltext:"删除"},
           {
               closeAfterEdit:true,
               beforeShowForm:function (fmt){
                   fmt.find("#cover").attr("disabled",true)//禁用input框
               }
           },
           {
               afterSubmit:function(data){
                   //文件上传
                   $.ajaxFileUpload({
                       url:"${pageContext.request.contextPath}/album/upload",
                       type:"post",
                       dataType:"JSON",
                       fileElementId:"cover",
                       data:{id:data.responseText},
                       success:function(){
                           //刷新表单
                           $("#abtable").trigger("reloadGrid");
                       }
                   })
                   //一定要有返回值，返回什么都行
                   return "hello";
               },
               closeAfterAdd:true
           }
           )
   });
    //创建子表
    function addSubGrid(subgridId,rowId) {
        //初始化表格的id
        var subgridTableId = subgridId + "table";
        //初始化分页的id
        var pagerId = subgridId + "pager";

        //进行子表初始化
        $("#"+subgridId).html("<table id='"+subgridTableId+"'/><div id='"+pagerId+"'/>");
        //键表
        $("#"+subgridTableId).jqGrid(
            {
                url:"${path}/chapter/show?id="+rowId,
                datatype:"JSON",
                editurl:"${path}/chapter/edit?id="+rowId,
                rowNum:2,
                pager:"#"+pagerId,
                sortname:"num",
                sortorder:"asc",
                rowNum : 2,
                rowList : [ 2, 4, 6, 8 ],
                viewrecords : true,
                autowidth:true,
                height:"auto",
                styleUI:"Bootstrap",
                colNames:[ 'Id', '名字', '音频大小', '音频时长','上传时间','操作'],
                colModel:[
                    {name : "id",  index : "num",width : 80,key : true},
                    {name : "url",id:"url",editable:true,edittype:"file",index : "item",  width : 130},
                    {name : "sizes",index : "qty",width : 70,align : "center"},
                    {name : "duration",index : "unit",width : 70,align : "center"},
                    {name : "createDate",index : "total",width : 70,align : "center"},
                    {name : "url",width : 70,align : "center",formatter:function (cellvalue) {
                            return "<a href='#' onclick='ployerChapter(\""+cellvalue+"\")'><span class='glyphicon glyphicon-play-circle'/> &nbsp;&nbsp;" +
                                "<a href='#' onclick='downloadChapter(\""+cellvalue+"\")'><span class='glyphicon glyphicon-download'/>"

                        }}
                ]
            }
        ).jqGrid("navGrid","#"+pagerId,{
            add:true,edit:false,del:true,addtext:"添加",deltext:"删除"
        },{},{
            afterSubmit:function(data){
                //文件上传
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/chapter/upload",
                    type:"post",
                    dataType:"JSON",
                    fileElementId:"url",
                    data:{id:data.responseText},
                    success:function(){
                        //刷新表单
                        $("#abtable").trigger("reloadGrid");
                    }
                })
                //一定要有返回值，返回什么都行
                return "hello";
            },
            closeAfterAdd:true
        })

    }
    
    function downloadChapter(fileName) {
        location.href="${path}/chapter/download?fileName="+fileName;
    }
    
    function ployerChapter(fileName) {
        //展示模态框
        $("#audioModel").modal("show");
        //播放
        $("#playerAudio").attr("src","${path}/upload/music/"+fileName);
    }
</script>

<div class="panel panel-success">
    <div class="panel panel-heading" align="center">专辑信息</div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">专辑信息</a> </li>
    </ul>

    <table id="abtable"></table>
    <div id="abpager"></div>

    <div class="modal fade" id="audioModel" tabindex="-1" role="dialog">
        <div  class="modal-dialog" role="document">
            <div class="modal-content">
               <audio id="playerAudio" src="#" controls="controls"/>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>




















