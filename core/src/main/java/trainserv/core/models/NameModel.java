package trainserv.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class NameModel {
    @Inject
    private String name;



    private String formattedName;

    @PostConstruct
    protected void initModel() {

         this.formattedName = Character.toUpperCase(this.name.charAt(0))+this.name.substring(1);
    }
    public String getFormattedName() {
        return formattedName;
    }
}
