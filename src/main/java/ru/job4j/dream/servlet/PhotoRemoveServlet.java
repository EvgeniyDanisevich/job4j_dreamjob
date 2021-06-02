package ru.job4j.dream.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PhotoRemoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String photoName = req.getParameter("id");
        for (File file : Objects.requireNonNull(new File("c:\\images\\").listFiles())) {
            if (file.getName().substring(0, file.getName().lastIndexOf(".")).equals(photoName)) {
                if (file.delete()) {
                    break;
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
