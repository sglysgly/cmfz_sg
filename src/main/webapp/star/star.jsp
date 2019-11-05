<%@page pageEncoding="UTF-8" %>
<script>
    $(function () {
        $("#star-show-table").jqGrid({
            url : '${pageContext.request.contextPath}/star/show',
            datatype : "json",
            height : "auto",
            colNames : [ '编号', '艺名', '真名', '照片', '性别', '生日' ],
            colModel : [
                {name : 'id'},
                {name : 'nickname',editable:true},
                {name : 'name',editable:true},
                {name : 'photo',edittype:"file",editable:true,id:'photo',formatter:function (value,option,rows) {
                        return "<img style='width:100px;height:70px' src='${pageContext.request.contextPath}/upload/photo/"+value+"'>";
                    }},
                {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name : 'bir',editable:true,edittype:"date"}
            ],
            styleUI:'Bootstrap',
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10],
            pager : '#star-page',
            viewrecords : true,
            subGrid : true,
            editurl:"${pageContext.request.contextPath}/star/edit",
            caption : "所有明星列表",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/user/showByStarId?id="+id,
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
                        pager : pager_id,
                        autowidth:true,
                        height : 'auto'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },

        }).jqGrid('navGrid', '#star-page', {
            add : true,
            edit : true,
            del : true,
            search:false,
            addtext:"添加",
            deltext:"删除",
            edittext:"修改"
        },{
            closeAfterEdit:true,
            beforeShowForm:function (fmt){
                fmt.find("#photo").attr("disabled",true)//禁用input框
            }
            },{
            afterSubmit:function(data){
                //文件上传
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/star/upload",
                    type:"post",
                    dataType:"JSON",
                    fileElementId:"photo",
                    data:{id:data.responseText},
                    success:function(){
                        //刷新表单
                        $("#star-show-table").trigger("reloadGrid");
                    }
                })
                //一定要有返回值，返回什么都行
                return "hello";
            },
            closeAfterAdd:true
            },{}
        );
    })
</script>


<div class="panel page-header">
            <h3>展示所有的明星</h3>
        </div>
        <table id="star-show-table"></table>
        <div id="star-page" style="height: 40px;">

    </div>