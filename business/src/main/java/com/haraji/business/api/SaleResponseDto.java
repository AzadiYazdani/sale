package com.haraji.business.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.joda.time.DateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ اطلاعات حراجی")
public class SaleResponseDto {

    @Schema(description = "شناسه حراجی", example = "1")
    private long id;

    @Schema(description = "شناسه کسب و کار", example = "1")
    private int id_business;

    @Schema(description = "نام فارسی کسب و کار", example = "\"فروشگاه لوازم خانگی خانه سبز\"")
    private String business_title_per;

    @Schema(description = "شهر محل کسب و کار", example = "\"تهران\"")
    private String business_city;

    @Schema(description = "نشانی محل کسب و کار و حراجی", example = "\"چهارراه سیروس- کوچه قنادها- پلاک 82\"")
    private String address;

    @Schema(description = "تلفن کسب و کار", example = "\"02166341231\"")
    private String business_tel;

    @Schema(description = "نوع کسب و کار", example = "\"لوازم خانگی\"")
    private String business_type;

    @Schema(description = "نام فرد پاسخگو به تلفن کسب و کار", example = "\"جوادی\"")
    private String last_name;

    @Schema(description = "تاریخ ثبت حراجی", example = "\"2023-10-25T10:00:00.000Z\"")
    private DateTime create_Date;

    @Schema(description = "تاریخ آغاز حراجی", example = "\"2023-10-26T08:00:00.000Z\"")
    private DateTime start_Date;

    @Schema(description = "تاریخ پایان حراجی", example = "\"2023-11-26T22:00:00.000Z\"")
    private DateTime end_Date;

    @Schema(description = "طول حراجی", example = "5")
    private int duration;

    @Schema(description = "واحد زمانی طول حراجی (مثلا 1 برای روز، 2 برای هفته)", example = "2")
    private int time_unit;

    @Schema(description = "ساعت آغاز حراجی", example = "9")
    private int working_hour_from;

    @Schema(description = "ساعت پایان حراجی", example = "21")
    private int working_hour_to;

    @Schema(description = "کمترین درصد حراجی", example = "15")
    private int percentage_from;

    @Schema(description = "بیشترین درصد حراجی", example = "50")
    private int percentage_to;

    @Schema(description = "بیشترین قیمت", example = "200000")
    private int highest_price;

    @Schema(description = "کمترین قیمت", example = "50000")
    private int lowest_price;

    @Schema(description = "توضیحات حراجی", example = "\"در این حراجی امکان پرداخت قسطی هم وجود دارد\"")
    private String description;

    @Schema(description = "حراجی فعال است", example = "true")
    private boolean active;

    @Override
    public String toString() {
        return "SaleResponseDto{" +
                "id=" + id +
                ", id_business=" + id_business +
                ", business_title_per='" + business_title_per + '\'' +
                ", business_city='" + business_city + '\'' +
                ", address='" + address + '\'' +
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
