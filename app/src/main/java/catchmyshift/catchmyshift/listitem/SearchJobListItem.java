package catchmyshift.catchmyshift.listitem;

/**
 * Created by silve on 31/08/2017.
 */

public class SearchJobListItem {

    private String jobName;
    private String jobAddress;
    private String jobVacancyNum;

    public SearchJobListItem(String jobName, String jobAddress, String jobVacancyNum){
        this.jobName = jobName;
        this.jobAddress=jobAddress;
        this.jobVacancyNum = jobVacancyNum;
    }

    public String getJobName(){return jobName;}
    public String getJobAddress(){return jobAddress;}
    public String getJobVacancyNum(){return jobVacancyNum;}
}
