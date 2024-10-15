package com.felipe.music_library.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "GROUPS")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "SHORT_NAME", nullable = false)
    private String shortName;

    @Lob
    @Column(name = "NAME", nullable = false)
    private String name;

    @ColumnDefault("0")
    @Column(name = "SIZE", nullable = false)
    private Integer size;

}