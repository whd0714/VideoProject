package videoproject.video.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private LocalDateTime joinAt;
    private boolean isAdmin;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinAt = LocalDateTime.now();
    }
}
