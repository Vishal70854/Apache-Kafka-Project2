package net.javaguides.springboot.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wikimedia_recentchange")
@Getter
@Setter
public class WikimediaData {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

    @Column(length = 512) // since the column length is very large. we have increased the columnlength
    @Lob // this annotation is used to store large data(since wikimedia data is very large and also it is updated time to time)
   private String wikiEventData;

}
