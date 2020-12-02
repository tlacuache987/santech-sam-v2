package mx.santandertec.sam.v2.zuul.router.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.santandertec.sam.v2.zuul.router.filters.ContentReplacerFilter;
import mx.santandertec.sam.v2.zuul.router.filters.HeaderUrlRewritingFilter;

@Configuration
public class FiltersConfig {

	@Bean
	public HeaderUrlRewritingFilter headerUrlRewritingFilter() {
		//return new HeaderUrlRewritingFilter(":8081|:8082|:8083|:8088|:8089", ":8080");
		return new HeaderUrlRewritingFilter("transfers-host1:8088|transfers-host2:8089", "samio:8080");
	}

	@Bean
	public ContentReplacerFilter contentRewritingFilter() {
		//return new ContentReplacerFilter(":8081|:8082|:8083|:8088|:8089", ":8080");
		return new ContentReplacerFilter("transfers-host1:8088|transfers-host2:8089", "samio:8080");
	}
}
