package ir.company.app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.company.app.domain.AbstractAuditingEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Column(name = "password_hash", length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100, unique = true)
    private String guestId;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date", nullable = true)
    private ZonedDateTime resetDate = null;

    @Column(name = "rating", nullable = true)
    private int rating = 0;

    @Column(name = "mobile", nullable = true)
    private String mobile;


    @Column(name = "avatar", nullable = true)
    private String avatar;


    @Size(max = 50)
    @Column(name = "push_session_key", length = 50)

    private String pushSessionKey;

    @Column(name = "gender", nullable = true)

    private Boolean gender;

    @Column(name = "guest", nullable = true)

    private Boolean guest;

    private ZonedDateTime lastRoulette;
    private ZonedDateTime lastVideo;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Avatar> avatars;

    @ManyToOne
    private User invitedUser2;
    @ManyToOne
    private User invitedUser3;
    @ManyToOne
    private User invitedUser1;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPushSessionKey() {
        return pushSessionKey;
    }

    public void setPushSessionKey(String pushSessionKey) {
        this.pushSessionKey = pushSessionKey;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Boolean getGuest() {
        return guest;
    }

    public void setGuest(Boolean guest) {
        this.guest = guest;
    }

    public ZonedDateTime getLastRoulette() {
        return lastRoulette;
    }

    public void setLastRoulette(ZonedDateTime lastRoulette) {
        this.lastRoulette = lastRoulette;
    }

    public ZonedDateTime getLastVideo() {
        return lastVideo;
    }

    public void setLastVideo(ZonedDateTime lastVideo) {
        this.lastVideo = lastVideo;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }

    public User getInvitedUser2() {
        return invitedUser2;
    }

    public void setInvitedUser2(User invitedUser2) {
        this.invitedUser2 = invitedUser2;
    }

    public User getInvitedUser3() {
        return invitedUser3;
    }

    public void setInvitedUser3(User invitedUser3) {
        this.invitedUser3 = invitedUser3;
    }

    public User getInvitedUser1() {
        return invitedUser1;
    }

    public void setInvitedUser1(User invitedUser1) {
        this.invitedUser1 = invitedUser1;
    }
}
