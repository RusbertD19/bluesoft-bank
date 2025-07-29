package bluesoftBank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
					.csrf(csrf -> csrf.disable())
					.cors(cors -> {}) // usa el bean de corsFilter
					.authorizeHttpRequests(auth -> auth
							.anyRequest().permitAll()
					)
					.formLogin(form -> form.disable())
					.httpBasic(httpBasic -> httpBasic.disable())
					.logout(logout -> logout.disable());

			return http.build();
		}

		@Bean
		public CorsFilter corsFilter() {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowCredentials(true); // importante para cookies o autenticación futura
			config.setAllowedOrigins(List.of("http://localhost:4200")); // origen frontend
			config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			config.setAllowedHeaders(List.of("*"));

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config);
			return new CorsFilter(source);
		}
	}

