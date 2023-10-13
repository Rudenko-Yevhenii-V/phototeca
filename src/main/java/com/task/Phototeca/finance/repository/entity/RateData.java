package com.task.phototeca.finance.repository.entity;

import com.task.phototeca.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "rate_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@SuperBuilder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public class RateData extends BaseEntity {

    @Column(name = "json_rate_data")
    String jsonRateData;

}
