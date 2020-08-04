package iron.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String password;
    private String user;

}
