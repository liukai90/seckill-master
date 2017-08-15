package org.seckill.mapper;

import org.apache.ibatis.annotations.Param;
import org.seckill.pojo.SuccessKilled;

public interface SuccessKilledMapper {
	
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * 根据id查询SuccessKilled并携带秒杀实体对象
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
