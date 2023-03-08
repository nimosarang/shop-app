package com.shop.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ItemDto {
    private Long id; //상품코드
    private String itemNm; //상품명
    private Integer price; //상품가격
    private String itemDetail; //상품 상세 설명
    private String sellStatCd; // ?
    private LocalDateTime regTime; //등록시간
    private LocalDateTime updateTime; //수정시간


}
