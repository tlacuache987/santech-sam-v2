package mx.santandertec.sam.v2.zuul.router.filters;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.filter;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

import java.util.Collection;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public final class HeaderUrlRewritingFilter extends ZuulFilter {

	private static final ImmutableSet<String> DEFAULT_WHITELIST = ImmutableSet.of("Link", "Location");

	private final String regex;
	private final String replacement;
	private final ImmutableSet<String> whitelist;

	public HeaderUrlRewritingFilter(final String regex, final String replacement) {
		this(regex, replacement, DEFAULT_WHITELIST);
	}
	
	@Override
	public String filterType() {
		return POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return 100;
	}

	@Override
	public boolean shouldFilter() {
		return containsHeaders(RequestContext.getCurrentContext());
	}

	private static boolean containsHeaders(final RequestContext context) {
		assert context != null;
		return !context.getZuulResponseHeaders().isEmpty();
	}

	@Override
	public Object run() {
		try {
			rewriteHeaders(RequestContext.getCurrentContext(), this.whitelist, this.regex, this.replacement);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private static void rewriteHeaders(final RequestContext context, final Collection<String> whitelist,
			final String regex, final String replacement) {
		assert context != null;
		for (final Pair<String, String> header : context.getZuulResponseHeaders()) {
			if (caseInsensitiveContains(whitelist, header.first())) {
				// Don't use context.getResponse() as it will produce dual headers
				// these headers are added to the HttpServletResponse in
				// SendResponseFilter.java in Zuul
				header.setSecond(header.second().replaceAll(regex, replacement));
				log.debug("Rewrote header: " + header.first() + " to " + header.second());
			}
		}
	}

	private static boolean caseInsensitiveContains(final Collection<String> collection, final String value) {
		return !filter(collection, new CaseInsensitiveEqualityPredicate(value)).isEmpty();
	}

	private static class CaseInsensitiveEqualityPredicate implements Predicate<String> {

		private final String referenceValue;

		public CaseInsensitiveEqualityPredicate(final String referenceValue) {
			this.referenceValue = checkNotNull(referenceValue);
		}

		@Override
		public boolean apply(final String input) {
			return this.referenceValue.equalsIgnoreCase(input);
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj instanceof CaseInsensitiveEqualityPredicate) {
				final CaseInsensitiveEqualityPredicate that = (CaseInsensitiveEqualityPredicate) obj;
				return Objects.equal(this.referenceValue, that.referenceValue);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(this.referenceValue);
		}
	}
}