package com.ajay.image_upload_aws.profile;

import com.ajay.image_upload_aws.bucket.BucketName;
import com.ajay.image_upload_aws.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;
    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;

        this.fileStore = fileStore;
    }


    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID profileId, MultipartFile file) {
        //1. check if image is not empty
        //2. check if file is an image
        //3. check if the user exists in the database
        // 4 . grab some meta data from file if any
        //5. store the image in s3 and update database (userProfileImageLink) with s3 image link
        if (file.isEmpty()) {
            throw new IllegalStateException("File is empty");

        }

        String contentType = file.getContentType();
        if (contentType == null || !Arrays.asList(
                ContentType.IMAGE_JPEG.getMimeType(),
                ContentType.IMAGE_PNG.getMimeType(),
                ContentType.IMAGE_GIF.getMimeType()
        ).contains(contentType)) {
            throw new IllegalStateException("Unsupported image type: " + contentType);
        }

        UserProfile user = userProfileDataAccessService.getUserProfiles()
                .stream().filter(userProfile -> userProfile.getUserProfileId().equals(profileId))

                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User profile not found"));

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        System.out.println("Uploading user profile image to " + path);
        String fileName = String.format("%s-%s",file.getOriginalFilename(), UUID.randomUUID().toString());
        try {
            fileStore.save(BucketName.PROFILE_IMAGE.getBucketName(), fileName, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(fileName);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save file", e);
        }



    }

    public byte[] downloadUserProfileImage(UUID profileId) {
        UserProfile user = userProfileDataAccessService.getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(profileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User profile not found"));

        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), key))
                .orElse(new byte[0]);
    }

    public void addUser(UserProfile userProfile) {
        userProfileDataAccessService.addUserProfile(
                new UserProfile(UUID.randomUUID(), userProfile.getUsername(), null)
        );
    }

}
