package com.haraji.business.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.joda.time.DateTime;


@Immutable
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class SaleView {

    private Long idOff;

    private Long id_business;

    private String business_title_per;

    private String business_city;

    private String Address;

    private String business_tel;

    private String business_type;

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

    @Override
    public String toString() {
        return "SaleView{" +
                "idOff=" + idOff +
                ", id_business=" + id_business +
                ", business_title_per='" + business_title_per + '\'' +
                ", business_city='" + business_city + '\'' +
                ", Address='" + Address + '\'' +
                ", business_tel='" + business_tel + '\'' +
                ", business_type='" + business_type + '\'' +
                ", last_name='" + last_name + '\'' +
                ", create_Date=" + create_Date +
                ", start_Date=" + start_Date +
                ", end_Date=" + end_Date +
                ", duration=" + duration +
                ", time_unit=" + time_unit +
                ", working_hour_from=" + working_hour_from +
                ", working_hour_to=" + working_hour_to +
                ", percentage_from=" + percentage_from +
                ", percentage_to=" + percentage_to +
                ", highest_price=" + highest_price +
                ", lowest_price=" + lowest_price +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
