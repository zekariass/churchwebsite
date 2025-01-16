package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import com.churchwebsite.churchwebsite.repositories.UserProfileRepository;
import com.churchwebsite.churchwebsite.services.storage.CloudinaryFileStorageManager;
import com.churchwebsite.churchwebsite.services.storage.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserService userService;
    private final CloudinaryFileStorageManager cloudinaryFileStorageManager;

    @Value("${local.file.user.profile-dir}")
    private String localUserProfilePicDir;

    @Value("${cloudinary.file.user.profile-dir}")
    private String cloudinaryUserProfilePicDir;

    @Value("${file.storage.type}")
    private String fileStorageType;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, UserService userService, CloudinaryFileStorageManager cloudinaryFileStorageManager) {
        this.userProfileRepository = userProfileRepository;
        this.userService = userService;
        this.cloudinaryFileStorageManager = cloudinaryFileStorageManager;
    }

    public void saveUserProfile(UserProfile userProfile){
        userProfileRepository.save(userProfile);
    }


    public void updateUserProfile(UserProfile userProfile, MultipartFile userProfilePhoto){

//        UserProfile updateUserProfile = null;

        // Check if userProfile has data and save it
        if (userProfile != null) {
                String profilePhotoPath;
                UserProfile existingUserProfile = userProfileRepository.findById(userProfile.getUserProfileId()).orElse(null);

                if (userProfilePhoto != null && !userProfilePhoto.isEmpty()) {
                    if(fileStorageType.equalsIgnoreCase("cloudinary")){
                        Map result = cloudinaryFileStorageManager.storeFile(userProfilePhoto, cloudinaryUserProfilePicDir);
                        profilePhotoPath = (String) result.get("url");
                        userProfile.setPublicId((String) result.get("public_id"));

                    }else{
                        LocalFileStorageManager localFileStorageManager = getLocalFileStorageManager(existingUserProfile);
                        profilePhotoPath = localFileStorageManager.storeFile(userProfilePhoto);
                        userProfile.setPublicId(null);
                    }

                    userProfile.setProfilePhoto(profilePhotoPath);
                }


                if((userProfilePhoto == null || userProfilePhoto.isEmpty()) && existingUserProfile != null){
                    userProfile.setProfilePhoto(existingUserProfile.getProfilePhoto());
                }

                // Save userProfile after assigning the profile photo if needed
                UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
                User user = userService.getCurrentUser().getUser();

                // set the updated user profile to current user to update the template until next login
                user.setUserProfile(updatedUserProfile);
                userService.saveUser(user);
            }
        }

    private LocalFileStorageManager getLocalFileStorageManager(UserProfile existingUserProfile) {
        LocalFileStorageManager fileStorageManager = new LocalFileStorageManager(localUserProfilePicDir);

        // If existing user profile is present and has profile image already, delete the existing photo before saving new one
        if(existingUserProfile != null && existingUserProfile.getProfilePhoto() != null){
            String[] filePath = existingUserProfile.getProfilePhoto().split("/");
            String fileName = filePath[filePath.length - 1];
            fileStorageManager.deleteFile(fileName);
        }
        return fileStorageManager;
    }
}

