package com.emu.apps.qcm.services.jpa.entity;

import com.emu.apps.qcm.services.jpa.entity.common.AuditableEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


@Entity
public class UserProfile extends AuditableEntity<String> implements UserDetails {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PRINCIPAL_ID")
    private String principalId;

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "LAST_LOGIN")
    private Date lastLogin;

    @Column(name = "OAUTH2_CLIENT")
    //@Enumerated(EnumType.STRING)
    private String oauth2Client;

    @Column(name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired;

    @Column(name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked;

    @Column(name = "CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired;

    @Column(name = "ENABLED")
    private boolean enabled;

    public UserProfile() {
    }

    public UserProfile(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getOauth2Client() {
        return oauth2Client;
    }

    public void setOauth2Client(String oauth2Client) {
        this.oauth2Client = oauth2Client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("NO_ROLE"));
    }
}