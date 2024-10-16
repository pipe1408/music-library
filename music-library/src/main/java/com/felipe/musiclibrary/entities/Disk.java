package com.felipe.musiclibrary.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "DISKS")
public class Disk {
    @Id
    @Lob
    @Column(name = "ID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private com.felipe.musiclibrary.entities.Group group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ARTIST_ID", nullable = false)
    private Artist artist;

    @Column(name = "PUBLISHED_YEAR", nullable = false)
    private Integer publishedYear;

    @ColumnDefault("0")
    @Column(name = "SIZE", nullable = false)
    private Integer size;

}