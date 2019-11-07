package cn.appsys.service.developer;

import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DevUserService {
    /**
     * 登陆验证
     * @param devCode
     * @param password
     * @return
     */
    public DevUser login(String devCode,String password);

    /**
     * 用户账户申请
     * @param devUser
     * @return
     */
    public boolean add(DevUser devUser) throws Exception;

    /**
     * 获取用户数量
     * @return
     * @throws Exception
     */
    public int getDevUserCount() throws Exception;

    /**
     * 获取用户列表
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<DevUser> getDevUserList(@Param(value = "devCode") String devCode,
                                        @Param(value = "devName") String devName,
                                        @Param(value = "devInfo") String devInfo,
                                        @Param(value = "creationDate") Date creationDate,
                                        @Param(value = "from") Integer currentPageNo,
                                        @Param(value = "pageSize") Integer pageSize
                                        )throws Exception;

    public boolean update(@Param(value = "devCode") String devCode)throws Exception;

    public boolean delete(@Param(value = "devCode") String devCode)throws Exception;

    public boolean modify(DevUser devUser)throws Exception;
}
