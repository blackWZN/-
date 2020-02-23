//package com.wzn.ablog.admin.DynamicAuthority;
//
//import com.wzn.ablog.admin.dao.PortDao;
//import com.wzn.ablog.admin.dao.RoleDao;
//import com.wzn.ablog.admin.entity.Port;
//import com.wzn.ablog.admin.entity.Role;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
//@Service
//public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
//
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//
//    @Autowired
//    private RoleDao roleDao;
//
//    @Autowired
//    private PortDao portDao;
//
//    private HashMap<String, Collection<ConfigAttribute>> map = null;
//
////    private HashMap<String, Collection<Role>> map = null;
//    /**
//     * 加载权限表中所有权限
//     */
//    public void loadResourceDefine() {
//        map = new HashMap<>();
//        Collection<ConfigAttribute> array;
//        ConfigAttribute cfg = null;
//        // 查询出所有的请求URL
//        List<Port> ports = portDao.findAll();
//
//        for (Port port : ports) {
//            array = new ArrayList<>();
//            List<Role> roles = roleDao.findRolesByPortId(port.getId());
//            for (Role role : roles) {
//                cfg = new SecurityConfig(role.getRole_name());
//            }
//            /**
//             *此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
//             *此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
//             */
//            array.add(cfg);
//            //map放入URL对应的角色
//            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
//            map.put(port.getPort_url(), array);
//        }
//
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    /**
//     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
//     * 用来判定用户是否有此权限。如果不在权限表中则放行。
//     */
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        if (map == null) loadResourceDefine();
//        //object 中包含用户请求的request 信息
//        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
//        logger.debug("请求url:"+String.valueOf(request.getRequestURI()));
//        AntPathRequestMatcher matcher;
//        String resUrl;
//        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
//            resUrl = iter.next();
//            matcher = new AntPathRequestMatcher(resUrl);
////            if (matcher.matches(request)) {
////                return map.get(resUrl);
////            }
//            if(resUrl.equals(request.getRequestURI())){
//                logger.debug("权限中的url"+resUrl);
//                logger.debug("返回的权限："+String.valueOf(map.get(resUrl)));
//                return map.get(resUrl);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> class1) {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//}