package com.project.bookstore.repository;

import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.CartItem;
import com.project.bookstore.model.OrderDetailEntity;
import com.project.bookstore.model.OrderEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    try{
      Session session = getSession();
      Query<?> query = session.createNativeQuery("select * from ORDER where USER_ID = :userId and STATUS = :status limit 1").addEntity(OrderEntity.class);
      query.setParameter("userId", userId);
      query.setParameter("status", WConstants.OrderStatus.IN_CART.getValue());
      return (OrderEntity)query.getSingleResult();
    } catch(Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
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
        return findCartByUserId(userId);
      }
      return null;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  // also supports updating cart count as needed
  @Transactional
  public int addCartItems(int orderId, List<OrderDetailEntity> itemList){
    int res = 0;
    try{
      for(OrderDetailEntity detail: itemList){
        Session session = getSession();
        // If the same book exists in the cart, update it's quantity. If user adds different book then insert new entry as usual.
        Query<?> query = session.createNativeQuery("merge into ORDER_DETAIL as od using (values(:orderId, :bid, :quan, :price)) as data(A, B, C, D) " +
                "on od.ORDER_ID = data.A and od.BID = data.B " +
                "when matched then update set od.QUANTITY = od.QUANTITY + data.C " +
                "when not matched then INSERT values (data.A, data.B, data.C, data.D)");
        query.setParameter("orderId", orderId);
        query.setParameter("bid", detail.getBid());
        query.setParameter("quan", detail.getQuantity());
        query.setParameter("price", detail.getPrice());
        res = query.executeUpdate();
      }
      return res;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

//  todo: Come back to this later, and try to make it work
//  @Transactional
//  public int addCartItems(int orderId, List<OrderDetailEntity> itemList){
//    Session session = getSession();
//    try{
//      Query<?> query = session.createNativeQuery("insert into ORDER_DETAIL (order_id, bid, quantity, price) " +
//              "select o.order_id, o.bid, o.quantity, o.price from ORDER_DETAIL o where (o.order_id, o.bid, o.QUANTITY, o.PRICE) in :itemList");
////      query.setParameter("orderId", orderId);
//      query.setParameter("itemList", itemList);
//      return query.executeUpdate();
//    } catch (Exception e){
//      log.error(e.getMessage(), e);
//      return WConstants.RESULT_UNKNOWN_ERROR;
//    }
//  }

  @Transactional
  public List<CartItem> returnCartData(int orderId){
    try{
      Session session = getSession();
      Query<?> query = session.createNativeQuery("select od.bid, B.title, B.images, od.quantity, od.price, (od.quantity * od.price) as amount from " +
              "ORDER_DETAIL od join BOOK B on od.BID = B.BID " +
              "where od.ORDER_ID = :orderId");
      query.setParameter("orderId", orderId);
      List<Object[]> itemList = (List<Object[]>)query.getResultList();

      List<CartItem> cartItems = new ArrayList<>();
      for(Object[] bookData: itemList){
        CartItem item = new CartItem();
        item.setBid((int)bookData[0]);
        item.setTitle(String.valueOf(bookData[1]));
        item.setImage(String.valueOf(bookData[2]));
        item.setQuantity((int) bookData[3]);
        item.setPrice(Util.roundDouble((Double) bookData[4]));
        item.setAmount(Util.roundDouble((Double) bookData[5]));
        cartItems.add(item);
      }
      return cartItems;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

}
