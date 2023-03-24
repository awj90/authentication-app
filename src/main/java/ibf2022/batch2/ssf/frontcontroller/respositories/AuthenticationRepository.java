package ibf2022.batch2.ssf.frontcontroller.respositories;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	// TODO Task 5
	// Use this class to implement CRUD operations on Redis

	public void addToDisabledList(String username) {
		redisTemplate.opsForValue().set(username, username);
		redisTemplate.expire(username, Duration.ofMinutes(30));
	}

	public boolean isLocked(String username) {
		return redisTemplate.hasKey(username);
	}


}
