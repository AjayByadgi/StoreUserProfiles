package com.ajay.image_upload_aws.bucket;

public enum BucketName {

    PROFILE_IMAGE("ajay-image-upload");

    private final String bucketName;


    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
