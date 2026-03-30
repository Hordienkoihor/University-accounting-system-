package service.interfaces;

import domain.University;

public interface UniversityServiceInt {
     void save(University university);
     boolean isUniversityLoaded();
     void loadUniversity(String path);
     void createUniversity(String fullName, String shortName, String city, String address);
     University getUniversity();

}
