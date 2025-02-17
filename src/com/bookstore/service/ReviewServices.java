package com.bookstore.service;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Review;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ReviewServices {
    private ReviewDAO reviewDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ReviewServices(HttpServletRequest request, HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
        this.reviewDAO = new ReviewDAO();
    }

    public void listAllReview() throws ServletException, IOException {
        listAllReview(null, null);
    }

    public void listAllReview(String message, String alertType) throws ServletException, IOException {
        List<Review> listReviews = reviewDAO.listAll();

        request.setAttribute("listReviews", listReviews);

        if (message != null) {
            request.setAttribute("message", message);
            request.setAttribute("alertType", alertType);
        }

        String listPage = "review_list.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
        dispatcher.forward(request, response);
    }

    public void editReview() throws ServletException, IOException {
        Integer reviewId = Integer.parseInt(request.getParameter("id"));
        Review review = reviewDAO.get(reviewId);

        request.setAttribute("review", review);

        String editPage = "review_form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
        dispatcher.forward(request, response);
    }

    public void updateReview() throws ServletException, IOException {
        Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
        String headline = request.getParameter("headline");
        String comment = request.getParameter("comment");

        Review review = reviewDAO.get(reviewId);
        review.setHeadline(headline);
        review.setComment(comment);

        reviewDAO.update(review);

        String message = "The review has been updated successfully.";
        String alertType = "success";

        listAllReview(message, alertType);
    }

    public void deleteReview() throws ServletException, IOException {
        Integer reviewId = Integer.parseInt(request.getParameter("id"));
        Review review = reviewDAO.get(reviewId);

        String message;
        String alertType;
        if (review == null) {
            message = "Could not find a review with ID " + reviewId + ", or it might have been deleted by another admin.";
            alertType = "warning";
        } else {
            message = "The review has been deleted successfully.";
            alertType = "success";
            reviewDAO.delete(reviewId);
        }
        listAllReview(message, alertType);
    }


    public void showReviewForm() throws ServletException, IOException {
        Integer bookId = Integer.parseInt(request.getParameter("book_id"));
        BookDAO bookDAO = new BookDAO();
        Book book = bookDAO.get(bookId);

        HttpSession session = request.getSession();
        session.setAttribute("book", book);

        Customer customer = (Customer) session.getAttribute("loggedCustomer");

        Review existReview = reviewDAO.findByCustomerAndBook(customer.getCustomerId(), bookId);
        String targetPage = "frontend/review_form.jsp";
        if (existReview != null) {
            request.setAttribute("review", existReview);
            targetPage = "frontend/review_info.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
        dispatcher.forward(request, response);
    }

    public void submitReview() throws ServletException, IOException {
        Integer bookId = Integer.parseInt(request.getParameter("bookId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String headline = request.getParameter("headline");
        String comment = request.getParameter("comment");

        Review newReview = new Review();
        newReview.setHeadline(headline);
        newReview.setRating(rating);
        newReview.setComment(comment);

        Book book = new Book();
        book.setBookId(bookId);
        newReview.setBook(book);

        Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
        newReview.setCustomer(customer);

        reviewDAO.create(newReview);
        String messagePage = "frontend/review_done.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
        dispatcher.forward(request, response);

    }
}
