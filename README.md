# single-page-app
## 前端框架： 
          单页面应用
          requirejs模块化编程
          内置bootstrap、bootstrap3-dialog、bootstrap-fileinput、bootstrap-table

## 目录结构：  
           --app           用户代码部分
             --image       图片
             --view        页面视图，包含页面js和html
           --public        框架代码部分
             --dist        引用外部资源
             --lib         前端js库
             --config.js   requirejs配置部分
           --WEB-INF       默认提供的一种jsp启动模式，用户可使用其他方式
           
## 页面入口： 
            WEB-INF/views/index.jsp

## 视图：
          约定大于配置，默认A.js加载A.html的模板
          使用requirejs写法，返回一个包含init方法的json对象
## Ajax:     
          Ajax.get/put/putJson/postJson
          示例:
          `Ajax.postJson("deployment", deployment, function () {
              dialog.close();
              Alert.success('应用' + deployment.name + '创建成功');
              $("#appTable").bootstrapTable('refresh');
          }, function (msg) {
              Alert.error('应用' + deployment.name + '创建失败!原因：' + msg);
          });`
## Dialog/Table
          [bootstrap3-dialog](http://vadimg.com/twitter-bootstrap-wizard-example/#examples)
          示例：
          `
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
          `
          [bootstrap-table](http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation)
          示例：
          `
          $('#appTable').bootstrapTable();
          window.actionFormatter = function (value, row, index) {
              return [
                  '<a class="delete ml10" data-toggle="tooltip" href="javascript:void(0)" title="删除">',
                  '<i class="fa fa-trash-o"></i>',
                  '</a>'
              ].join('');
          };
          `

