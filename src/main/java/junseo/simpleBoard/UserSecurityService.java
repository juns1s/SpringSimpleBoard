package junseo.simpleBoard;

import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.domain.UserRole;
import junseo.simpleBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Member> members = memberRepository.findByuserName(userName);
        if(members.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Member member = members.get();
        System.out.println("검색된 맴버: "+member.getUserName());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(userName)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(member.getUserName(), member.getPassword(), authorities);
    }
}
