package junseo.simpleBoard.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptSourceInputNonExistentImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public Post findOne(Long id){
        return em.find(Post.class, id);
    }

    public List<Post> findAll(PostSearch postSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);
        Root<Post> p = cq.from(Post.class);
        Join<Post, Member> m = p.join("member", JoinType.INNER);
        List<Predicate> criteria = new ArrayList<>();

        //제목 검색
        if(postSearch.getTitle()!=null){
            Predicate title = cb.equal(p.get("title"), postSearch.getTitle());
            criteria.add(title);
        }
        //작성자 검색
        if(postSearch.getWriter()!=null){
            Predicate writer = cb.equal(p.get("writer"), postSearch.getWriter());
            criteria.add(writer);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Post> query = em.createQuery(cq).setMaxResults(1000);    //최대 1000개
        return query.getResultList();
    }

    public void deleteOne(Post post){
        post.delete();
        //엔티티 삭제
        em.remove(post);
    }
}
