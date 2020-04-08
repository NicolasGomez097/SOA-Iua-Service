package com.iua.soa.repository;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisRepositoryImplementation implements RedisRepository {
	private Jedis redis;
	
	public RedisRepositoryImplementation () {
		redis = new Jedis("localhost", 6379);
	}
	
	public boolean insert(String key, String value) {
		try {
			redis.set(key, value);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public String get(String key) {
		try {
			return redis.get(key);
		} catch (Exception e) {
			return null;
		}
	}
}
