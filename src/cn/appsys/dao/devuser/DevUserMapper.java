package cn.appsys.dao.devuser;

import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DevUserMapper {
    /**
     * 通过userCode获取User
     * @param devCode
     * @return
     * @throws Exception
     */
    public DevUser getLoginUser(@Param("devCode") String devCode);

    /**
     * 申请添加用户
     * @param devUser
     * @return
     */
    public int add(DevUser devUser)throws Exception;

    /**
     * 获取用户总数
     * @return
     * @throws Exception
     */
    public int getDevUserCount()throws Exception;

    /**
     * 获取用户列表
     * @return
     * @throws Exception
     */
    public List<DevUser> getDevUserList(@Param(value = "devCode") String devCode,
                                        @Param(value = "devName") String devName,
                                        @Param(value = "devInfo") String devInfo,
                                        @Param(value = "creationDate") Date creationDate,
                                        @Param(value="from")Integer currentPageNo,
                                        @Param(value="pageSize")Integer pageSize)throws Exception;

    public int update(@Param("devCode") String devCode)throws Exception;

    public int delete(@Param("devCode") String devCode)throws Exception;

    public int modify(DevUser devUser)throws Exception;
}
