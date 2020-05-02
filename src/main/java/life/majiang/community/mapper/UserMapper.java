package life.majiang.community.mapper;

import life.majiang.community.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-10 9:26
 * @description
 */
@Component(value = "userMapper")
public interface UserMapper {
    @Insert("insert into USER (ACCOUNT_ID,NAME,EMAIL,BIO,GMT_CREATE,GMT_MODIFIED,AVATAR_URL) values (#{accountId}, #{name},#{email},#{bio},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from USER where TOKEN=#{token} and GMT_TOKEN>#{currentTime}")
    User findByToken(String token,Long currentTime);

    @Select("select * from USER where ACCOUNT_ID=#{accountId}")
    User findByAccountId(String accountId);

    @Update("update USER set ACCOUNT_ID=#{accountId},NAME=#{name},EMAIL=#{email},BIO=#{bio}, TOKEN=#{token}, GMT_MODIFIED=#{gmtModified}, GMT_TOKEN=#{gmtToken} , AVATAR_URL=#{avatarUrl} where id=#{id}")
    void updateUserLoginInfo(User user);

    @Select("select * from USER where ID=#{creator}")
    User findById(int creator);
}
