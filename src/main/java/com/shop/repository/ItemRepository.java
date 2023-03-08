package com.shop.repository;

import com.shop.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long>,
    QuerydslPredicateExecutor<Item> {

    // 상품의 이름을 이용하여 데이터를 조회하는 예제를 살펴보겠습니다.
    List<Item> findByItemNm(String itemNm); //find + (엔티티 이름) + By + 변수이름

    //상품을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    //파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드 (LessThan 조건 처리하기)
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // ---------------------------------------------------------
    // @Query 어노테이션을 이용하여 상품 데이터를 조회하는 예제를 진행해보겠습니다.
    // 상품 상세 설명을 파라미터로 받아 해당 내용을 상품 상세 설명에 포함하고 있는 데이터를 조회하며,
    // 정렬 순서는 가격이 높은 순으로 조회합니다
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc ")
    List<Item> findByItemDetail(@Param("itemDetail")String itemDetail);
}
