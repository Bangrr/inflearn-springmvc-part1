package hello.servlet.web.frontcontroller.V3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.V3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet { // /front-controller/v3 하위 모든 요청은 이 서블릿에서 받아들인다.

    private Map<String, ControllerV3> controllerMap = new HashMap<>(); // <매핑 URL, 호출될 컨트롤러>

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI); // requestURI 를 조회해서 controllerMap 에서 실제 호출할 컨트롤러를 찾는다.
        if (controller == null) { // 만약 없다면 404 상태코드를 반환한다.
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HashMap<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap); // 있다면 process() 를 통해서 해당 컨트롤러를 실행한다.

        String viewName = mv.getViewName(); // 뷰 논리이름
        MyView view = viewResolver(viewName); // 뷰 리졸버를 통해 물리 뷰 경로로 변경

        view.render(mv.getModel(), request, response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static HashMap<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName -> {
            paramMap.put(paramName, request.getParameter(paramName));
        });
        return paramMap;
    }
}
