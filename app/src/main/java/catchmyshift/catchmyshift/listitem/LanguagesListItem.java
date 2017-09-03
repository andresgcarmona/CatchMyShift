package catchmyshift.catchmyshift.listitem;

/**
 * Created by melli on 03/09/2017.
 */

public class LanguagesListItem {
    private String Language;
    private String LanguageLevel;
    private String LanguageComments;

    public LanguagesListItem(String language, String languageLevel, String languageComments) {
        Language = language;
        LanguageLevel = languageLevel;
        LanguageComments = languageComments;
    }

    public String getLanguage() {
        return Language;
    }

    public String getLanguageLevel() {
        return LanguageLevel;
    }

    public String getLanguageComments() {
        return LanguageComments;
    }
}
