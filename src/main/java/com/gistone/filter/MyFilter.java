package com.gistone.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 使用注解标注过滤器
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
 * 属性filterName声明过滤器的名称,可选
 * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
@WebFilter(filterName="myFilter",urlPatterns="/*")
public class MyFilter implements Filter {
	
	@Override
	public void destroy() {
        //System.out.println("过滤器销毁");
    }
	
	

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    	HttpServletRequest myrequest = (HttpServletRequest)request;
		HttpServletResponse myresponse = (HttpServletResponse)response;
		HttpSession session = myrequest.getSession();
		
        String url = myrequest.getServletPath();
//        String currentURL = hrequest.getRequestURI();
//        System.out.println(currentURL);
        boolean tongguo = false;
        Map special  = new HashMap();
        special.put("getLogin_massage.do", "getLogin_massage.do");//session获取用户登陆信息
        special.put("img.do", "img.do");//验证码
        special.put("loginin.do", "loginin.do");//登录验证
        
        special.put("anuser.html", "anuser.html");//用户扫码查看详细信息
        special.put("anGetPk.do", "anGetPk.do");//用户扫码查看详细信息

        if(url.indexOf("/index.html") == -1){
        	if(url.indexOf(".do") != -1||url.indexOf(".html") != -1){
        		if(session == null || session.getAttribute("Login_map")==null){
        			for (Object key : special.keySet()) {//循环所有的特例
            			if(url.indexOf(special.get(key).toString()) != -1){
            				tongguo = true;
            			}
            		}
            		if(!tongguo){
            			System.out.println(url);
            			PrintWriter out = response.getWriter();
        				StringBuilder builder = new StringBuilder();
        				builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
        				builder.append("window.top.location.href=\"");
        				String path = myrequest.getContextPath();
        				builder.append(path);
        				builder.append("/index.html\";</script>");
        				response.setContentType("text/html;charset=utf-8");
        				out.print(builder.toString());
        				out.close();// 普通 jsp页面session过期处理
            		}else{
            			chain.doFilter(request, response);
            		}
        		}else{
        			chain.doFilter(request, response);
        		}
        	}else{
        		chain.doFilter(request, response);
        	}
        }else{
        	chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        //System.out.println("过滤器初始化");
    }
    
}
