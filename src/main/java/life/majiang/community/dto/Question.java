package life.majiang.community.dto;

import lombok.Data;

import java.awt.print.PrinterAbortException;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-13 22:04
 * @description
 */
@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tags;
}
