package vttp.batch5.ssf.noticeboard.repositories;

import java.time.Duration;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;


@Repository
public class NoticeRepository {

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */

	 /* Key Management 
	 Set a Key-Value Pair: SET key value 
	 Example: SET name "John"

	 Get the Value of a Key: GET key 
	 Example: GET name
	 
	 Delete a Key: DEL key 
	 Example: DEL name
	 
	 Check if a Key Exists: EXISTS key 
	 Example: EXISTS name
	 
	 View All Keys: KEYS *
	 
	 Set Key Expiry (in seconds): EXPIRE key seconds 
	 Example: EXPIRE name 60
	 
	 Check Time to Live (TTL) for a Key: TTL key
	 
	 Data Structure Commands Strings
	 Append to a String: APPEND key value 
	 Example: APPEND name " Doe"
	 
	 Hashes Set a Field in a Hash: HSET hash key value 
	 Example: HSET user name "Alice"
	 
	 Get a Field from a Hash: HGET hash key
	 
	 Get All Fields and Values from a Hash: HGETALL hash
	 
	 Lists Push to the Beginning of a List: LPUSH list value
	 
	 Push to the End of a List: RPUSH list value
	 
	 Get All List Elements: LRANGE list 0 -1
	 
	 Sets Add Members to a Set: SADD set value
	 
	 Get All Members of a Set: SMEMBERS set
	 
	 Sorted Sets Add Members with Scores: ZADD zset score member 
	 Example: ZADD leaderboard 100 "Alice"
	 
	 Get Members by Rank: ZRANGE zset 0 -1 WITHSCORES */

	public void insertNotices() {

	}

	@Autowired
    @Qualifier("String-String")
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    HashOperations<String, String, String> hashOps;

    @PostConstruct
    public void init(){
        hashOps = redisTemplate.opsForHash();
    }

    public void addToHash(String redisKey, String field, String value) {
        redisTemplate.opsForHash().putIfAbsent(redisKey, field, value);
    }

    public String getFieldValue(String redisKey, String field) {
        String value = hashOps.get(redisKey, field);
        return value;
    }
    
    public Set<String> getAllFields(String redisKey) {
        Set<String> fieldsKeySet = hashOps.keys(redisKey);
        return fieldsKeySet;
    }

    public void editFieldValue(String redisKey, String field, String newValue) {
        redisTemplate.opsForHash().put(redisKey, field, newValue);
    }

    public void deleteField(String redisKey, String field) {
        redisTemplate.opsForHash().delete(redisKey, field);
    }

    public Boolean isFieldExists(String redisKey, String field) {
        return redisTemplate.opsForHash().hasKey(redisKey, field);
    }

    public void expire(String redisKey, Long expireValue) {
        Duration expireDuration = Duration.ofSeconds(expireValue);
        redisTemplate.expire(redisKey, expireDuration);
    }
}
