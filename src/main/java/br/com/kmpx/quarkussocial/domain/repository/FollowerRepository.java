package br.com.kmpx.quarkussocial.domain.repository;

import java.util.List;
import java.util.Optional;

import br.com.kmpx.quarkussocial.domain.model.Follower;
import br.com.kmpx.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower>{

	public boolean follows(User follower, User user) {
		Parameters params = Parameters.with("follower", follower).and("user", user);
		
		PanacheQuery<Follower> query = find("follower = :follower and user = :user", params);
		
		Optional<Follower> firstResultOptional = query.firstResultOptional();
		
		return firstResultOptional.isPresent();
	}
	
	public List<Follower> findByUser(Long userId) {
		PanacheQuery<Follower> query = find("user.id", userId);
		return query.list();
	}

	public void deleteByFollowerAndUser(Long followerId, Long userId) {
		Parameters params = Parameters.with("userId", userId).and("followerId", followerId);
		
		delete("follower.id = :followerId and user.id = :userId", params);
	}
}
