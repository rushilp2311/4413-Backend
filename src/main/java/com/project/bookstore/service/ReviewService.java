package com.project.bookstore.service;

import com.project.bookstore.common.Util;
import com.project.bookstore.common.WConstants;
import com.project.bookstore.model.BookEntity;
import com.project.bookstore.model.ReviewEntity;
import com.project.bookstore.model.ReviewInputData;
import com.project.bookstore.repository.ReviewRepository;
import com.project.bookstore.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private BookService bookService;

  public List<ReviewEntity> getReviewsForBook(int bid) throws Exception{
    List<ReviewEntity> reviews;
    try{
      reviews = reviewRepository.getReviewsForBook(bid);
    } catch (Exception e){
      throw new Exception(e);
    }
    return reviews;
  }


  public String addReview(ReviewInputData inputData) {
    // Error checking
    if (StringUtils.isEmpty(inputData.getUserId()) || inputData.getStars() < 0 || inputData.getStars() > 5
            || StringUtils.isEmpty(inputData.getMessage()) ||
            inputData.getMessage().length() > WConstants.REVIEW_MESSAGE_LENGTH || inputData.getBid() < 0){
      return Util.getJsonResponse(WConstants.RESULT_INVALID_DATA, inputData.getUserId());
    }
    // make sure that user exists, if not then return error
    if (!userService.isUserExist(inputData.getUserId())) {
      return Util.getJsonResponse(WConstants.RESULT_USER_DOES_NOT_EXIST, inputData.getUserId());
    }
    // check if BID is valid
    BookEntity book = bookService.getBookInfo(inputData.getBid());
    if (book == null) {
      return Util.getJsonResponse(WConstants.RESULT_INVALID_DATA, inputData.getUserId());
    }

    // add review
    if(reviewRepository.addReview(inputData.getBid(), inputData.getUserId(), inputData.getStars(), inputData.getMessage()) == 1){
      JSONObject json = new JSONObject();
      json.put("status", WConstants.RESPONSE_SUCCESS);
      json.put("message", "Review successfully added.");
      return json.toString();
    } else{
      return Util.getJsonResponse(WConstants.RESULT_UNKNOWN_ERROR, inputData.getUserId());
    }
  }
}
