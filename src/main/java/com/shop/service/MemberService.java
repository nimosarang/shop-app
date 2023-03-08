package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional //1.비지니스 로직을 담당하는 서비스 게층클래스에 트랜잭션 어노테이션 선언. 로직 처리도중 에러 발생 시 변경된 데이터를 로직을 수행하기 이전 상태로 콜백시킴
@RequiredArgsConstructor //2.
public class MemberService implements UserDetailsService { //멤버서비스가 유저디테일서비스 구현

    private final MemberRepository memberRepository; //3
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) { //4. 이미 가입된 회원 예외처리
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // loadUserByUsername()메소드를 오버라이딩. 로그인 할 유저의 이메일을 파라미터로 전달받는다
        Member member = memberRepository.findByEmail(email);

        if (member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder() // UserDetail 을 구현하고 있는 User 객체를 반환해줍니다. User 객체를 생성하기 위해서,
            //생성자로 회원의 이메일,비밀번호,role 을 파라미터로 넘겨줍니다
            .username(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRole().toString())
            .build();

    }
}
