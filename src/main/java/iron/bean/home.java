package iron.bean;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String link;
    private String img;
}
