package domain.enums;

public enum ScientificDegree {
    ASSOCIATE("Associate of Science"),
    BACHELOR("Bachelor of Science"),
    MASTER("Master of Science"),
    DOCTORATE("Doctor of Philosophy"),
    NONE("None");

    public final String displayName;

    ScientificDegree(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Scientific degree: " + displayName;
    }
}
