//存放主要的交互逻辑js代码
//javascipt模块化

var seckill={
		//封装秒杀相关ajax的url
		basePath:'/seckill-master',
		URL:{
			now:function(){
				return seckill.basePath+'/seckill/time/now';
			},
			exposer:function(seckillId){
				return seckill.basePath+'/seckill/'+seckillId+'/exposer';
			},
			execution:function(seckillId,md5){
				return seckill.basePath+'/seckill/'+seckillId+'/'+md5+'/execution';
			}
		},
		validatePhone:function (phone){
			if(phone&&phone.length==11&&!isNaN(phone)){
				return true;
			}else{
				return false;
			}
		},
		
		countDown:function (seckillId,nowTime,startTime,endTime){
			var seckillBox=$('#seckill-box');
			if(nowTime>endTime){
				//秒杀结束
				seckillBox.html('秒杀结束！');
			}else if(nowTime<startTime){
				//秒杀未开始
				var killTime=new Date(startTime+1000);
				seckillBox.countdown(killTime,function(event){
					//时间格式
					var format=event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
					seckillBox.html(format);
				}).on('finish.countdown',function(){
					seckill.handlerSeckill(seckillId,seckillBox);
				});
			}else{
				//秒杀开始
				seckill.handlerSeckill(seckillId,seckillBox);
			}
		},

		handlerSeckill: function (seckillId, node) {
	        //获取秒杀地址,控制显示器,执行秒杀
	        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');

	        $.get(seckill.URL.exposer(seckillId), {}, function (result) {
	            //在回调函数种执行交互流程
	            if (result && result['success']) {
	                var exposer = result['data'];
	                if (exposer['exposed']) {
	                    //开启秒杀
	                    //获取秒杀地址
	                    var md5 = exposer['md5'];
	                    var killUrl = seckill.URL.execution(seckillId, md5);
	                    console.log("killUrl: " + killUrl);
	                    //绑定一次点击事件
	                    $('#killBtn').one('click', function () {
	                        //执行秒杀请求
	                        //1.先禁用按钮
	                        $(this).addClass('disabled');//,<-$(this)===('#killBtn')->
	                        //2.发送秒杀请求执行秒杀
	                        $.post(killUrl, {}, function (result) {
	                            if (result) {
	                                var killResult = result['data'];
	                                var state = killResult['state'];
	                                var stateInfo = killResult['stateInfo'];
	                                //显示秒杀结果
	                                node.html('<span class="label label-success">' + stateInfo + '</span>');
	                            }
	                        });
	                    });
	                    node.show();
	                    
	                } else {
	                    //未开启秒杀(浏览器计时偏差)
	                    var now = exposer['now'];
	                    var start = exposer['start'];
	                    var end = exposer['end'];
	                    seckill.countDown(seckillId, now, start, end);
	                }
	            } else {
	                console.log('result: ' + result);
	            }
	        });

	    },

		detail:{
			//详情页初始化
			init :function(params){
				//手机验证和登录
				var killPhone=$.cookie('killPhone');
				var startTime=params['startTime'];
				var endTime=params['endTime'];
				var seckillId=params['seckillId'];
				console.log(killPhone);
				//var basePath=params['basePath'];
				//验证手机号
				if(!seckill.validatePhone(killPhone)){
					//绑定phone
					var killPhoneModal=$('#killPhoneModal');
					killPhoneModal.modal({
						show:true,//显示弹出层
						backdrop:'static',//禁止位置关闭
						keyboard:false//关闭键盘事件
					});
	
					$('#killPhoneBtn').click(function() {
						var inputPhone=$('#killPhoneKey').val();
						if(seckill.validatePhone(inputPhone)){
							//手机号写入cookie七天过期
							$.cookie('killPhone',inputPhone);
							//验证通过刷新页面
							window.location.reload();
						}else{
							$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误</label>').show(300);
						}
						
					});
				}
				$.get(seckill.URL.now(), {}, function (result) {
	                if (result && result['success']) {
	                    var nowTime = result['data'];
	                    //时间判断 计时交互
	                    seckill.countDown(seckillId, nowTime, startTime, endTime);
	                } else {
	                    console.log('result: ' + result);
	                    alert('result: ' + result);
	                }
	            });
			
			}
	
		}
}
