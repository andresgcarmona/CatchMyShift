package catchmyshift.catchmyshift.listitem;

/**
 * Created by silve on 31/08/2017.
 */

public class SearchJobListItem {

    private String jobName;
    private String jobAddress;
    private String jobVacancyNum;
    private String jobPubDate;
    private String jobStartDate;
    private String jobEndDate;
    private String jobStartTime;
    private String jobEndTime;
    private String jobSalary;
    private String jobTask;
    private String jobRequirements;
    private String jobLat;
    private String jobLong;
    private String companyName;
    private String companyDescription;
    private String companyLogo;

    public SearchJobListItem(String jobName, String jobAddress, String jobVacancyNum, String jobPubDate, String jobStartDate, String jobEndDate,
                             String jobStartTime, String jobEndTime, String jobSalary, String jobTask, String jobRequirements,
                             String jobLat, String jobLong, String companyName, String companyDescription, String companyLogo){
        this.jobName = jobName;
        this.jobAddress=jobAddress;
        this.jobVacancyNum = jobVacancyNum;
        this.jobPubDate = jobPubDate;
        this.jobStartDate=jobStartDate;
        this.jobEndDate = jobEndDate;
        this.jobStartTime = jobStartTime;
        this.jobEndTime = jobEndTime;
        this.jobSalary = jobSalary;
        this.jobTask = jobTask;
        this.jobRequirements=jobRequirements;
        this.jobLat = jobLat;
        this.jobLong = jobLong;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.companyLogo = companyLogo;
    }

    public String getJobName(){return jobName;}
    public String getJobAddress(){return jobAddress;}
    public String getJobVacancyNum(){return jobVacancyNum;}
    public String getJobPubDate(){return jobPubDate;}
    public String getJobStartDate(){return jobStartDate;}
    public String getJobEndDate(){return jobEndDate;}
    public String getJobStartTime(){return jobStartTime;}
    public String getJobEndTime(){return jobEndTime;}
    public String getJobSalary(){return jobSalary;}
    public String getJobTask(){return jobTask;}
    public String getJobRequirements(){return jobRequirements;}
    public String getJobLat(){return  jobLat;}
    public String getJobLong(){return jobLong;}
    public String getCompanyName(){return  companyName;}
    public String getCompanyDescription(){return companyDescription;}
    public String getCompanyLogo(){return  companyLogo;}

}
