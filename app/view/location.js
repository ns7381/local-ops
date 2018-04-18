define(["common/dialog", 'common/ajax', 'common/alert', 'common/validate', 'common/table'], function (Dialog, Ajax, Alert) {
    var appPage = {};
    appPage.init = function () {
        window.actionFormatter = function (value, row, index) {
            return [
                '<a class="edit" data-toggle="tooltip" href="javascript:void(0)" title="编辑">',
                '<i class="fa fa-pencil"></i>',
                '</a>',
               /* '<a class="ml10" data-toggle="tooltip" href="#/app/version?app_id=' + row.id + '&app_name=' + row.name + '" title="版本">',
                '<i class="fa fa-id-card"></i>',
                '</a>',
                '<a class="ml10" data-toggle="tooltip" href="#/app/install?app_id=' + row.id + '&app_name=' + row.name + '" title="部署">',
                '<i class="fa fa-linkedin"></i>',
                '</a>',*/
                '<a class="delete ml10" data-toggle="tooltip" href="javascript:void(0)" title="删除">',
                '<i class="fa fa-trash-o"></i>',
                '</a>'
            ].join('');
        };
        window.nameFormatter = function (value, row, index) {
            return '<a href="#/location/host?locationId=' + row.id + '&locationName=' + row.name + '">' + row.name + '</a>';
        };
        window.typeFormatter = function (value, row, index) {
            return row.type == "dev" ? "开发环境" : row.type == "test" ? "测试环境" : "生产环境";
        };
        window.actionEvents = {
            'click .edit': function (e, value, row, index) {
                Dialog.modal({
                    title: '编辑环境',
                    template: 'location/edit.html',
                    data: row,
                    show: function (dialog) {
                        formValidate(dialog.getModal());
                    },
                    clickOk: function (dialog) {
                        var $modal = dialog.getModal();
                        var valid = $(".form-horizontal", $modal).valid();
                        if (!valid) return false;
                        var location = $(".form-horizontal", $modal).serializeObject();
                        Ajax.putJson("location", location, function () {
                            dialog.close();
                            Alert.success('环境' + row.name + '修改成功');
                            $("#locationTable").bootstrapTable('refresh');
                        }, function (msg) {
                            Alert.error('环境' + row.name + "修改失败!原因：" + msg);
                        });
                    }
                });
            },
            'click .delete': function (e, value, row, index) {
                Dialog.confirm("确定要删除环境" + row.name + "吗", function (result) {
                    if (result) {
                        Ajax.delete("location/" + row.id, function (data) {
                            Alert.success('环境' + row.name + '删除成功');
                            $("#locationTable").bootstrapTable('refresh');
                        }, function (err) {
                            Alert.error('环境' + row.name + '删除失败!原因：' + err);
                        });
                    }
                });
            }
        };
        $('#locationTable').bootstrapTable();
        $('#toolbar').on('click', '#btn-add', function () {
            Dialog.modal({
                title: '新建环境',
                template: 'location/add.html',
                shown: function (dialog) {
                    formValidate(dialog.getModal());
                },
                clickOk: function (dialog) {
                    var $modal = dialog.getModal();
                    var valid = $(".form-horizontal", $modal).valid();
                    if (!valid) return false;
                    var location = $(".form-horizontal", $modal).serializeObject();
                    Ajax.postJson("location", location, function () {
                        dialog.close();
                        Alert.success('环境' + location.name + '创建成功');
                        $("#locationTable").bootstrapTable('refresh');
                    }, function (msg) {
                        Alert.error('环境' + location.name + '创建失败!原因：' + msg);
                    });
                }
            });
        });
    };
    function formValidate($modal) {
        return $(".form-horizontal", $modal).validate({
            rules: {
                'name': {
                    required: true,
                    maxlength: 128
                },
                'description': {
                    maxlength: 1024
                }
            }
        });
    }

    return appPage;
}); 