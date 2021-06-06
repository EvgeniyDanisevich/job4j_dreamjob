package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GreetingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String input = "";
        try (BufferedReader br = new BufferedReader(req.getReader())) {
            input += br.readLine();
        }
        String name = input.split(":")[1]
                .replace(" ", "")
                .replace("\"", "")
                .replace("}", "");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String json = "{" + "\"name\":" + "\"" + name + "\"" + "}";
        writer.println(json);
        writer.flush();
        writer.close();
    }
}