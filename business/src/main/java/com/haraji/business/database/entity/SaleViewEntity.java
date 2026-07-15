package com.haraji.business.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.joda.time.DateTime;


@Entity
@Immutable
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class SaleViewEntity {

    @Id
    @Column()
    private long saleId;

    private int businessId;

    private String business_title_per;

    private String business_city;

    private String Address;

    private String business_tel;

    private String businessTypeId;

    private String last_name;

    private DateTime create_Date;

    private DateTime start_Date;

    private DateTime end_Date;

    private int duration;

    private int time_unit;

    private int working_hour_from;

    private int working_hour_to;

    private int percentage_from;

    private int percentage_to;

    private int highest_price;

    private int lowest_price;

    private String description;

    private boolean active;

    private Boolean isEdit;

}
