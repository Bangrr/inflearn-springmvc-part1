package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet  extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println("helloData.username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}

/*
 * JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 JSON 변환 라이브러리가 필요한데,
 * Spring MVC 를 선택하면 기본으로 Jackson 라이브러리( ObjectMapper )를 함께 제공한다.
 *
 * HTML form 데이터도 메시지 바디를 통해 전송되므로 직접 읽을 수는 있으나,
 * 이미 제공하는 파라미터 조회 기능( request.getParameter() )을 사용하면 된다.
 *
 * http://localhost:8080/request-body-json
 * POST 선택, Body > raw 선택, JSON 선택, Body 내용 입력 ( {"username": "hello", "age": 20} )
 * Headers content-type: application/json 확인 --> Send 후 로그 확인
 *      messageBody = {"username": "hello", "age": 20}
 *      helloData.username = hello
 *      helloData.age = 20
 */