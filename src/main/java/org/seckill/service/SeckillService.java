package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.pojo.Seckill;

/**
 * 业务接口：站在使用者角度设计接口
 * 考虑三个方面：方法定义粒度，参数，返回类型
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启输出秒杀接口地址，否则输出系统时间
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀业务
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws SeckillCloseException
	 * @throws RepeatKillException
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)throws SeckillException,
	SeckillCloseException,RepeatKillException;
}
