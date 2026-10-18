package lk.job_finder_app.smart_job_aggregator.domain.models;

import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;

public class Role {
    private Long roleId;
    private RoleName roleName;
    private int limitPerMinute;

    public Role(Long roleId, RoleName roleName, int limitPerMinute) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.limitPerMinute = limitPerMinute;
    }

    public Long getRoleId() {
        return roleId;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public int getLimitPerMinute() {
        return limitPerMinute;
    }
}
