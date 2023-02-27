package junseo.simpleBoard.repository;

import junseo.simpleBoard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByuserName(String userName);
    Optional<Member> findById(Long id);
}
