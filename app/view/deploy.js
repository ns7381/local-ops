define(["common/dialog", 'common/ajax', 'common/alert', 'common/validate', 'common/table'], function (Dialog, Ajax, Alert) {
    var appPage = {};
    appPage.init = function () {
        var renderData = {};
        Ajax.get('location', function (data) {
            renderData.location = data;
        });
        Ajax.get('topology', function (data) {
            renderData.topology = data;
        });
        window.actionFormatter = function (value, row, index) {
            return [
                '<a class="delete ml10" data-toggle="tooltip" href="javascript:void(0)" title="删除">',
                '<i class="fa fa-trash-o"></i>',
                '</a>'
            ].join('');
        };
        window.nameFormatter = function (value, row, index) {
            return '<a href="#/deploy/detail?deployId=' + row.id + '&deployName=' + row.name+ 
                '&locationId=' + row.locationId +'&locationName=' + row.locationName +
                '&deploymentTopologyId=' + row.deploymentTopologyId +'&deploymentTopologyName=' + row.deploymentTopologyName + '">' + row.name + '</a>';
        };
        window.actionEvents = {
            'click .delete': function (e, value, row, index) {
                Dialog.confirm("确定要删除应用" + row.name + "吗", function (result) {
                    if (result) {
                        Ajax.delete("deployment/" + row.id, function (data) {
                            Alert.success('应用' + row.name + '删除成功');
                            $("#appTable").bootstrapTable('refresh');
                        }, function (err) {
                            Alert.error('应用' + row.name + '删除失败!原因：' + err);
                        });
                    }
                });
            }
        };
        $('#appTable').bootstrapTable();
        $('#toolbar').on('click', '#btn-add', function () {
            Dialog.modal({
                title: '新建应用',
                template: 'deploy/add.html',
                data: renderData,
                shown: function (dialog) {
                    formValidate(dialog.getModal());
                },
                clickOk: function (dialog) {
                    var $modal = dialog.getModal();
                    var valid = $(".form-horizontal", $modal).valid();
                    if (!valid) return false;
                    var deployment = $(".form-horizontal", $modal).serializeObject();
                    $.each(renderData.location, function (k, v) {
                        if (deployment.locationId == v.id) {
                            deployment.locationName = v.name;
                        }
                    });
                    $.each(renderData.topology, function (k, v) {
                        if (deployment.deploymentTopologyId == v.id) {
                            deployment.deploymentTopologyName = v.name;
                        }
                    });
                    Ajax.postJson("deployment", deployment, function () {
                        dialog.close();
                        Alert.success('应用' + deployment.name + '创建成功');
                        $("#appTable").bootstrapTable('refresh');
                    }, function (msg) {
                        Alert.error('应用' + deployment.name + '创建失败!原因：' + msg);
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