package catchmyshift.catchmyshift.listitem;

/**
 * Created by melli on 02/09/2017.
 */

public class WorkExperienceListItem {
    private String JobPosition;
    private String JobCompany;
    private String JobDate;

    public WorkExperienceListItem(String jobPosition, String jobCompany, String jobDate) {
        JobPosition = jobPosition;
        JobCompany = jobCompany;
        JobDate = jobDate;
    }

    public String getJobPosition() {
        return JobPosition;
    }

    public String getJobCompany() {
        return JobCompany;
    }

    public String getJobDate() {
        return JobDate;
    }
}
