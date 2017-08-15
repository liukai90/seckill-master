package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.pojo.Seckill;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	private final Logger Logger=(Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void getSeckillList(){
		
		List<Seckill> seckills=seckillService.getSeckillList();
		
		Logger.info("seckills={}",seckills);
	}
	
	@Test
	public void getById(){
		
		Seckill seckill=seckillService.getById(1003);
		
		Logger.info("seckill={}", seckill);
	}
	
	@Test
	public void exportSeckillUrl(){
		
		long id=1001;
		
		Exposer exposer=seckillService.exportSeckillUrl(1001);
		
		if(exposer.isExposed()){
			Logger.info("exposer={}", exposer);
			long phone=15738512109l;
			String md5=exposer.getMd5();
			try {
				SeckillExecution execution=seckillService.executeSeckill(1000, 15738512109l, md5);
				Logger.info("result={}", execution);
			} catch (RepeatKillException e) {
				Logger.error(e.getMessage());
			}catch (SeckillCloseException e) {
				
				Logger.error(e.getMessage());
			}
		}else{
			//秒杀未开启
			Logger.warn("exposer", exposer);
		}
		
		
	}
	

}
