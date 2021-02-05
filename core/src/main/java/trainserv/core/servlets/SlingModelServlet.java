package trainserv.core.servlets;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import trainserv.core.models.StockModel;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION +"= slingmodelservlet","sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes="+ "/apps/trainserv/components/structure/page","sling.servlet.selectors="+ "model"
        })

public class SlingModelServlet extends SlingAllMethodsServlet {
        private static final long serialVersionUID = 1L;
        Logger logger = LoggerFactory.getLogger(this.getClass());

        public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException,IOException{
                logger.info("inside sling model test servlet");
                response.setContentType("text/html");

                ResourceResolver resourceResolver;

                try{
                 resourceResolver = request.getResourceResolver();
                 String nodePath = request.getRequestPathInfo().getSuffix();
                 if(nodePath != null){
                        Resource resource = resourceResolver.getResource(nodePath);

                         Resource parent = resource.getChild("lastTrade");

                         ValueMap valueMap = parent.adaptTo(ValueMap.class);
                        // valueMap.put(ResourceResolverFactory.SUBSERVICE, "datawrite");

                         response.getOutputStream().println("<br /> <h3>");
                         response.getOutputStream().println("last Trade node with valuemap is");
                         response.getOutputStream().println(" </h3></br>");
                         response.getOutputStream().println("(lastTrade)"+valueMap.get("lastTrade").toString() +"requested Date"+valueMap.get("requestDate").toString() +"requested Time"+valueMap.get("requestTime").toString());


                         //Adapt the resource to our model
                         StockModel stockModel = resource.adaptTo(StockModel.class);
                         response.getOutputStream().println("<br /> <h3>");
                         response.getOutputStream().println("last Trade node with stock model is");
                         response.getOutputStream().println(" </h3></br>");
                         response.getOutputStream().println("lastTrade"+stockModel.getLastTrade() +"requested time"+stockModel.getTimestamp());
                 }
                 else{
                         response.getWriter().println("cant get last node in uri,enter a suffix");
                 }
                } catch (Exception e) {
                        response.getWriter().println("total failure");
                        logger.error(e.getMessage());
                }
        }

        }

