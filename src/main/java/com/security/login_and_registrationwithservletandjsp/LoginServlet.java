package com.security.login_and_registrationwithservletandjsp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String uemail= request.getParameter("username");
        String upwd = request.getParameter("password");
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();
        Connection conn = null;

        if(uemail == null || uemail.equals(""))
        {
            request.setAttribute("status" , "inValidEmail");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request , response);
        }
        if(upwd == null || upwd.equals(""))
        {
            request.setAttribute("status" , "inValidPassword");
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request , response);
        }

        try{
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/registirationandlogin","postgres","AFHJ12&asd");

            PreparedStatement pst = conn.prepareStatement("Select  * from users where uemail=? and upwd=?");
            pst.setString(1, uemail);
            pst.setString(2, upwd);

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                session.setAttribute("name", rs.getString("username"));
                dispatcher = request.getRequestDispatcher("index.jsp");
            }
            else{
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");

            }
            dispatcher.forward(request,response);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                if(conn != null){
                    conn.close();

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }





    }

}
