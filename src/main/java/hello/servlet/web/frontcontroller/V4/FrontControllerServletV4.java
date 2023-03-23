package hello.servlet.web.frontcontroller.V4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.V4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.V4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet { // /front-controller/v4 하위 모든 요청은 이 서블릿에서 받아들인다.

    private Map<String, ControllerV4> controllerMap = new HashMap<>(); // <매핑 URL, 호출될 컨트롤러>

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerMap.get(requestURI); // requestURI 를 조회해서 controllerMap 에서 실제 호출할 컨트롤러를 찾는다.
        if (controller == null) { // 만약 없다면 404 상태코드를 반환한다.
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); // 모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다.

        String viewName = controller.process(paramMap, model); // 뷰 논리이름

        MyView view = viewResolver(viewName); // 뷰 리졸버를 통해 물리 뷰 경로로 변경
        view.render(model, request, response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName -> {
            paramMap.put(paramName, request.getParameter(paramName));
        });
        return paramMap;
    }
}
