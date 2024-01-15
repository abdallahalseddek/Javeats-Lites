
package com.javaeat.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
