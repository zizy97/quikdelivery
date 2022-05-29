package com.quikdeliver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@Getter
public enum BucketName {
    PACKAGE_IMAGES("quikapp-image-store");
    private final String bucketName;
}
