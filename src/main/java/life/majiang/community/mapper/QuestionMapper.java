package life.majiang.community.mapper;

import life.majiang.community.dto.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-13 21:58
 * @description
 */
@Component(value = "questionMapper")
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tags) values(#{title},#{description}," +
            "#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);
}
