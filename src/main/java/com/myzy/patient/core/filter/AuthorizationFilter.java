package com.myzy.patient.core.filter;

import com.myzy.patient.system.service.SysResourceService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权过滤器（权限校验）
 *
 * @author leekejin
 */
public class AuthorizationFilter extends OncePerRequestFilter {

    @Resource
    private SysResourceService sysResourceService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 公共权限的直接放行
        Object permit = request.getAttribute("permit");
        if (permit != null && (boolean) permit) {
            filterChain.doFilter(request, response);
            return;
        }
        // 校验权限,失败返回403
        boolean pass = sysResourceService.checkAuthorization(request);
        if (pass) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "没有访问权限");
        }
    }

}
