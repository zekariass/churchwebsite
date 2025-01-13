package com.churchwebsite.churchwebsite.specifications;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasEmail(String email){
        return (root, query, cb) -> (email == null || email.isEmpty()) ? cb.conjunction() : cb.equal(root.get("email"), email);
    }

    public static Specification<User> hasUsername(String username){
        return (root, query, cb) -> (username == null || username.isEmpty()) ? cb.conjunction() : cb.like(root.get("username"), '%' + username + '%');
    }

    public static Specification<User> hasFirstName(String firstName){
        return (root, query, cb) -> {
            if(firstName == null || firstName.isEmpty()){
                return cb.conjunction();
            }

            Join<User, UserProfile> userUserProfileJoin = root.join("userProfile", JoinType.LEFT);

            return cb.like(userUserProfileJoin.get("firstName"), '%' + firstName + '%');
        };
    }

    public static Specification<User> hasLastName(String lastName){
        return (root, query, cb) -> {
            if(lastName == null || lastName.isEmpty()){
                return cb.conjunction();
            }

            Join<User, UserProfile> userUserProfileJoin = root.join("userProfile", JoinType.LEFT);

            return cb.like(userUserProfileJoin.get("lastName"), '%' + lastName + '%');
        };
    }

    public static Specification<User> hasRole(Integer roleID){
        return (root, query, cb) -> {
            if(roleID == null){
                return cb.conjunction();
            }

            Join<User, Role> userRoleJoin = root.join("roles", JoinType.LEFT);

            return cb.equal(userRoleJoin.get("roleId"), roleID);
        };
    }
}
