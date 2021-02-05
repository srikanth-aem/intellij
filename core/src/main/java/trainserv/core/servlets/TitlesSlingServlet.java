package trainserv.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION +"= titleservlet","sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes="+ "/apps/trainserv/components/content/title","sling.servlet.selectors="+ "model",
        })

public class TitlesSlingServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUid = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource();
        resp.setContentType("text/html");
        resp.getWriter().print("<h1>having fon with servlets</h1>");
        resp.getWriter().write("Title = " + resource.adaptTo(ValueMap.class).get("jcr:title"));
    }

}
