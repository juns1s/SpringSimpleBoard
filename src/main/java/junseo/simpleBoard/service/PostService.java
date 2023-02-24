package junseo.simpleBoard.service;

import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.domain.Post;
import junseo.simpleBoard.repository.MemberRepository;
import junseo.simpleBoard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long post(Long memberId,PostInformation postInformation){
        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();

        //게시글 정보 생성
        Post post = new Post();
        post.setMember(member);
        post.setTitle(postInformation.getTitle());
        post.setContent(postInformation.getContent());
        post.setWriter(member.getNickName());

        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).get();
        postRepository.deleteById(postId);
    }

    @Transactional
    public Long editPost(Long postId, Long memberId, PostInformation postInformation){
        Post post = postRepository.findById(postId).get();
        Member member = memberRepository.findById(memberId).get();

        checkWriter(post, member);

        return post.edit(postInformation);
    }

    private void checkWriter(Post post, Member member) {
        if(post.getMember().getId() != member.getId()){
            throw new IllegalStateException("작성자가 아닙니다.");
        }
    }
}
