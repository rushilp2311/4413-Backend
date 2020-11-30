package com.project.bookstore.repository;

import com.project.bookstore.common.WConstants;
import com.project.bookstore.controller.UserController;
import com.project.bookstore.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public class UserRepository {

  Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private EntityManager entityManager;

  private Session getSession(){
    return entityManager.unwrap(Session.class);
  }

  @Transactional
  public int signupUser(UserEntity userEntity){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("insert into user(USER_ID, FIRST_NAME, LAST_NAME, USER_TYPE, EMAIL, PASSWORD" +
              ") VALUES (:user_id, :f_name, :l_name, :user_type, :email, :password)");
      query.setParameter("user_id", UUID.randomUUID().toString());
      query.setParameter("f_name", userEntity.getFirst_name());
      query.setParameter("l_name", userEntity.getLast_name());
      query.setParameter("user_type", WConstants.UserType.USER.getValue()); // todo: replace this with the correct user_type based on visitor, user, partner...
      query.setParameter("email", userEntity.getEmail());
      query.setParameter("password", (userEntity.getPassword()));
      return query.executeUpdate();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return WConstants.RESULT_UNKNOWN_ERROR;
    }
  }

  @Transactional
  public boolean isUserEmailExist(String email){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where email = :email");
      query.setParameter("email", email);
      return query.getSingleResult() != null;
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return false;
    }
  }

  @Transactional
  public UserEntity findUser(String email){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where EMAIL = :email and USER_TYPE = :user_type").addEntity(UserEntity.class);
      query.setParameter("email", email);
      query.setParameter("user_type", WConstants.UserType.USER.getValue());
      return (UserEntity)query.getSingleResult();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

  @Transactional
  public UserEntity findUserByUserId(String userId){
    Session session = getSession();
    try{
      Query<?> query = session.createNativeQuery("select * from user where USER_ID = :userId and USER_TYPE = :user_type").addEntity(UserEntity.class);
      query.setParameter("userId", userId);
      query.setParameter("user_type", WConstants.UserType.USER.getValue());
      return (UserEntity)query.getSingleResult();
    } catch (Exception e){
      log.error(e.getMessage(), e);
      return null;
    }
  }

}
