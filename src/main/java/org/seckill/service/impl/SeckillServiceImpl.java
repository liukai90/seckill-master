package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.mapper.SeckillMapper;
import org.seckill.mapper.SuccessKilledMapper;
import org.seckill.mapper.cache.RedisDao;
import org.seckill.mapper.cache.RedisDaoTest;
import org.seckill.pojo.Seckill;
import org.seckill.pojo.SuccessKilled;
import org.seckill.service.SeckillService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import ch.qos.logback.classic.Logger;

@Service
public class SeckillServiceImpl implements SeckillService {
	
	private Logger logger=(Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillMapper seckillMapper;
	
	@Autowired
	private SuccessKilledMapper successKilledMapper;
	
	@Autowired
	private RedisDao redisDao;
	
	//用于混淆md5
	private final String slat="cdfwewr242e15e2q1e1w5d*&$&$&^W5wd324235";

	public List<Seckill> getSeckillList() {
		
		return seckillMapper.queryAll(0, 5);
	}

	public Seckill getById(long seckillId) {

		return seckillMapper.queryById(seckillId);
	}

	public Exposer exportSeckillUrl(long seckillId) {
		//从缓存里获取秒杀商品，如果过时没有从数据库里读
		Seckill seckill=redisDao.getSeckill(seckillId);
		if(seckill==null){
			seckill=seckillMapper.queryById(seckillId);
			if(seckill==null){
				return new Exposer(false, seckillId);
			}else{
				redisDao.putSeckill(seckill);
			}
		}
		//获取当前时间来与秒杀开始时间与结束时间比较，返回结果
		Date startTime=seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		Date nowTime=new Date();
		if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), 
					endTime.getTime());
		}
		String md5=this.getMD5(seckillId);
		//如果都符合要求，暴露秒杀地址
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 使用注解控制事物方法的优点
	 * 1：开发团队达成一致约定，明确标注事物方法的编程风格
	 * 2.保持方法执行时间尽可能短，不要穿插其他的网络操作rpc/http请求或者剥离到事物外部
	 * 3.
	 */
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException {
		if(md5==null||!md5.equals(this.getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑：减库存+记录购买行为
		Date nowTime=new Date();
		try {
			//记录购买行为
			int insertCount=successKilledMapper.insertSuccessKilled(seckillId, userPhone);
			if(insertCount<=0){
				//重复秒杀
				throw new RepeatKillException("seckill repeated");
			}else{
				//秒杀成功
				int updataCount=seckillMapper.reduceNumber(seckillId, nowTime);
				if(updataCount<=0){
					//没有更新记录，秒杀结束
					throw new SeckillCloseException("seckill is closed");
				}else{
					SuccessKilled successKilled=successKilledMapper.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);
				}
				
			}
			
		}catch(SeckillCloseException e1){
			throw e1;
		}catch(RepeatKillException e2){
			throw e2;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译异常，转化为运行异常
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
	}
	
	private String getMD5(long seckillId){
		String base=seckillId+"/"+slat;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
