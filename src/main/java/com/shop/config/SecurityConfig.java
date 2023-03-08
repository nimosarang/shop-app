package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//@Configuration과 @EnableWebSecurity 어노테이션은 스프링 애플리케이션의 보안 구성 파일임을 나타냅니다.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final
    MemberService memberService;

    public SecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.formLogin()을 통해 로그인 페이지와 성공 및 실패시 URL 을 설정합니다
        http.formLogin()
            .loginPage("/members/login") //1. 로그인 페이지 URL 설정
            .defaultSuccessUrl("/") //2. 로그인 성공 시 이동할 URL 설정
            .usernameParameter("email") //3. 로그인 시 사용할 파라미터 이름으로 email 지정
            .failureUrl("/members/login/error") //4. 로그인 실패 시 이동할 URL 설정
            .and()
            // http.logout()을 통해 로그아웃 URL 과 성공시 이동할 URL 을 설정합니다.
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //5. 로그아웃 URL 설정
            .logoutSuccessUrl("/"); //6. 로그아웃 성공 시 이도할 URL 설정

        //http.authorizeRequests()를 사용하여 요청에 대한 권한을 부여합니다.
        // 여기서는 정적 자원에 대한 모든 요청과 일부 URL 에 대한 모든 요청을 허용하며,
        // '/admin' URL 에 대한 요청은 ADMIN 역할을 가진 사용자에게만 허용합니다.

        http.authorizeRequests() //1.시큐리티 처리에 HttpServletRequest 를 이용한다는 것을 의미합니다.
            .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll() // static 디렉터리의 하위 파일은 인증을 무시하도록 설정합니다.
            .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
            //2.permitAll()을 통해 모든 사용자가 인증(로그인)없이 해당 경로에 접근할 수 있도록 설정합니다
            //메인페이지,회원관련 URL,상품 상세페이지, 상품 이미지를 불러오는 경로가 이에 해당합니다.
            .mvcMatchers("/admin/**").hasRole("ADMIN") //3./admin 으로 시작하는 경로는 해당 계정이 ADMIN Role 일 경우에만 접근 가능하도록 설정합니다.
            .anyRequest().authenticated(); //4. 2번과 3번에서 설정해준 경로를 제외한 나머지 경로들은 모두 인증을 요구하도록 설정합니다.

        //http.exceptionHandling()을 사용하여 인증 실패 시 실행될 클래스를 설정합니다.
        http.exceptionHandling()
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); //5.인증되지 않은 사용자가 리소스에 접근했을 때 수행되는 핸들러를 등록합니다.

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //@Bean 어노테이션은 PasswordEncoder를 인코딩 할 때 사용할 BCryptPasswordEncoder 객체를 생성합니다.
}