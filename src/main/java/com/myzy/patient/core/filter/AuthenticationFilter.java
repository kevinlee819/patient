package com.myzy.patient.core.filter;

import com.myzy.patient.utils.JwtTokenUtil;
import com.myzy.patient.core.Constant;
import com.myzy.patient.core.UserContext;
import com.myzy.patient.core.entity.TokenEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 认证过滤器（登录校验）
 *
 * @author leekejin
 */
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String OPTIONS = "OPTIONS";

    /**
     * 过滤不需要登录也能访问的资源
     *
     * @param request 请求
     * @return true允许访问，false需要登录
     */
    private boolean permit(HttpServletRequest request) {
        // 跨域的时候会有OPTIONS请求，直接放行
        if (OPTIONS.equalsIgnoreCase(request.getMethod())){
            return true;
        }

        // druid
        Stream<String> druid = Stream.of("/druid/**");
        // swagger
        Stream<String> swagger = Stream.of("/swagger-resources/**", "/swagger-ui.html", "/v2/**", "/webjars/**");
        // 不需要登录可以访问的公共资源
        Stream<String> common = Stream.of("/sysUser/login", "/sysFile/upload", "/sysFile/downTemplate", "/file/**");
        // 校验请求request.getRequestURI()是否匹配
        PathMatcher pathMatcher = new AntPathMatcher();
        return Stream.of(druid, swagger, common).flatMap(item -> item)
                // request.getRequestURI() 匹配到公共资源返回true
                .anyMatch(allow -> pathMatcher.match(allow, request.getRequestURI()));
    }

    /**
     * 二次认证
     * @param request
     * @return
     */
    private boolean secondPermit(HttpServletRequest request) {
        if (permit(request)){
           return true;
        } else {
            // 普通用户可访问的资源
            Stream<String> sys = Stream.of("/sysUser/currentUser","/sysUser/refreshToken","/sysUser/password",
                    "/sysDictionary/**","/sysRegion/**");
            Stream<String> patientManage = Stream.of("/patientInfo/**");
            // 校验请求request.getRequestURI()是否匹配
            Stream<String> modifyInfo = Stream.of("/patientInfo/");
            PathMatcher pathMatcher = new AntPathMatcher();
            if(Stream.of(modifyInfo).flatMap(item -> item).anyMatch(allow -> pathMatcher.match(allow, request.getRequestURI())) && "PUT".equals(request.getMethod())){
                return false;
            }
            return Stream.of(sys,patientManage).flatMap(item -> item)
                    // request.getRequestURI() 匹配到公共资源返回true
                    .anyMatch(allow -> pathMatcher.match(allow, request.getRequestURI()));
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从header中获取jwtToken
        try {
            String token = request.getHeader(Constant.HEADER_TOKEN_NAME);
            if (StringUtils.isNotBlank(token)) {
                TokenEntity tokenEntity = JwtTokenUtil.validation(token);
                // 使用上下文管理用户信息
                if (tokenEntity != null && tokenEntity.getUserId() != null) {
                    if (tokenEntity.getCreateUserName() == null || tokenEntity.getCreateUserName().equals(Constant.ADMIN) || secondPermit(request)){
                        // 登录账号的放行
                        try {
                            UserContext.setUser(tokenEntity);
                            filterChain.doFilter(request, response);
                        } finally {
                            UserContext.removeUser();
                        }
                        return;
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权限");
                        return;
                    }

                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            e.printStackTrace();
            return;
        }
        // 不需要登录也可以访问的放行
        if (permit(request)) {
            request.setAttribute("permit", true);
            filterChain.doFilter(request, response);
            return;
        }
        // 不登录访问非公共资源
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "用户未登陆");
    }

}
