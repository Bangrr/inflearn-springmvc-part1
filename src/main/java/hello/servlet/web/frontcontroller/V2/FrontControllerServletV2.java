package hello.servlet.web.frontcontroller.V2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.V2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.V2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet { // /front-controller/v2 하위 모든 요청은 이 서블릿에서 받아들인다.

    private Map<String, ControllerV2> controllerMap = new HashMap<>(); // <매핑 URL, 호출될 컨트롤러>

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV2 controller = controllerMap.get(requestURI); // requestURI 를 조회해서 controllerMap 에서 실제 호출할 컨트롤러를 찾는다.
        if (controller == null) { // 만약 없다면 404 상태코드를 반환한다.
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);// 있다면 process() 를 통해서 해당 컨트롤러를 실행한다.
        view.render(request, response);
    }
}
