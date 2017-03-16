/**
 * Created by Administrator on 2016/11/15.
 */
define(['bs/table', 'bs/tooltip'], function (bootstrapTable) {
    if ($.fn.bootstrapTable) {
        $.extend(true, $.fn.bootstrapTable.defaults, {
            queryParams: function (params) {
                return $.extend({}, params);    //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，本项目中用于自定义表格的查询，于别的文章详细阐述。
            },
            toolbar: "#toolbar",  //一个jQuery 选择器，指明自定义的toolbar（工具栏），将需要的功能放置在表格工具栏（默认）位置。
            sidePagination: "client",  //设置在哪里进行分页，可选值为 'client' 或者 'server'。设置 'server'时，必须设置 服务器数据地址（url）或者重写ajax方法
            pageNumber: 1,    //如果设置了分页，首页页码
            pageSize: 10,   //如果设置了分页，页面数据条数
            pageList: [
                10, 20, 50, 100, 200   //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
            ],
            pagination: true,  //设置为 true 会在表格底部显示分页条
            showRefresh: true, //显示 刷新按钮
            showColumns: true, //是否显示 内容列下拉框
            uniqueId: "id", //是否显示 内容列下拉框
            showToggle: true,
            showExport: true,
            search: true,   //是否启用搜索框   
            onAll: function () {
                $('[data-toggle="tooltip"]').tooltip();
            }
        });
    }
    return bootstrapTable;
});