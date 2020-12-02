package mx.santandertec.sam.v2.zuul.router.filters;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SessionSavingZuulPreFilter extends ZuulFilter {

	// @Autowired
	// private SessionRepository<Session> repository;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();

		HttpSession session = context.getRequest().getSession();

		// Session session = repository..getSession(httpSession.getId());

		// context.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
		context.addZuulRequestHeader("My-HEADER", "LOL");
		context.addZuulRequestHeader("ivGroup", (String) session.getAttribute("ivGroup"));
		context.addZuulRequestHeader("ivUser", (String) session.getAttribute("ivUser"));

		log.info("headers: ");
		log.info("{}", context.getZuulRequestHeaders());

		// log.info("ZuulPreFilter session proxy: {}", session.getId());

		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

}