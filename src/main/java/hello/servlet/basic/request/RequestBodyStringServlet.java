package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // UTF_8 문자표 지정

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");
    }
}
/*
 * inputStream은 byte코드를 반환한다.
 * byte 코드를 우리가 읽을 수 있는 문자(String)로 보려면 문자표(CharSet)를 지정해주어야 한다.
 *
 * http://localhost:8080/request-body-string
 * POST 선택, Body > raw 선택, Text 선택, Body 내용 입력 ( hello! )
 * Headers content-type: text/plain 확인 --> Send 후 로그 확인 ( messageBody = hello! )
 */