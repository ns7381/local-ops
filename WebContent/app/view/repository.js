define(["common/dialog", 'common/ajax', 'common/alert','common/tool', 'common/validate', 'common/table'], function (Dialog, Ajax, Alert, Tool) {
    var appPage = {};
    appPage.init = function () {
        window.repositoryActionFormatter = function (value, row, index) {
            return [
                '<a class="edit" data-toggle="tooltip" href="javascript:void(0)" title="编辑">',
                '<i class="fa fa-pencil"></i>',
                '</a>',
                /*'<a class="git ml10" data-toggle="tooltip" href="javascript:void(0)" title="检出">',
                '<i class="fa fa-gitlab"></i>',
                '</a>',*/
                '<a class="delete ml10" data-toggle="tooltip" href="javascript:void(0)" title="删除">',
                '<i class="fa fa-trash-o"></i>',
                '</a>'
            ].join('');
        };
        window.repositoryStatusFormatter = function (value, row, index) {
            return '<span class="text-info">' + generateRequestStatus(row.status) + '</span>';
            function generateRequestStatus(status) {
                if (status == null) {
                    return "";
                }
                var label = "";
                switch (status) {
                    case 'CLONED':
                        label = "克隆完成";
                        break;
                    case 'CLONING':
                        label = '<div class="progress progress-striped active">' +
                            '<div class="progress-bar" role="progressbar" style="width: 100%;">' +
                            '<div class="text">正在克隆</div></div>' +
                            '</div>';
                        break;
                    case 'FAIL':
                        label = "克隆失败";
                        break;
                }
                return label;
            }
        };
        window.repositoryActionEvents = {
            'click .edit': function (e, value, row, index) {
                Dialog.modal({
                    title: '编辑仓库',
                    template: 'repository/edit.html',
                    data: row,
                    show: function (dialog) {
                        formValidate(dialog.getModal());
                    },
                    clickOk: function (dialog) {
                        var $modal = dialog.getModal();
                        var valid = $(".form-horizontal", $modal).valid();
                        if (!valid) return false;
                        var repository = $(".form-horizontal", $modal).serializeObject();
                        Ajax.putJson("resource/repository", repository, function () {
                            dialog.close();
                            Alert.success('仓库' + row.repositoryUrl + '修改成功');
                            $("#repository-table").bootstrapTable('refresh');
                        }, function (msg) {
                            Alert.error('仓库' + row.repositoryUrl + "修改失败!原因：" + msg);
                        });
                    }
                });
            },
            'click .git': function (e, value, row, index) {
                Dialog.modal({
                    title: '检出代码',
                    template: 'repository/git.html',
                    data: row,
                    show: function (dialog) {
                        formValidate(dialog.getModal());
                    },
                    clickOk: function (dialog) {
                        var $modal = dialog.getModal();
                        var valid = $(".form-horizontal", $modal).valid();
                        if (!valid) return false;
                        var repository = $(".form-horizontal", $modal).serializeObject();
                        Ajax.putJson("resource/repository", repository, function () {
                            dialog.close();
                            Alert.success('仓库' + row.repositoryUrl + '修改成功');
                            $("#repository-table").bootstrapTable('refresh');
                        }, function (msg) {
                            Alert.error('仓库' + row.repositoryUrl + "修改失败!原因：" + msg);
                        });
                    }
                });
            },
            'click .delete': function (e, value, row, index) {
                Dialog.confirm("确定要删除仓库" + row.repositoryUrl + "吗", function (result) {
                    if (result) {
                        Ajax.delete("resource/repository/" + row.id, function (data) {
                            Alert.success('仓库' + row.repositoryUrl + '删除成功');
                            $("#repository-table").bootstrapTable('refresh');
                        }, function (err) {
                            Alert.error('仓库' + row.repositoryUrl + '删除失败!原因：' + err);
                        });
                    }
                });
            }
        };
        $('#repository-table').bootstrapTable({toolbar: "#repository-toolbar"});
        Tool.webSocket("/messages?routing-key=repo.status", function (event) {
            var payload = JSON.parse(event.data);
            $('#repository-table').bootstrapTable('updateByUniqueId', {id: payload.id, row: payload});
        });
        $('#repository-toolbar').on('click', '#btn-add', function () {
            Dialog.modal({
                title: '新建仓库',
                template: 'repository/add.html',
                shown: function (dialog) {
                    formValidate(dialog.getModal());
                },
                clickOk: function (dialog) {
                    var $modal = dialog.getModal();
                    var valid = $(".form-horizontal", $modal).valid();
                    if (!valid) return false;
                    var repository = $(".form-horizontal", $modal).serializeObject();
                    Ajax.postJson("resource/repository", repository, function () {
                        dialog.close();
                        Alert.success('仓库' + repository.repositoryUrl + '创建成功');
                        $("#repository-table").bootstrapTable('refresh');
                    }, function (msg) {
                        Alert.error('仓库' + repository.repositoryUrl + '创建失败!原因：' + msg);
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