package com.rzfk.common.core.redis;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.rzfk.common.config.AppConfig;
import lombok.SneakyThrows;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 *
 * @author shenxinquan
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {

	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private AppConfig config;
	@SneakyThrows
	public String getKey(String key){
		return StrUtil.format("{}:{}",config.getName(),key);
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key   缓存的键值
	 * @param value 缓存的值
	 */
	public <T> void setCacheObject(final String key, final T value) {
		redissonClient.getBucket(getKey(key)).set(value);
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key      缓存的键值
	 * @param value    缓存的值
	 * @param timeout  时间
	 * @param timeUnit 时间颗粒度
	 */
	public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
		RBucket<T> result = redissonClient.getBucket(getKey(key));
		result.set(value);
		result.expire(timeout, timeUnit);
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @return true=设置成功；false=设置失败
	 */
	public boolean expire(final String key, final long timeout) {
		return expire(getKey(key), timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @param unit    时间单位
	 * @return true=设置成功；false=设置失败
	 */
	public boolean expire(final String key, final long timeout, final TimeUnit unit) {
		RBucket rBucket = redissonClient.getBucket(getKey(key));
		return rBucket.expire(timeout, unit);
	}

	/**
	 * 获得缓存的基本对象。
	 *
	 * @param key 缓存键值
	 * @return 缓存键值对应的数据
	 */
	public <T> T getCacheObject(final String key) {
		RBucket<T> rBucket = redissonClient.getBucket(getKey(key));
		return rBucket.get();
	}

	/**
	 * 删除单个对象
	 *
	 * @param key
	 */
	public boolean deleteObject(final String key) {
		return redissonClient.getBucket(getKey(key)).delete();
	}

	/* */

	/**
	 * 删除集合对象
	 *
	 * @param collection 多个对象
	 * @return
	 */
	public long deleteObject(final Collection collection) {
		return redissonClient.getKeys().delete(Arrays.toString(collection.toArray()));
	}

	/**
	 * 缓存List数据
	 *
	 * @param key      缓存的键值
	 * @param dataList 待缓存的List数据
	 * @return 缓存的对象
	 */
	public <T> boolean setCacheList(final String key, final List<T> dataList) {
		RList<T> rList = redissonClient.getList(getKey(key));
		return rList.addAll(dataList);
	}

	/**
	 * 获得缓存的list对象
	 *
	 * @param key 缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public <T> List<T> getCacheList(final String key) {
		RList<T> rList = redissonClient.getList(getKey(key));
		return rList.readAll();
	}

	/**
	 * 缓存Set
	 *
	 * @param key     缓存键值
	 * @param dataSet 缓存的数据
	 * @return 缓存数据的对象
	 */
	public <T> boolean setCacheSet(final String key, final Set<T> dataSet) {
		RSet<T> rSet = redissonClient.getSet(getKey(key));
		return rSet.addAll(dataSet);
	}

	/**
	 * 获得缓存的set
	 *
	 * @param key
	 * @return
	 */
	public <T> Set<T> getCacheSet(final String key) {
		RSet<T> rSet = redissonClient.getSet(getKey(key));
		return rSet.readAll();
	}

	/**
	 * 缓存Map
	 *
	 * @param key
	 * @param dataMap
	 */
	public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
		if (dataMap != null) {
			RMap<String, T> rMap = redissonClient.getMap(getKey(key));
			rMap.putAll(dataMap);
		}
	}

	/**
	 * 获得缓存的Map
	 *
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> getCacheMap(final String key) {
		RMap<String, T> rMap = redissonClient.getMap(getKey(key));
		return rMap.getAll(rMap.keySet());
	}

	/**
	 * 往Hash中存入数据
	 *
	 * @param key   Redis键
	 * @param hKey  Hash键
	 * @param value 值
	 */
	public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
		RMap<String, T> rMap = redissonClient.getMap(getKey(key));
		rMap.put(hKey, value);
	}

	/**
	 * 获取Hash中的数据
	 *
	 * @param key  Redis键
	 * @param hKey Hash键
	 * @return Hash中的对象
	 */
	public <T> T getCacheMapValue(final String key, final String hKey) {
		RMap<String, T> rMap = redissonClient.getMap(getKey(key));
		return rMap.get(hKey);
	}

	/**
	 * 获取多个Hash中的数据
	 *
	 * @param key   Redis键
	 * @param hKeys Hash键集合
	 * @return Hash对象集合
	 */
	public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
		RListMultimap rListMultimap = redissonClient.getListMultimap(getKey(key));
		return rListMultimap.getAll(hKeys);
	}

	/**
	 * 获得缓存的基本对象列表
	 *
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	public Collection<String> keys(final String pattern) {
		Iterable<String> iterable = redissonClient.getKeys().getKeysByPattern(pattern);
		return Lists.newArrayList(iterable);
	}
}
