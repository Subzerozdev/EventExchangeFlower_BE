package com.eventflowerexchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // này Khánh comment cho nó nhiều code thôi chứ không có gì đâu.
    @Column(name = "name", nullable = false)
    private String name;

    // cái này là Khánh mới code
    // Khánh thêm comment vào cho nó dài dòng văn tự thôi .
    @ManyToMany(mappedBy = "types", fetch = FetchType.LAZY)
    private  List<Post> posts;
}
