

package trainserv.core;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component(service= JobConsumer.class,immediate = true,
                property={ Constants.SERVICE_DESCRIPTION + "= This job consumer consumes job replication event",
                          JobConsumer.PROPERTY_TOPICS+"= trainserv/core/replicationjob"})
public class ReplicationLogger implements JobConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public JobResult process(final Job job){
        final String pagePath  = job.getProperty("PAGE_PATH").toString();

        ResourceResolver resourceResolver = null;
        try{
            Map<String, Object> serviceParams = new HashMap<String, Object>();
            serviceParams.put(resourceResolverFactory.SUBSERVICE,"datawrite");
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceParams);
        } catch (LoginException e) {
            e.printStackTrace();
        }
       //create a page object to log  its title
        final PageManager pm = resourceResolver.adaptTo(PageManager.class);
        final Page page = pm.getContainingPage(pagePath);
        if (page!= null){
            logger.info("+++++====ACTIVATION OF PAGE {}",page.getTitle());
        }
        else{
            logger.info("+++++====ACTIVATION OF PAGE is a failure",page.getTitle());
        }
        return JobResult.OK;
    }
}
