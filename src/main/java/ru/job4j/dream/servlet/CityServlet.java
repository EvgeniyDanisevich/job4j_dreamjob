package ru.job4j.dream.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.dream.json.JsonMapper;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.PsqlCityStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class CityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Collection<City> cities = new PsqlCityStore().findAll();
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        String json = JsonMapper.serialize(cities);
        writer.println(json);
        writer.flush();
        writer.close();
    }
}
