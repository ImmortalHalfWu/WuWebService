
//JavaScript代码区域
layui.use('element', function(){
  var element = layui.element;

});

layui.use('layer', function () {
  var layer = layui.layer;
  layer.msg('123');
  // layer.open({
  //   type: 1,
  //   title:'12',
  //   content: '传入任意的文本或html' //这里content是一个普通的String
  // });

  // layer.open({
  //   type: 3,
  //   title:'12',
  //   content: '传入任意的文本或html' //这里content是一个普通的String
  // });

  // layer.open({
  //   type: 4,
  //   skin: 'layui-layer-molv',
  //   content: ['内容', '#testId'], //数组第二项即吸附元素选择器或者DOM
  //   tips:[2, '#EEF0F6']
  // });

  // layer.confirm('纳尼？', {
  //   btn: ['按钮一', '按钮二', '按钮三'] //可以无限个按钮
  //   ,btn3: function(index, layero){
  //     //按钮【按钮三】的回调
  //   }
  // }, function(index, layero){
  //   //按钮【按钮一】的回调
  // }, function(index){
  //   //按钮【按钮二】的回调
  // });
});


//列表数据
layui.use('flow', function(){
    var flow = layui.flow;
    flow.load({
        elem: '#LAY_demo1' //流加载容器
        ,mb:150
        ,scrollElem: '#LAY_demo1' //滚动条所在元素，一般不用填，此处只是演示需要。
        ,done: function(page, next){ //执行下一页的回调
            //模拟数据插入
            setTimeout(function(){
                var lis = [];
                for(var i = 0; i < 1; i++){
                    lis.push('<button style="font-size:30px; color: #2E2D3C">123123</button></br>'+ ( (page-1)*1 + i + 1 ) +'</button>')
                }
                //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                next(lis.join(''), page < 10); //假设总页数为 10
                mySwiper.slideTo(0);
            }, 500);
        }
    });
});

function navClick(index) {
  print('点击导航下标：' + index);
  if (typeof index === "number") {
    mySwiper.slideTo(index);
  }

}

function thisClick() {
  layer.msg('123');
}

function print(text) {
  console.log(text);
}
