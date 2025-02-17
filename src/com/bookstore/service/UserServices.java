package com.bookstore.service;

import com.bookstore.dao.UserDAO;
import com.bookstore.entity.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServices {
    private UserDAO userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public UserServices(HttpServletRequest request, HttpServletResponse response) {
        this.userDAO = new UserDAO();
        this.request = request;
        this.response = response;
    }

    public void listUser() throws ServletException, IOException {
        listUser(null, null);
    }

    public void listUser(String message, String alertType) throws ServletException, IOException {
        List<Users> listUsers = userDAO.listAll();
        request.setAttribute("listUsers", listUsers);
        if (message != null) {
            request.setAttribute("message", message);
            request.setAttribute("alertType", alertType);
        }

        String listPage = "user_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
    }

    public void createUser() throws ServletException, IOException {
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");

        Users existUser = userDAO.findByEmail(email);

        if (existUser != null) {
            String message = "Could not create user. A user with email: " + email + " already exists";
            String alertType = "warning";
            listUser(message, alertType);
        } else {
            Users user = new Users(email, fullName, password);
            userDAO.create(user);
            listUser("A new user created successfully", "success");
        }
    }

    public void editUser() throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        Users user = userDAO.get(userId);
        String editPage = "user_form.jsp";
        String fullName = user.getFullName();
        fullName = fullName.replace(" ", "&nbsp;");

        request.setAttribute("fullName", fullName);
        request.setAttribute("user", user);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
        requestDispatcher.forward(request, response);

    }

    public void updateUser() throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullname");
        String password = request.getParameter("password");

        Users userById = userDAO.get(userId);

        Users userByEmail = userDAO.findByEmail(email);

        if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
            String message = "Could not update user. User with email " + email + " already exists.";
            String alertType = "warning";
            listUser(message, alertType);
        }else {
        	Users user = new Users(userId, email, fullName, password);
            userDAO.update(user);
            String message = "User has been updated successfully";
            listUser(message, "success");
        }

    }

    public void deleteUser() throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        userDAO.delete(userId);

        String message = "User has been deleted successfully";
        listUser(message, "success");
    }

    public void login() throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean loginResult = userDAO.checkLogin(email, password);
        if (loginResult) {
            request.getSession().setAttribute("useremail", email);
            request.getRequestDispatcher("/admin/").forward(request, response);
        } else {
            String message = "Login failed";
            request.setAttribute("message", message);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }
}