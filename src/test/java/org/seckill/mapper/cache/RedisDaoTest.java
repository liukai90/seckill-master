package org.seckill.mapper.cache;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.mapper.SeckillMapper;
import org.seckill.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	@Autowired
	private RedisDao redisdao;
	
	@Autowired
	private SeckillMapper seckillMapper;
	@Test
	public void seckillTest(){
		long id=1001;
		Seckill seckill=redisdao.getSeckill(id);
		if(seckill==null){
			seckill=seckillMapper.queryById(id);
			if(seckill!=null){
				System.out.println(seckill);
				redisdao.putSeckill(seckill);
				seckill=redisdao.getSeckill(id);
				System.out.println(seckill);
			}
		}
		
				
	}

}
