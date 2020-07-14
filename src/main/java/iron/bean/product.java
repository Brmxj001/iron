package iron.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String namee;

    private String introduction;
    private String introductione;

    private String simple;
    private String simplee;

    private String prize;
    private String prizee;

    private String model;
    private String modele;

    private Integer cid;

    private String cover;

    private Integer callon;

    @Transient
    private List<img> imgs;

    @Transient
    private List<contact> contacts;

}
