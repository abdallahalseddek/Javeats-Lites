
package com.javaeat.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {


    @Column(name = "creation_time",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;
    @Column(name = "last_updated_time",nullable = false,updatable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;

    @Column(name ="created_by")
    @CreatedBy
    private String createdBy;

    @Column(name ="updated_by")
    private  String updatedBy;


}
