package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Content-Type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); // 주석처리하면 크롬기준 ISO-8859-1 으로 인코딩된다. 그럼 한글이 안나온다.

        HelloData helloData = new HelloData();
        helloData.setUsername("김삿갓");
        helloData.setAge(20);

        // {"username":"kim", "age":20}
        String result = objectMapper.writeValueAsString(helloData);

        response.getWriter().write(result); // 위 인코딩을 주석처리하려면 아래 주석과 같이 출력해야 utf-8로 된다.
//        OutputStream os = response.getOutputStream();
//        PrintStream out = new PrintStream(os, true);
//        out.println(result);
    }
}
/*
 * HTTP 응답으로 JSON을 반환할 때는 Content-Type 을 "application/json" 로 지정해줘야 한다.
 * Jackson 라이브러리가 제공하는 "objectMapper.writeValueAsString()" 를 사용하면 객체를 JSON 문자로 변경할 수 있다.
 *
 * application/json 은 스펙상 utf-8 형식을 사용하도록 정의되어 있지만,
 * response.getWriter() 를 사용하면 추가 파라미터를 자동으로 추가해버려서
 * charset=utf-8 이라고 명시해 주지 않으면 크롬기준 ISO-8859-1 로 인코딩 된다.
 *
 * response.getOutputStream() 으로 출력하면 추가 파라미터 없이 application/json 자체로 utf-8 형식을 사용할 수 있다.
 *
 */