package catchmyshift.catchmyshift.listitem;

/**
 * Created by melli on 02/09/2017.
 */

public class WorkExperienceListItem {
    private String JobPosition;
    private String JobCompany;
    private String JobDate;
    private String JobDescription;
    private String JobSupervisorName;
    private String JobSupervisorNumber;
    private String JobCurrentJb;
    private String JobStartDate;
    private String JobEndDate;

    public WorkExperienceListItem(String jobPosition, String jobCompany, String jobDate, String jobDescription, String jobSupervisorName,
                                  String jobSupervisorNumber, String jobCurrentJb, String jobStartDate, String jobEndDate) {
        JobPosition = jobPosition;
        JobCompany = jobCompany;
        JobDate = jobDate;
        JobDescription = jobDescription;
        JobSupervisorName = jobSupervisorName;
        JobSupervisorNumber =  jobSupervisorNumber;
        JobCurrentJb = jobCurrentJb;
        JobStartDate = jobStartDate;
        JobEndDate = jobEndDate;
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

    public String getJobDescription(){return JobDescription;}

    public String getJobSupervisorName() {
        return JobSupervisorName;
    }

    public String getJobSupervisorNumber() {
        return JobSupervisorNumber;
    }

    public String getJobCurrentJb() {
        return JobCurrentJb;
    }

    public String getJobStartDate() {
        return JobStartDate;
    }

    public String getJobEndDate() {
        return JobEndDate;
    }
}
