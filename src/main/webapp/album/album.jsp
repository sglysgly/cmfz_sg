<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>
    $(function(){

        $("#abtable").jqGrid({
                url : "${path}/album/album.json",
                datatype : "json",
                autowidth:true,
                height : "auto",
                styleUI:"Bootstrap",
                rowNum : 8,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#abpager',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                colNames : [ 'Id', '名字', '作者', '封面','集数', '发布时间' ],
                colModel : [
                    {name : 'id',index : 'id',  width : 55},
                    {name : 'title',index : 'invdate',width : 90},
                    {name : 'author',index : 'name',width : 100},
                    {name : 'cover_img',index : 'amount',width : 80,align : "right",edittype:"file",
                        editoptions:{enctype:"multipart/from-data"}
                    },
                    {name : 'count',index : 'tax',width : 80,align : "right"},
                    {name : 'pub_date',index : 'total',width : 80,align : "right"}
                ],
                subGrid : true, //开启子表格支持
                //subgrid_id  子表格的Id,当开启子表格式,会在主表格中创建一个子表格，subgrid_id就是这个子表格的Id
                subGridRowExpanded : function(subgridId, rowId) {
                    addSubGrid(subgridId,rowId);
                },
                /*subGridRowColapsed : function(subgrid_id, row_id) {
                    // this function is called before removing the data
                    //var subgrid_table_id;8i
                    //subgrid_table_id = subgrid_id+"_t";
                    //jQuery("#"+subgrid_table_id).remove();
                }*/
            });
        /*增删改查操作*/
        $("#abtable").jqGrid('navGrid', '#abpager',
            {add : false,edit : false,del : false}
        );

    });

    //创建子表单
    function addSubGrid(subgridId,rowId) {

        var subgridTableId = subgridId + "table";
        var pagerId = subgridId+"pager";

        //初始化表单,分页工具栏
        $("#" + subgridId).html("<table id='" + subgridTableId+ "'/><div id='"+ pagerId + "'/>");

        //创建表单
        $("#" + subgridTableId).jqGrid({
                //url:"/chapter/queryByPage?albumId="+rowId,
                url:"${path}/album/chapter.json",
                datatype : "json",
                rowNum : 20,
                pager : "#"+pagerId,
                sortname : 'num',
                sortorder : "asc",
                autowidth:true,
                height : "auto",
                styleUI:"Bootstrap",
                colNames : [ 'Id', '名字', '音频大小', '音频时长','上传时间' ],
                colModel : [
                    {name : "id",  index : "num",width : 80,key : true},
                    {name : "url",index : "item",  width : 130},
                    {name : "size",index : "qty",width : 70,align : "right"},
                    {name : "duration",index : "unit",width : 70,align : "right"},
                    {name : "up_date",index : "total",width : 70,align : "right"}
                ]
            });

        /*子表格增删改查*/
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
            {edit : false,add : false,del : false}
        );
    }

</script>

<div class="panel panel-success">

    <div class="panel panel-heading">专辑信息</div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">专辑信息</a></li>
    </ul>

    <table id="abtable" />

    <div id="abpager" />

</div>
