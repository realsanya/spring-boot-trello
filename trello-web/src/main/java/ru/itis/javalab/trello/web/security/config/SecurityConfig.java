package ru.itis.javalab.trello.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.javalab.trello.web.security.jwt.filter.JwtFilter;
import ru.itis.javalab.trello.web.security.oauth.CustomOAuth2UserService;
import ru.itis.javalab.trello.web.security.oauth.OAuth2LoginSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ComponentScan("ru.itis.javalab.trello.web")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Order(2)
    @Configuration
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        @Qualifier("customUserDetailService")
        private UserDetailsService userDetailsService;
        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.oauth2Client();
            http.authorizeRequests()
                    .antMatchers("/oauth2/**").permitAll()
                    .antMatchers("/signIn").permitAll()
                    .antMatchers("/signUp").permitAll()
                    .antMatchers("/home").permitAll()
//                    .antMatchers("/home").authenticated()
                    .antMatchers("/support").permitAll()

                    .and()
                    .formLogin()
                        .loginPage("/signIn")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/main")
                        .failureUrl("/signIn?error")
                    .and()
                    .oauth2Login()
                        .loginPage("/signIn")
                        .userInfoEndpoint().userService(oAuth2UserService)
                        .and()
                        .successHandler(oAuth2LoginSuccessHandler)
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenRepository(persistentTokenRepository());

            http.csrf().disable();
            http.antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/home").authenticated().and()
                    .formLogin().disable()
                    .httpBasic().and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }

        @Autowired
        private CustomOAuth2UserService oAuth2UserService;
        @Autowired
        private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    }

    @Order(1)
    @Configuration
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter{
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        @Qualifier("customUserDetailService")
        private UserDetailsService userDetailsService;
        @Autowired
        private JwtFilter jwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/main").authenticated().and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

    }

//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final JwtAuthenticationProvider jwtAuthenticationProvider;
//
//    @Autowired
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationProvider jwtAuthenticationProvider) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http
//                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/signIn").permitAll()
//                .and()
//                .sessionManagement().disable();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(jwtAuthenticationProvider);
//    }

//    @Order(1)
//    @Configuration
//    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//        @Autowired
//        @Qualifier("customUserDetailService")
//        private UserDetailsService userDetailsService;
//        @Autowired
//        private DataSource dataSource;
//        @Autowired
//        private CustomOAuth2UserService oAuth2UserService;
//
//        @Autowired
//        private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.csrf().disable();
//            http.authorizeRequests()
//                    .antMatchers("/signIn").permitAll()
//                    .antMatchers("/signUp").permitAll()
//                    .antMatchers("/profile").authenticated()
//                    .antMatchers("/signIn/oauth2/**").permitAll()
//                    .anyRequest().permitAll()
//
//                    .and()
//                    .formLogin()
//                    .loginPage("/signIn")
//                    .usernameParameter("email")
////                    .passwordParameter("password")
//                    .defaultSuccessUrl("/profile")
//                    .failureUrl("/signIn?error")
//                    .and()
//                    .oauth2Login()
//                    .loginPage("/signIn")
//                    .userInfoEndpoint().userService(oAuth2UserService)
//                    .and()
//                    .successHandler(oAuth2LoginSuccessHandler)
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID")
//                    .and()
//                    .rememberMe()
//                    .rememberMeParameter("remember-me")
//                    .tokenRepository(persistentTokenRepository());
//
////            http.csrf().disable();
////            http.antMatcher("/**")
////                    .authorizeRequests()
////                    .antMatchers("/profile").authenticated().and()
////                    .formLogin().disable()
////                    .httpBasic().and()
////                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        }
//
////        @Bean
////        public CorsConfigurationSource corsConfigurationSource() {
////            final CorsConfiguration configuration = new CorsConfiguration();
////            configuration.setAllowedOrigins(Arrays.asList("*"));
////
////            List<String> methodsAllowed = new ArrayList<>();
////            methodsAllowed.add("HEAD");
////            methodsAllowed.add("GET");
////            methodsAllowed.add("POST");
////            methodsAllowed.add("PUT");
////            methodsAllowed.add("DELETE");
////            methodsAllowed.add("PATCH");
////            configuration.setAllowedMethods(methodsAllowed);
////
////            List<String> headersAllowed = new ArrayList<>();
////            headersAllowed.add("*");
////            configuration.setAllowedHeaders(headersAllowed);
////
////            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////            source.registerCorsConfiguration("/**", configuration);
////            return source;
////        }
//
//        @Bean
//        public PersistentTokenRepository persistentTokenRepository() {
//            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//            jdbcTokenRepository.setDataSource(dataSource);
//            return jdbcTokenRepository;
//        }
//    }
//
//    @Order(2)
//    @Configuration
//    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Autowired
//        private PasswordEncoder passwordEncoder;
//        @Autowired
//        @Qualifier("customUserDetailService")
//        private UserDetailsService userDetailsService;
//
//        @Autowired
//        private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//        @Autowired
//        private JwtAuthenticationProvider jwtAuthenticationProvider;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.csrf().disable();
//            http.antMatcher("/**")
//                    .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                    .authorizeRequests()
//                    .antMatchers("/profile").authenticated().and()
//                    .formLogin().disable()
//                    .httpBasic().disable()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        }
//
////        @Override
////        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
////        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(jwtAuthenticationProvider);
//        }
//    }
}


