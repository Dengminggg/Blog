package com.dm.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 热文vo对象，通常一个接口对应一个vo对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    //唯一id
    @TableId
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
