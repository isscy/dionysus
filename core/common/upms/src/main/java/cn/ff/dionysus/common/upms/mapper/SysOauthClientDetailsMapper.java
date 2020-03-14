package cn.ff.dionysus.common.upms.mapper;

import cn.ff.dionysus.common.basal.entity.SysOauthClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOauthClientDetailsMapper {
    SysOauthClientDetails  getByClientId(String clientId);
}
