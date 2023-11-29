package com.example.securingweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity                      // This annotation helps to enable Spring (basic) authentication
public class WebSecurityConfig {
    /**
     * @param http
     * @return
     * @throws Exception
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * @note This method, SecurityFilterChain, allows us to create our definitions
         * of which paths are allowed to all user traffic, and which ones are not
         */
        http
                .authorizeHttpRequests((requests) -> requests
//                        This is where every user can have access to.
//                        In networking, it is like a default static route (0.0.0.0) where
//                        everyone can gain entry despite having no priveleges.
                        .requestMatchers("/", "/home").permitAll()

//                        Here you can see that at this /home everyone is granted to be authenticated
                        .anyRequest().authenticated()
                )

//                Redirect traffic here when users go to this route
//                Here, this Bean is using the form processing
                .formLogin((form) -> form
                        /**
                         * @note Look at the templates layer
                         * login.html
                         */
                        .loginPage("/login")            // Here is where most of your view (login) form is rendered
                        .permitAll()                    // Here you try to authenticate all user traffic
                        /**
                         * @note the permitAll() method allows access to the specified URLs without authentication
                         */

                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("Logging in right now....");
        UserDetails user = User.withUsername("username")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        System.out.println("Username provided: " + user.getUsername());
        System.out.println("Password provided: " + user.getPassword());
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        /**
         * @note all user credentials such as passwords
         * should be encrypted and stored in plain-text while in memory
         * in the browser
         */
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }









    /**
     * @note
     * @Configuration annotation is used to indicate that a class declares one or more @Bean methods
     * and may be processed by the Spring container to generate bean definitions and service
     *
     * @note This helps setup Spring configuration of Spring Security. It is where most
     * of the user credentials and authentication is setup.
     * @note There should be no unauthorized access to /hello
     *
     * @note Remember the form processing:
     * - This usually consist of validating user credentials
     *
     *
     *
     * @note Access to "/" and "/home" is permitted for all users (permitAll()).
     * Authentication is required for all other URLs.
     * The login page ("/login") is accessible to all users (permitAll()).
     * Logging out is permitted for all users (permitAll()).
     */
}