package org.seckill.mapper;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.pojo.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledTest {
	
	@Resource
	private SuccessKilledMapper successKilledMapper;
	
	@Test
	public void insertSuccessKilledTest(){
		int count=successKilledMapper.insertSuccessKilled(1001L, 15738512109L);
		System.out.println(count);
	}
	@Test
	public void queryByIdWithSeckillTest(){
		SuccessKilled successKilled=successKilledMapper.queryByIdWithSeckill(1001L, 15738512109L);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill().toString());
	}

}
