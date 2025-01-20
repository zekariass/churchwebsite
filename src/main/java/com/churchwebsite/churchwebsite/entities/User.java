package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
//@PasswordMatcher
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", nullable = true)
    private UserProfile userProfile;

    @NotEmpty(message = "Username must not be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long.")
    private String username;

    @NotEmpty(message = "You must provide email for communication")
    @Email(message = "Invalid email.")
    private String email;

    @NotEmpty(message = "You must provide password")
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;


    @Transient
    private String passwordConfirm;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registrationTime;

    private boolean isActive;

    private boolean isBlocked;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String email, String password, String passwordConfirm, LocalDateTime registrationTime, boolean isActive, boolean isBlocked) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.registrationTime = registrationTime;
        this.isActive = isActive;
        this.isBlocked = isBlocked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserProfile getUserProfileId() {
        return userProfile;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfile = userProfileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    @AssertTrue(message = "Password does not match.")
    public boolean isPasswordMatching(){
        return password != null && password.equals(passwordConfirm);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userProfile=" + userProfile +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", registrationTime=" + registrationTime +
                ", isActive=" + isActive +
                ", isBlocked=" + isBlocked +
                ", roles=" + roles +
                '}';
    }
}
