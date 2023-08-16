package com.example.crud_mysql_jwt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "entertainments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Entertainment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "review")
    private String review;
    @Column(name = "score")
    private byte score;
    @Column(name = "watch_again")
    private boolean watch_again;
    @Column(name = "duration")
    private int duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
