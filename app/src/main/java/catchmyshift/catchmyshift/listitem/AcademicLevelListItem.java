package catchmyshift.catchmyshift.listitem;

/**
 * Created by melli on 03/09/2017.
 */

public class AcademicLevelListItem {
    private String AcademicDegree;
    private String AcademicInstitution;
    private String AcademicYear;

    public AcademicLevelListItem(String academicDegree, String academicInstitution, String academicYear) {
        AcademicDegree = academicDegree;
        AcademicInstitution = academicInstitution;
        AcademicYear = academicYear;
    }

    public String getAcademicDegree() {

        return AcademicDegree;
    }

    public String getAcademicInstitution() {
        return AcademicInstitution;
    }

    public String getAcademicYear() {
        return AcademicYear;
    }
}
