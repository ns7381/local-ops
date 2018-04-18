/*
 * 针对bootstrap-dialog 模态对话框的二次封装。
 */
define(['bs/dialog', 'template'], function (BootstrapDialog, Template) {
    var Dialog = $.extend({}, BootstrapDialog);
    Dialog.modal = function (options) {
        var tplBasePath = "app/view/";
        var bootstrapOpt = {
            title: options.title,
            message: options.message,
            nl2br: options.nl2br || false,
            closeByBackdrop: false,
            closeByKeyboard: false,
            buttons: options.buttons || [{
                label: '取消',
                cssClass: 'btn-default',
                action: function (dialog) {
                    dialog.close();
                }
            }, {
                label: '确定',
                cssClass: 'btn-primary',
                action: function (dialog) {
                    options.clickOk(dialog);
                }
            }],
            onshow: function (dialogRef) {
                typeof options.show == 'function' && options.show(dialogRef);
            },
            onshown: function (dialogRef) {
                typeof options.shown == 'function' && options.shown(dialogRef);
            },
            onhide: function (dialogRef) {
                typeof options.hide == 'function' && options.hide(dialogRef);
            },
            onhidden: function (dialogRef) {
                typeof options.hidden == 'function' && options.hidden(dialogRef);
            }
        };
        if (options.template) {
            $.get(tplBasePath + options.template, function (html) {
                var render = Template.compile(html);
                bootstrapOpt.message = render(options.data);
                return BootstrapDialog.show(bootstrapOpt);
            });
        } else {
            return BootstrapDialog.show(bootstrapOpt);
        }
    };
    return Dialog;
});