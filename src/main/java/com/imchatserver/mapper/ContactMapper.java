package com.imchatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imchatserver.entity.Contact;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContactMapper extends BaseMapper<Contact> {
}
