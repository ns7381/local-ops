define(["common/dialog", 'common/ajax', 'common/tool', 'common/alert', "bs/datetimepicker", 'common/validate', 'common/table', 'jq/fileDownload'], function (Dialog, Ajax, Tool, Alert) {
    var appPage = {};
    appPage.init = function () {
        window.licenseActionFormatter = function (value, row, index) {
            return [
                '<a class="edit" data-toggle="tooltip" href="javascript:void(0)" title="编辑">',
                '<i class="fa fa-pencil"></i>',
                '</a>',
                '<a class="delete ml10" data-toggle="tooltip" href="javascript:void(0)" title="删除">',
                '<i class="fa fa-window-close"></i>',
                '</a>'
            ].join('');
        };
        window.licenseActionEvents = {
            'click .edit': function (e, value, row, index) {
                Dialog.modal({
                    title: '编辑',
                    template: 'license/edit.html',
                    data: row,
                    shown: function (dialog) {
                        formValidate(dialog.getModal());
                        $('.form_datetime').datetimepicker({
                            autoclose: true,
                            pickerPosition: "bottom-left"
                        });
                    },
                    clickOk: function (dialog) {
                        var $modal = dialog.getModalBody();
                        var valid = $(".form-horizontal", $modal).valid();
                        if (!valid) return false;
                        var reqData = $(".form-horizontal", $modal).serializeObject();
                        Ajax.putJson("license", reqData, function () {
                            dialog.close();
                            Alert.success('授权信息' + row.name + '修改成功');
                            $("#licenseTable").bootstrapTable('refresh');
                        }, function (msg) {
                            Alert.error('授权信息' + row.name + '修改失败!原因：' + msg);
                        });
                    }
                });
            },
            'click .delete': function (e, value, row, index) {
                Dialog.confirm("确定要删除" + row.name + "吗", function (result) {
                    if (result) {
                        Ajax.delete("license/" + row.id, function (data) {
                            Alert.success('授权信息' + row.name + '删除成功');
                            $("#licenseTable").bootstrapTable('refresh');
                        }, function (err) {
                            Alert.error('授权信息' + row.name + '删除失败!原因：' + err);
                        });
                    }
                })

            }
        };
        $('#licenseTable').bootstrapTable({toolbar: "#licenseTable-toolbar"});
        $('#licenseTable-toolbar').on('click', '#btn-add', function () {
            Dialog.modal({
                title: '新建授权信息',
                template: 'license/add.html',
                shown: function (dialog) {
                    formValidate(dialog.getModalContent());
                    $('.form_datetime').datetimepicker({
                        autoclose: true,
                        pickerPosition: "bottom-left"
                    });
                },
                clickOk: function (dialog) {
                    var $modal = dialog.getModal();
                    var valid = $(".form-horizontal", $modal).valid();
                    if (!valid) return false;
                    var reqData = $(".form-horizontal", $modal).serializeObject();
                    reqData.appId = appId;
                    Ajax.postJson("license", reqData, function () {
                        dialog.close();
                        Alert.success('授权信息' + reqData.name + '创建成功');
                        $("#licenseTable").bootstrapTable('refresh');
                    }, function (msg) {
                        Alert.error('授权信息' + reqData.name + '创建失败!原因：' + msg);
                    });
                }
            });
        });
        $('#licenseTable-toolbar').on('click', '#btn-license', function () {
            Dialog.confirm("确定要将导出证书吗", function (result) {
                if (result) {
                    $.fileDownload('license/file',{
                        successCallback: function (url) {
                            Alert.success('证书导出成功');
                        },
                        failCallback: function (html, url) {
                            Alert.error('证书导出失败');
                        }
                    });
                }
            });
        });
    };
    function formValidate($modal) {
        return $(".form-horizontal", $modal).validate({
            rules: {
                'orgCode': {
                    required: true
                },
                'orgName': {
                    required: true
                },
                'serverLimit': {
                    required: true,
                    digits: true
                },
                'serviceLimit': {
                    required: true,
                    digits: true
                },
                'endDate': {
                    required: true
                }
            }
        });
    }
    return appPage;
}); 