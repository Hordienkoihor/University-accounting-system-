package service.interfaces;

import domain.University;

public interface UniversityServiceInt {
     boolean isUniversityLoaded();
     void loadUniversity(String path);
     void createUniversity(String fullName, String shortName, String city, String address);
     University getUniversity();

}
