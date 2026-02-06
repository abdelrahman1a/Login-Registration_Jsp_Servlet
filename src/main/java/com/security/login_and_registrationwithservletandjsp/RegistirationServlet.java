package com.security.login_and_registrationwithservletandjsp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegistirationServlet extends HttpServlet {


        private String message;

        public void init() {
            message = "Hello World!";
        }

//        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//            response.setContentType("text/html");
//
//            // Hello
//            PrintWriter out = response.getWriter();
//            out.println("<html><body>");
//            out.println("<h1>" + message + "</h1>");
//            out.println("</body></html>");
//        }
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

            String uname = request.getParameter("name");
            String uemail = request.getParameter("email");
            String upwd = request.getParameter("pass");
            String repeatpwd = request.getParameter("re_pass");

            String uphone = request.getParameter("contact");
            RequestDispatcher dispatcher = null;
            Connection conn = null;

            if(uname == null || uname.equals(""))
            {
                request.setAttribute("status" , "inValidUserName");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request , response);
            }
            if(uemail == null || uemail.equals(""))
            {
                request.setAttribute("status" , "inValidEmail");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request , response);
            }
            if(upwd == null || upwd.equals(""))
            {
                request.setAttribute("status" , "inValidPassword");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request , response);
            }
            if(repeatpwd == null || repeatpwd.equals(""))
            {
                request.setAttribute("status" , "inValidPassword");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request , response);
            }
            if(!repeatpwd.equals(upwd))
            {
                request.setAttribute("status" , "NotMatchedPassword");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request , response);
            }
            if(upwd.length() < 8)
            {
                request.setAttribute("status" , "lengthTooShortPassword");
                dispatcher = request.getRequestDispatcher("registration.jsp");
                dispatcher.forward(request , response);
            }
            try{
                Class.forName("org.postgresql.Driver");

                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/registirationandlogin","postgres","AFHJ12&asd");

                PreparedStatement pst = conn.prepareStatement("insert into users (username , uemail , upwd , uphone)  values(?,?,?,?)");
                pst.setString(1, uname);
                pst.setString(2, uemail);
                pst.setString(3, upwd);
                pst.setString(4, uphone);

                int rowCount = pst.executeUpdate();
                dispatcher = request.getRequestDispatcher("registration.jsp");

                if(rowCount > 0){
                    request.setAttribute("status", "success");
                }
                else{
                    request.setAttribute("status", "failed");
                }
                dispatcher.forward(request, response);

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



        public void destroy() {
        }

}
