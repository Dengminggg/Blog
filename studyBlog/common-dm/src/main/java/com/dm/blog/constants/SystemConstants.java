package com.dm.blog.constants;


/**
 * 此处放置系统所需要用到的字面值常量
 * 实际项目中都不允许直接在代码中使用字面值。都需要定义成常量来使用。这种方式有利于提高代码的可维护性
 */
public class SystemConstants {

    //文章为草稿状态
    public static final int ARTICLE_STATUS_DRAFT = 1;
    //文章为正常发布状态
    public static final int ARTICLE_STATUS_NORMAL = 0;

    //文章分类为正常状态
    public static final String CATEGORY_STATUS_NORMAL = "0";
    //文章分类为异常状态
    public static final String CATEGORY_STATUS_ABNORMAL = "1";

    //友联为审核通过
    public static final String LINK_STATUS_PASS = "0";
    //友联为审核未通过
    public static final String LINK_STATUS_FAIL = "1";
    //友联为待查看
    public static final String LINK_STATUS_WAITING = "2";

    //rootId为根评论
    public static final Long ROOT_ID = -1L;

    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";

    public static final String Article_View_Count = "Article_View_Count";

    public static final String CATALOG = "M";
    public static final String MENU = "C";
    public static final String BUTTON = "F";

    public static final String STATUS_NORMAL = "0";

    public static final String CATEGORY_NAME = "博客分类.xlsx";

    public static final String ADMAIN = "1";
}
