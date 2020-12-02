package mx.santandertec.sam.v2.zuul.router.filters;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.CharStreams;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ContentReplacerFilter extends ZuulFilter {

	private static final String CONTENT_TYPE = "Content-Type";

	private static final ImmutableSet<String> DEFAULT_WHITELIST = ImmutableSet.of("text/html", "application/javascript",
			"text/css");

	private final String regex;
	private final String replacement;

	@Override
	public String filterType() {
		return POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return containsAllowedHeaders(RequestContext.getCurrentContext());
	}

	private boolean containsAllowedHeaders(RequestContext context) {
		assert context != null;

		for (final Pair<String, String> header : context.getZuulResponseHeaders()) {
			if (CONTENT_TYPE.equalsIgnoreCase(header.first())) {
				for (String allowedMimeType : DEFAULT_WHITELIST) {
					if (header.second().contains(allowedMimeType)) {
						if (log.isDebugEnabled())
							log.debug("Filtering resource {} {}", context.getRequest().getMethod(),
									context.getRequest().getRequestURI());
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext context = RequestContext.getCurrentContext();

		if (log.isDebugEnabled()) {
			context.getZuulResponseHeaders().forEach(pair -> {
				log.debug("Header {}: {}", pair.first(), pair.second());
			});
			log.debug("===");
		}

		try (final InputStream responseDataStream = context.getResponseDataStream()) {

			if (responseDataStream == null) {
				if (log.isTraceEnabled())
					log.trace("Response Body is null");
				return null;
			}

			String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
			responseData = responseData.replaceAll(regex, replacement);

			if (log.isTraceEnabled())
				log.trace("Response Body is: {}", responseData);

			context.setResponseBody(responseData);
		} catch (Exception e) {
			throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}

		return null;
	}
}