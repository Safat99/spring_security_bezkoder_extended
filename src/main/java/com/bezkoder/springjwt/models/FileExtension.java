package com.bezkoder.springjwt.models;

public enum FileExtension {

    pdf, jpg, jpeg, png;

    public static boolean isValid(String fileExtension) {
        try {
            FileExtension.valueOf(fileExtension.toLowerCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
