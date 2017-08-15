package org.seckill.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.mapper.SeckillMapper;
import org.seckill.pojo.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillMapperTest {
	@Resource
	private SeckillMapper seckillMapper;
	
	@Test
	public void reduceNumberTest(){
		Integer count=seckillMapper.reduceNumber(1,new Date());
		System.out.println(count);
	}
	@Test
	public void queryByIdTest(){
		
		Seckill seckill=seckillMapper.queryById(1000);
		
		System.out.println(seckill.toString());
	}
	@Test
	public void queryAllTest(){
		List<Seckill> seckills=seckillMapper.queryAll(1, 20);
		for(Seckill seckill:seckills){
			System.out.println(seckill.toString());
		}
	}
	

}
