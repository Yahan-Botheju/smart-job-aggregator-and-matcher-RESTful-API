package lk.job_finder_app.smart_job_aggregator.domain.models;


public class User {
    private Long userId;
    private String userName;
    private String userEmail;

    private Role role;

    private Set<String> skillsRequired;

    public User(Long userId, String userName, String userEmail, Role role, Set<String> skillsRequired) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.role = role;
        this.skillsRequired = skillsRequired;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Role getRole() {
        return role;
    }

    public Set<String> getSkillsRequired() {
        return skillsRequired;
    }
}
