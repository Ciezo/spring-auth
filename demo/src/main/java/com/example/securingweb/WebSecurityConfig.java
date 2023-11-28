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
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        /**
                         * @note Remember, These are the basic credentials.
                         * Particularly, your typical way of asking user authentication
                         */
                        .username("cloyd")           // typical username credential
                        .password("12345678")       // typical password
                        .roles("USER")             // is there an admin role?
                        .build();

        return new InMemoryUserDetailsManager(user);
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
     */
}