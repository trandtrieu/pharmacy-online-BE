package com.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.stream.events.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedback_id;
    
    @Column(name = "rating")
    private int rating;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "created_at")
    private LocalDateTime created_at;
    
    @Column(name = "like_count")
    private int like_count;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account user;
    
    @OneToMany(mappedBy = "feedback")
    private Set<Comment> comments;
    
}

