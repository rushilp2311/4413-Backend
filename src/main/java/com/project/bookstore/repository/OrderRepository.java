package com.project.bookstore.repository;

import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Date;
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
      Query<?> query = session.createNativeQuery("select * from ORDER where USER_ID = :userId and (STATUS = :status or STATUS = :statuss) limit 1").addEntity(OrderEntity.class);
      query.setParameter("userId", userId);
      query.setParameter("status", WConstants.OrderStatus.IN_CART.getValue());
//      query.setParameter("statuss", WConstants.OrderStatus.DENIED.getValue());
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

  @Transactional
  public int removeCartItem(int orderId, int bid){
    int res = 0;
    try{
      Session session = getSession();
      // If the same book exists in the cart, update it's quantity. If user adds different book then insert new entry as usual.
      Query<?> query = session.createNativeQuery("delete from ORDER_DETAIL where ORDER_ID = :orderId and BID = :bid");
      query.setParameter("orderId", orderId);
      query.setParameter("bid", bid);
      res = query.executeUpdate();
      return res;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

  @Transactional
  public int incrementSubmitAttempts(String userId, int orderId){
    int res = 0;
    try{
      Session session = getSession();
      // If the same book exists in the cart, update it's quantity. If user adds different book then insert new entry as usual.
      Query<?> query = session.createNativeQuery("update ORDER set SUBMIT_ATTEMPTS = SUBMIT_ATTEMPTS + 1 where USER_ID = :userId and ORDER_ID = :orderId");
      query.setParameter("orderId", orderId);
      query.setParameter("userId", userId);
      res = query.executeUpdate();
      return res;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

  @Transactional
  public int submitOrder(String userId, int orderId){
    int res = 0;
    try{
      Session session = getSession();
      // If the same book exists in the cart, update it's quantity. If user adds different book then insert new entry as usual.
      Query<?> query = session.createNativeQuery("update ORDER set STATUS = :status where USER_ID = :userId and ORDER_ID = :orderId");
      query.setParameter("status", WConstants.OrderStatus.ORDERED.getValue());
      query.setParameter("orderId", orderId);
      query.setParameter("userId", userId);
      res = query.executeUpdate();
      return res;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

  @Transactional
  public List<OrderProcessedData> returnOrderByBid(int bid){
    try{
      Session session = getSession();
      Query<?> query = session.createNativeQuery("select B.title, B.price, OD.QUANTITY, O.* from order O " +
              "join ORDER_DETAIL OD on O.ORDER_ID = OD.ORDER_ID " +
              "join BOOK B on OD.BID = B.BID " +
              "where O.STATUS = 1 and B.BID = :bid");
      query.setParameter("bid", bid);
      List<Object[]> itemList = (List<Object[]>)query.getResultList();

      List<OrderProcessedData> orders = new ArrayList<>();
      for(Object[] orderData: itemList){
        OrderProcessedData item = new OrderProcessedData();
        item.setTitle(String.valueOf(orderData[0]));
        item.setPrice(Util.roundDouble((Double) orderData[1]));
        BigInteger bg = new BigInteger(String.valueOf(orderData[2]));
        item.setQuantity(bg.intValue());
        item.setOrderId((int)orderData[3]);
        item.setUserId(String.valueOf(orderData[4]));
        item.setOrderDate(((Date)orderData[5]).toString());

        orders.add(item);
      }
      return orders;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

}
