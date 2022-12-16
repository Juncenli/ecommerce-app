package com.shopme.common;

public class Constants {
    public static final String S3_BASE_URI;

    // 首先设置环境变量, 设置环境变量的原因是，当我改变bucket name的时候不需要再对代码进行修改，只需要对环境变量进行修改
    // if we change the bucket name, we must also change the environment variable name.
    // But we don't need to change the codes.
    static {
        String bucketName = System.getenv("AWS_BUCKET_NAME");
        String region = System.getenv("AWS_REGION");
        // https://shopme-files.s3.us-east-1.amazonaws.com/
        String patternWithRegion = "https://%s.s3.%s.amazonaws.com";
        S3_BASE_URI = bucketName == null ? "" : String.format(patternWithRegion, bucketName, region);
    }

//    public static void main(String[] args) {
//        System.out.println("S3 BASE URI: " + S3_BASE_URI);
//    }
}
