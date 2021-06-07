package ru.job4j.dream.servlet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemPostStore;
import ru.job4j.dream.store.PsqlPostStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlPostStore.class)
public class PostServletTest {

    @Test
    public void whenCreatePost() throws IOException {
        Store<Post> store = MemPostStore.instOf();
        PowerMockito.mockStatic(PsqlPostStore.class);
        PowerMockito.when(PsqlPostStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PowerMockito.when(req.getParameter("id")).thenReturn("0");
        PowerMockito.when(req.getParameter("name")).thenReturn("Post Name");
        PowerMockito.when(req.getParameter("description")).thenReturn("Post Description");
        new PostServlet().doPost(req, resp);
        Post result = store.findAll().iterator().next();
        Assert.assertEquals(result.getName(), "Post Name");
        Assert.assertEquals(result.getDescription(), "Post Description");
    }

    @Test
    public void whenDoGet() throws IOException, ServletException {
        Store<Post> store = MemPostStore.instOf();
        PowerMockito.mockStatic(PsqlPostStore.class);
        PowerMockito.when(PsqlPostStore.instOf()).thenReturn(store);
        PostServlet postServlet = new PostServlet();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        String address = "posts.jsp";
        String user = "user";
        when(req.getRequestDispatcher(address)).thenReturn(dispatcher);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        postServlet.doGet(req, resp);
        verify(req).getRequestDispatcher(address);
        verify(req).setAttribute("posts", store.findAll());
        verify(req).setAttribute("user", session.getAttribute("user"));
        verify(dispatcher).forward(req, resp);
    }
}