--创建数据库
create database seckill;
--使用数据库
--创建秒杀库存表
CREATE TABLE seckill(
'seckill_id' bigint NOT NULL AUTO_INCREMENT COMMIT '商品库存id',
'name' varchar(120) NOT NULL COMMIT '商品名称',
'number' int NOT NULL COMMIT '库存量',
'start_time' timestamp NOT NULL COMMIT '秒杀开启时间',
'end_time' timestamp NOT NULL commit '秒杀结束时间',
'create_time' timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMIT '创建时间' ,
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf-8 commit='秒杀成功明细表';

--初始化数据
insert into seckill(name,number,start_time,end_time) 
values
('1000元秒杀iPhone6','100','2017-07-29 00:00:00','2017-07-30 00:00:00'),
('500元秒杀ipad2','200','2017-07-29 00:00:00','2017-07-30 00:00:00'),
('300元秒杀小米4','300','2017-07-29 00:00:00','2017-07-30 00:00:00'),
('1元秒杀iPhone6','1','2017-07-29 00:00:00','2017-07-30 00:00:00'),
('200元秒杀红米note','100','2017-07-29 00:00:00','2017-07-30 00:00:00');
--秒杀成功明细表
--用户登录认证相关信息
create table success_killed(
'seckill_id' bigint NOT NULL COMMIT '秒杀商品id',
'user_phone' bigint NOT NULL commit '用户手机号',
'state' tinyint NOT NULL DEFAULT -1 commit '状态标识：-1：无效，0：成功，1：已付款',
'create_time' timestamp NOT NULL commit '创建时间',
PRIMARY KEY (seckill_id,user_phone),/*联合主键*/
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf-8 commit='秒杀成功明细表';