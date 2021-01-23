package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential>getCredentialsList(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(int credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) VALUES(#{url}, #{username}, #{password}, #{key}, #{userId})")
    int insertCredential(Credential credential);


    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password}, key=#{key}, userid = #{userId} WHERE credentialid = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(int credentialId);


}
