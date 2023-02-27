package junseo.simpleBoard.repository;

import junseo.simpleBoard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);
    Optional<Post> findByTitle(String title);
    Optional<Post> findByWriter(String writer);
}
