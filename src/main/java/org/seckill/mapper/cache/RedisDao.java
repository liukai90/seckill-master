package org.seckill.mapper.cache;

import org.seckill.pojo.Seckill;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import ch.qos.logback.classic.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private final Logger logger=(Logger) LoggerFactory.getLogger(this.getClass());
	
	private final JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip,int port){
		jedisPool=new JedisPool(ip,port);
	}
	
	public Seckill getSeckill(long seckillId){
		//redis业务逻辑
		try {
			Jedis jedis=jedisPool.getResource();
			try {
				String key="seckill:"+seckillId;
				//redis内部没有实现序列化
				//自定义序列化
				byte [] bytes=jedis.get(key.getBytes());
				//缓存重获取到
				if(bytes!=null){
					//空对象
					Seckill seckill=schema.newMessage();
					//给空对象赋值，反序列化
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					
					return seckill;
				}
			}finally{
				jedis.close();
			}
				
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		
		try {
			Jedis jedis=jedisPool.getResource();
			try {
				String key="seckill:"+seckill.getSeckillId();
				byte [] bytes=ProtostuffIOUtil.toByteArray(seckill, schema,
						LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout=60*60;
				String result=jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
