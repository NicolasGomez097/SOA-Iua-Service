package com.iua.soa.repository;

public interface RedisRepository {
	public boolean insert(String key, String value);	
	public String get(String key);
}
