package iron;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import iron.interceptor.IronInterceptor;

@Configuration
public class IronConfig implements WebMvcConfigurer {
	@Bean
	IronInterceptor IronInterceptor() {
		return new IronInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(IronInterceptor());
	}

}
