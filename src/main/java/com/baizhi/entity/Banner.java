package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Banner implements Serializable {
    private String id;
    private String title;
    private String imgPath;
    private String description;
    private String status;
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
}
