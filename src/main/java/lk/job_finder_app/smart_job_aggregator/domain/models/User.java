package lk.job_finder_app.smart_job_aggregator.domain.models;


import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;

import java.util.Set;

public class User {
    private Long userId;
    private String userName;
    private String userEmail;
    private RoleName role;
    private Set<String> skillsRequired;

    public User(Long userId, String userName, String userEmail, RoleName role, Set<String> skillsRequired) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.role = role;
        this.skillsRequired = skillsRequired;
    }

    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public RoleName getRole() { return role; }
    public Set<String> getSkillsRequired() { return skillsRequired; }


    /* __FACTORY_METHOD__ */

    public static User createNewUser(
            String userName,
            String userEmail,
            RoleName role,
            Set<String> skillsRequired
    ){
        return new User(null, userName, userEmail, role, skillsRequired);
    }

    /* __UPDATE_USER__ */

    public void updateUser(String userName, String userEmail, RoleName role, Set<String> skillsRequired){
        this.userName = userName;
        this.userEmail = userEmail;
        this.role = role;
        this.skillsRequired = skillsRequired;
    }

}
