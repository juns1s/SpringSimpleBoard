package junseo.simpleBoard.repository;

import jakarta.persistence.EntityManager;
import junseo.simpleBoard.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
//@RequiredArgsConstructor
//public class MemberRepository{
//
//    private final EntityManager em;
//
//    public void save(Member member){
//        em.persist(member);
//    }
//
//    public Member findOne(Long id){
//        return em.find(Member.class, id);
//    }
//
//    public List<Member> findAll(){
//        return em.createQuery("select m from Member m", Member.class)
//                .getResultList();
//    }
//
//    public Optional<List<Member>> findByusername(String userName){
//       return Optional.ofNullable(em.createQuery("select m from Member m where m.userName = :userName", Member.class)
//               .setParameter("userName", userName)
//               .getResultList());
//    }
//
//}

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByuserName(String userName);
    Optional<Member> findById(Long id);
}
