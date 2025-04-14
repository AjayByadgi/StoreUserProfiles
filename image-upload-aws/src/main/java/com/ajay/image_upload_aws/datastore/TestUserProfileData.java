package com.ajay.image_upload_aws.datastore;

import org.springframework.stereotype.Repository;
import com.ajay.image_upload_aws.profile.UserProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class acts as a mock data store for user profiles.
 * It's annotated with @Repository so Spring recognizes it as a data-access component.
 */
@Repository
public class TestUserProfileData {

    // This is a static list of user profiles that is shared across all instances of this class.
    // It acts like an in-memory database for demo/testing.
    private static final List<UserProfile> userProfiles = new ArrayList<>();

    // Static initializer block: This block runs once when the class is loaded.
    // It adds some sample user profiles to the list.
    static {
        userProfiles.add(new UserProfile(UUID.fromString("fc653012-38c0-4564-8113-1a9e6ae2d277"), "Snoopy", null));
        userProfiles.add(new UserProfile(UUID.fromString("bf21a0f6-7ed1-4233-8ab2-ac60ec13b96a"), "Charlie", null)); //
    }

    /**
     * Public method to expose the test user profiles.
     * @return a list of all test user profiles
     */
    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }
}
