package com.imchatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imchatserver.entity.Messages;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
}
