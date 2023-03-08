package com.shop.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;
//    @Test
//    @DisplayName("상품 등록 페이지 권한 테스트")
//    @WithMockUser(username = "admin", roles = "ADMIN") //1.회원이름이 admin 이고, role 이 ADMIN 인 유저가 로그인된 상태로 테스트할 수 있도록 해주는 오노테이션
//    public void itemFormTest() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) //2.상품 등록 페이지에 get 요청을 보냅니다
//            .andDo(print()) //3.요청과 응답 메시지를 확인할 수 있도록 콘솔창에 출력해줍니다.
//            .andExpect(status().isOk()); //4.응답 상태 코드가 정상인지 확인합니다.
//    }
    @Test
    @DisplayName("상품 등록 페이지 권한 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void itemFormTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 등록 페이지 일반 회원 접근 테스트")
    @WithMockUser(username = "user", roles = "USER")
    public void itemFormNotAdminTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
            .andDo(print())
            .andExpect(status().isForbidden()); //상품 등록 페이지 진입 요청 시 Forbidden 예외가 발생하면 테스트가 성공적으로 통과합니다
    }
}

































