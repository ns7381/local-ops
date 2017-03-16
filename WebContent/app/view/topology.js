define(["common/dialog", 'common/ajax', 'common/alert', 'common/validate', 'common/table'], function (Dialog, Ajax, Alert) {
    var appPage = {};
    appPage.init = function () {
        window.actionFormatter = function (value, row, index) {
            return [
                '<a class="edit" data-toggle="tooltip" href="javascript:void(0)" title="编辑">',
                '<i class="fa fa-pencil"></i>',
                '</a>',
                '<a class="delete ml10" data-toggle="tooltip" href="javascript:void(0)" title="删除">',
                '<i class="fa fa-trash-o"></i>',
                '</a>'
            ].join('');
        };
        window.nameFormatter = function (value, row, index) {
            return '<a href="#/topology/node?topologyId=' + row.id + '&topologyName=' + row.name + '">' + row.name + '</a>';
        };
        window.actionEvents = {
            'click .edit': function (e, value, row, index) {
                Dialog.modal({
                    title: '编辑拓扑',
                    template: 'topology/edit.html',
                    data: row,
                    show: function (dialog) {
                        formValidate(dialog.getModal());
                    },
                    clickOk: function (dialog) {
                        var $modal = dialog.getModal();
                        var valid = $(".form-horizontal", $modal).valid();
                        if (!valid) return false;
                        var topology = $(".form-horizontal", $modal).serializeObject();
                        Ajax.putJson("topology", topology, function () {
                            dialog.close();
                            Alert.success('拓扑' + row.name + '修改成功');
                            $("#appTable").bootstrapTable('refresh');
                        }, function (msg) {
                            Alert.error('拓扑' + row.name + "修改失败!原因：" + msg);
                        });
                    }
                });
            },
            'click .delete': function (e, value, row, index) {
                Dialog.confirm("确定要删除拓扑" + row.name + "吗", function (result) {
                    if (result) {
                        Ajax.delete("topology/" + row.id, function (data) {
                            Alert.success('拓扑' + row.name + '删除成功');
                            $("#appTable").bootstrapTable('refresh');
                        }, function (err) {
                            Alert.error('拓扑' + row.name + '删除失败!原因：' + err);
                        });
                    }
                });
            }
        };
        $('#appTable').bootstrapTable();
        $('#toolbar').on('click', '#btn-add', function () {
            Dialog.modal({
                title: '新建拓扑',
                template: 'topology/add.html',
                shown: function (dialog) {
                    formValidate(dialog.getModal());
                },
                clickOk: function (dialog) {
                    var $modal = dialog.getModal();
                    var valid = $(".form-horizontal", $modal).valid();
                    if (!valid) return false;
                    var topology = $(".form-horizontal", $modal).serializeObject();
                    Ajax.postJson("topology", topology, function () {
                        dialog.close();
                        Alert.success('拓扑' + topology.name + '创建成功');
                        $("#appTable").bootstrapTable('refresh');
                    }, function (msg) {
                        Alert.error('拓扑' + topology.name + '创建失败!原因：' + msg);
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