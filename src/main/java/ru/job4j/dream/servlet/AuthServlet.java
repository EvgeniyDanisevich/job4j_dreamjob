package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.Store;
import ru.job4j.dream.store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Store<User> userStore = UserStore.instOf();
        User findUser = userStore.findByEmail(email);
        if (findUser == null) {
            req.setAttribute("error", "Пользователя с таким email не существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if (findUser.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", findUser);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sc = req.getSession();
        sc.setAttribute("user", null);
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}