package com.dm.blog.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {


    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    private Long parentId;

    /**
     * 子节点
     */
    private List<MenuTreeVo> children;
}
