package com.fly4j.yshop.sms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.common.core.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class SmsCoupon extends BaseEntity {
  @TableId(type = IdType.ID_WORKER)
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  @ApiModelProperty(value="优惠券id",hidden=true)
  private Long id;

  @ApiModelProperty(value="优惠券名称",example="优惠券")
  private String name;

  @ApiModelProperty(value="满减条件",example="满100减10")
  private String condition_desc;

  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value="卡有效开始时间",example="2020-04-14 00:00:00")
  private Date startAt;

  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value="卡失效日期",example="2020-05-14 00:00:00")
  private Date endAt;

  @ApiModelProperty(value="描述信息，优惠券可用时展示",example="描述信息")
  private String description;

  @ApiModelProperty(value="不可用原因，优惠券不可用时展示",example="不可用")
  private String reason;

  @ApiModelProperty(value="折扣券优惠金额，单位分",example="100")
  private BigDecimal value;

  @ApiModelProperty(value="折扣券优惠金额文案",example="折")
  private String value_desc;

  @ApiModelProperty(value="单位文案",example="元")
  private String unit_desc;
}