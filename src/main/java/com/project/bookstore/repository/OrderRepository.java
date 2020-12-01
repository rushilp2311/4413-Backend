package com.project.bookstore.repository;

import com.project.bookstore.common.WConstants;
import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.CartItemInputData;
import com.project.bookstore.model.OrderEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderRepository {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private EntityManager entityManager;
  private Session getSession(){
    return entityManager.unwrap(Session.class);
  }

  // returns cart (items in the order *NOT placed* yet)
  @Transactional
  public OrderEntity findCartByUserId(String userId){
    Session session = getSession();
    Query<?> query = session.createNativeQuery("select * from ORDER where USER_ID = :userId and STATUS = :status limit 1").addEntity(OrderEntity.class);
    query.setParameter("userId", userId);
    query.setParameter("status", WConstants.OrderStatus.IN_CART.getValue());
    return (OrderEntity)query.getSingleResult();
  }

  @Transactional
  public OrderEntity insertNewCartAndReturn(String userId){
    try{
      Session session = getSession();
      Query<?> query = session.createNativeQuery("insert into ORDER (user_id, status) values(:userId, :status)");
      query.setParameter("userId", userId);
      query.setParameter("status", WConstants.OrderStatus.IN_CART.getValue());
      int res = query.executeUpdate();

      if(res == 1){ // if successfully inserted, return it
        query = session.createNativeQuery("select * from ORDER having max(ORDER_ID)").addEntity(OrderEntity.class); // this will return the order just created above
//      query.setParameter("userId", userId);
//      query.setParameter("status", WConstants.OrderStatus.IN_CART.getValue());
        return (OrderEntity)query.getSingleResult();
      }
      return null;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public int addCartItems(int orderId, List<CartItemInputData> itemList){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("insert into ORDER_DETAIL (order_id, bid, quantity, price) " +
              "(select :orderId, o.bid, o.quantity, o.price from ORDER_DETAIL o where (o.bid, o.QUANTITY, o.PRICE) in :itemList)");
      query.setParameter("orderId", orderId);
      query.setParameter("itemList", itemList);
      return query.executeUpdate();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

}
