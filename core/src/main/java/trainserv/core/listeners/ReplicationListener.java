package trainserv.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

@Component(service = EventHandler.class,immediate = true,property = {

                Constants.SERVICE_DESCRIPTION + "= This event handler listens the events on page activation",
                EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC})


public class ReplicationListener implements EventHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TOPIC = "trainserv/core/replicationjob";

    @Reference
    private JobManager jobManager;



    @Override
    public void handleEvent(final Event event) {
        ReplicationAction action = ReplicationAction.fromEvent(event);
        if (action.getType().equals(ReplicationActionType.ACTIVATE)){
            if (action.getPath() != null)
            {
                try{
                    HashMap<String,Object> jobprops = new HashMap<String, Object>();
                    jobprops.put("PAGE_PATH",action.getPath());
                    jobManager.addJob(TOPIC ,jobprops);
                    logger.info("=======topic:=="+TOPIC+ "======pay load==="+action.getPath()+"was added to the Job manager");
                } catch (Exception e) {
                    logger.error("=======topic:=="+TOPIC+ "======pay load==="+action.getPath()+"was erroneous");
                    e.printStackTrace();
                }
            }
        }
    }
}
