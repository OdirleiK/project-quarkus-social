package br.com.kmpx.quarkussocial.domain.repository;

import br.com.kmpx.quarkussocial.domain.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post>{

}
