package cn.appsys.service.developer;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class DevUserServiceImpl implements DevUserService {

    @Resource
    private DevUserMapper mapper;
    @Override
    public DevUser login(String devCode, String password) {
        DevUser user = null;
        user = mapper.getLoginUser(devCode);
        if(null != user){
            if(!((DevUser) user).getDevPassword().equals(password)){
                user = null;
            }
        }
        return user;
    }

    @Override
    public boolean add(DevUser devUser) throws Exception {
        boolean flag = false;
        int i = mapper.add(devUser);
        if(i > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public int getDevUserCount() throws Exception {
        return mapper.getDevUserCount();
    }

    @Override
    public List<DevUser> getDevUserList(String devCode, String devName, String devInfo, Date creationDate, Integer currentPageNo, Integer pageSize) throws Exception {
        return mapper.getDevUserList(devCode,devName,devInfo,creationDate,(currentPageNo-1)*pageSize,pageSize);
    }

    @Override
    public boolean update(String devCode) throws Exception {
        boolean flag = false;
        int i = mapper.update(devCode);
        if(i>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean delete(String devCode) throws Exception {
        boolean flag = false;
        int i = mapper.delete(devCode);
        if(i>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modify(DevUser devUser) throws Exception {
        boolean flag = false;
        int i = mapper.modify(devUser);
        if(i > 0){
            flag = true;
        }
        return flag;
    }


}
