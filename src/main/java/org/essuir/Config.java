package org.essuir;

/**
 * Created by Igor on 16.02.2017.
 */
public class Config {
    private static final String DSPACE_URL = "";
    private static final Integer OFFSET = 20;
    private static final String SUBMITTER_EMAIL = "";
    private static final String DSPACE_PATH = "/opt/dspace/bin/dspace packager";
    private static final Long COLLECTION_ID = 318L;

    public static Long getCollectionId() {
        return COLLECTION_ID;
    }

    public static String getDspacePath() {
        return DSPACE_PATH;
    }

    public static String getSubmitterEmail() {
        return SUBMITTER_EMAIL;
    }

    public static String getDspaceUrl() {
        return DSPACE_URL;
    }

    public static Integer getOFFSET() {
        return OFFSET;
    }
}
