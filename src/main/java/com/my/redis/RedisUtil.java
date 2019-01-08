package com.my.redis;

import java.io.Serializable;
import com.google.gson.Gson;
import com.my.util.Utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisUtil {
	private RedisTemplate<Serializable, Object> redisTemplate;

	// 批量删除对应的value
	public void remove(final String... keys) {
		for (String key : keys)
			remove(key);
	}

	// 批量删除key
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	// 判断缓存中是否有对应的value
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	// 删除对应的value
	public void remove(final String key) {
		if (exists(key))
			redisTemplate.delete(key);
	}

	// 删除符合要求的key
	public void deleteKeys(final String key) {
		redisTemplate.delete(redisTemplate.keys(key));
	}

	// 读取缓存
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}

	// 写入缓存
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 写入缓存
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public Long del(final String... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long result = 0L;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());

				}
				return result;
			}
		});
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */

	public Long delhset(final String key, final String... fields) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long result = 0L;
				for (int i = 0; i < fields.length; i++) {
					result = connection.hDel(
							redisTemplate.getStringSerializer().serialize(key),
							redisTemplate.getStringSerializer().serialize(
									fields[i]));

				}
				return result;
			}
		});
	}

	public void hashset(final String key, final Map<byte[], byte[]> value,
			final Long liveTime) {
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.hMSet(
						redisTemplate.getStringSerializer().serialize(key),
						value);
				if (liveTime > 0) {
					connection.expire(redisTemplate.getStringSerializer()
							.serialize(key), liveTime);
				}
				return 1L;
			}
		});

	}

	public Map<byte[], byte[]> gethash(final String key) {
		return redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {
			public Map doInRedis(RedisConnection connection)
					throws DataAccessException {
				try {
					if (connection.hGetAll(key.getBytes()) == null) {
						return null;
					} else {
						return connection.hGetAll(key.getBytes());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * 查看redis里有多少数据
	 */
	public Long dbSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	public String ping() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.ping();
			}
		});
	}

	/**
	 * 发布广播
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public void publish(String channel, String message)
			throws UnsupportedEncodingException {
		redisTemplate.convertAndSend(channel, Utils.string2Unicode(message));
	}

	/**
	 * 向redis中缓存数据（非全局对象，只针对于每位用户，以session为key，默认有效时间15分钟）;
	 * 
	 * @param obj
	 *            需要缓存的对象
	 * @param api
	 *            缓存的对象名称（一般为API接口名称）
	 * @param session
	 *            传入HttpSession对象
	 */
	public void setCache(Object obj, String api, HttpSession session) {
		String gsonStr = null;
		if (obj instanceof String) {
			gsonStr = (String) obj;
		} else {
			Gson gson = new Gson();
			gsonStr = gson.toJson(obj);
		}
		set("wtCache" + api + session.getId(), gsonStr, 60 * 15l);
	}

	public void setCache(Object obj, String api, HttpSession session, long time) {
		String gsonStr = null;
		if (obj instanceof String) {
			gsonStr = (String) obj;
		} else {
			Gson gson = new Gson();
			gsonStr = gson.toJson(obj);
		}
		set("wtCache" + api + session.getId(), gsonStr, time);
	}
	
	
	/**
	 * 从redis中获取缓存数据
	 * 
	 * @param api
	 *            缓存的对象名称（一般为API接口名称）
	 * @param session
	 *            HttpSession对象
	 * @param clazz
	 *            缓存对象的class类型
	 * @return 返回缓存对象的实体类
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> Object getCache(String api, HttpSession session, Class<T> clazz) {
		try {
			if (exists("wtCache" + api + session.getId())) {
				String json = get("wtCache" + api + session.getId()).toString();
				Gson gson = new Gson();
				Object bean;

				bean = clazz.newInstance();
				bean = gson.fromJson(json, clazz);
				return bean;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public <T> List<T> getCacheList(String api, HttpSession session,
			Class<T> clazz) {
		try {
			if (exists("wtCache" + api + session.getId())) {
				String json = get("wtCache" + api + session.getId()).toString();
				return Utils.jsonToList(json, clazz);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 校验重复发送
	 * 
	 * @return
	 */
	public boolean checkRepeat(String key) {

		long count = redisTemplate.opsForValue().increment(key, 1);
		if (count == 1) {
			// 设置有效期半分钟
			redisTemplate.expire(key, 30, TimeUnit.SECONDS);
		}
		if (count > 1) {
			return false;
		}
		return true;
	}

	public void removeRepeat(String key) {
		redisTemplate.delete(key);
	}

	public void setRedisTemplate(
			RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
