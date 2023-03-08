package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="item")
@Data
@ToString
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    //상품코드

    @Column(nullable = false,length = 50)
    private String itemNm;  //상품명

    @Column(name = "price", nullable = false)
    private int price;  //가격

    @Column(nullable = false)
    private int stockNumber;    //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;  //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태
    //판매 상태의 경우, 재고가 없거나 or 상품을 미리 등록해 놓고 나중에 '판매 중' 상태로 바꾸거나 재고가 없을 때는
    //프론트에 노출시키지 않기 위해서 판매 상태를 코드로 갖고 있겠습니다.

    private LocalDateTime regTime;  //등록 시간
    private LocalDateTime updateTime;   //수정 시간



}
