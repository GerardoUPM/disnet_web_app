package edu.upm.midas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import edu.upm.midas.token.token.JwtAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //@Autowired
    //private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/",
                        "/visualisation/*",
                        "/about",
                        "/results",
                        "/members/about",
                        "/members/alejandro",
                        "/members/eduardo",
                        "/members/ernestina",
                        "/members/gerardo",
                        "/members/lucia",
                        "/members/massimiliano",
                        "/confirmation_email",
                        "/user/request_history",
                        "/apis/tvp",
                        "/apis/disnet",
                        "/apis/disnet/web_service_1",
                        "/apis/disnet/web_service_2",
                        "/apis/disnet/web_service_3",
                        "/apis/disnet/web_service_4",
                        "/apis/disnet/web_service_5",
                        "/apis/disnet/web_service_6",
                        "/apis/disnet/web_service_7",
                        "/apis/disnet/web_service_8",
                        "/apis/disnet/web_service_9",
                        "/apis/disnet/web_service_10",
                        "/apis/disnet/web_service_11",
                        "/apis/disnet/common_errors",
                        "/apis/disease_album",
                        "/user/admin/home",
                        "/user/client/home",
                        "/terms_and_conditions",
                        "/temporarily_disabled").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers(HttpMethod.POST,"/user/update").permitAll()
                .antMatchers(HttpMethod.POST,"/user/reset_password").permitAll()
                .antMatchers("/user/forgot").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers("/user/confirmation").permitAll()
                .antMatchers(HttpMethod.GET,"/user/confirmation_email_response").permitAll()
                .antMatchers("/user/test").permitAll()
                .antMatchers("/user/client/**").hasAuthority("USER").anyRequest()
                .authenticated().and().csrf().disable()
                .formLogin()
//                .loginProcessingUrl("user/perform_login")
                .loginPage("/user/login").failureUrl("/user/login?error=true")

//                .successForwardUrl("/user/perform_login")
                .defaultSuccessUrl("/user/client/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/user/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }

/* TOKEN
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // allow anonymous resource requests
                .antMatchers(

                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**//*.html",
            "/user/registration",
            "/user/login",
            "/user/forgot",
            "/user/confirmation",
            "/user/home"
            ).permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();

    // Custom JWT based token filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    // disable page caching
        httpSecurity.headers().cacheControl();
}
*/

}