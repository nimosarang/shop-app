package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Data
@ToString
public class Member { //회원 정보를 저장하는 Member 엔티티. 관리할 회원 정보는 이름, 이메일, 비밀번호, 주소, 역할

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true) //1.회원은 이메일을 통해 유일하게 구분해야 하기 때문에, 동일한 값이 데이터베이스에 들어올 수 없도록 유니크속성 지정
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING) //2.Enum 을 사용하면 기본적으로 순서가 저장되는데, enum 순서가 바뀔 경우 문제 발생할 수 있어서 String 으로 저장 권장
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        //3. Member 엔티티를 생성하는 메소드. Member 엔티티에 회원을 생성하는 메소드를 만들어서 관리를 한다면 코드가 변경되더라도 한 군데만 수정되는 이점이 있다.
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        //4.스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean 을 파라미터로 넘겨서 비밀번호를 암호화 합니다.
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }



}
