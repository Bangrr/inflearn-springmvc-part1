package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 * <p></p>
 * 2. 동일한 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&username=kim&age=20
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[전체 파라미터 조회] - start");

        /*
        Enumeration<String> parameterNames = request.getParameterNames(); // 파라미터 이름들 모두 조회
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + "=" + request.getParameter(paramName));
        }
        */

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));

        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username"); // 단일 파라미터 조회
        String age = request.getParameter("age");

        System.out.println("request.getParameter(username) = " + username);
        System.out.println("request.getParameter(age) = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        System.out.println("request.getParameterValues(username)");
        String[] usernames = request.getParameterValues("username"); // 복수 파라미터 조회
        for (String name : usernames) {
            System.out.println("username = " + name);
        }

//        Map<String, String[]> parameterMap = request.getParameterMap(); // 파라미터를 Map으로 조회

        response.getWriter().write("ok");
    }
}

/*
 * [ GET 쿼리 파라미터 방식 ]
 * 메시지 바디 없이 URL의 쿼리 파라미터를 사용해서 데이터를 전달
 * ex) 검색, 필터, 페이징 등에서 많이 사용하는 방식
 * 쿼리 파라미터는 URL에 '?'를 시작으로 보낼 수 있고 추가 파라미터는 '&'로 구분하면 된다.
 *
 * username=hello&username=kim 과 같이 파라미터 이름은 하나인데 값이 중복일 경우,
 * request.getParameterValues() 를 사용해야 한다.
 * 중복일 때 request.getParameter()를 사용하면 request.getParameterValues() 의 첫 번째 값을 반환한다.
 *
 * [ POST HTML Form ]
 * HTML Form을 사용해서 데이터를 전송하면 다음과 같은 특징이 있다.
 *      content-type: application/x-www-form-urlencoded
 *      메시지 바디에 쿼리 파라미터 형식으로 데이터를 전달한다. ( username=hello&age=20 )
 *
 * application/x-www-form-urlencoded 형식은 GET 쿼리 파라미터 형식과 같아서
 * 쿼리 파라미터 조회 메서드를 그대로 사용하면 된다. ( 클라이언트 입장에서는 다르지만 서버 입장에서는 형식이 동일하다. )
 * request.getParameter() 로 GET URL 쿼리 파라미터 형식도 지원하고 POST HTML Form 형식도 둘 다 지원한다.
 *
 * content-type은 HTTP 메시지 바디의 데이터 형식을 지정한다.
 * GET 쿼리 파라미터 형식은 HTTP 메시지 바디를 사용하지 않기 때문에 content-type이 없다.
 * POST HTML Form 형식은 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 떄문에 content-type을 꼭 지정해아 한다.
 * 폼으로 데이터를 전송하는 형식을 application/x-www-form-urlencoded 라 한다.
 *
 * Postman 을 사용해서도 테스트를 할 수 있다.
 * http://localhost:8080/request-param
 * POST 선택, Body > x-www-form-urlencoded 선택, Body 내용 입력 ( username=kim , age=20 )
 * Headers content-type: application/x-www-form-urlencoded 추가됐는지 확인 --> Send 후 로그 확인
 */
