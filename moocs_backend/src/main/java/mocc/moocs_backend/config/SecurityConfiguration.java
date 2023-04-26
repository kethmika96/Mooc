package mocc.moocs_backend.config;

import mocc.moocs_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenHelper jWTTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // in memory Authentication
         auth.inMemoryAuthentication().withUser("kethmika2").password(passwordEncoder().encode("12345")).authorities("USER","ADMIN");
        //database Authentication
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public  PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


            //http.authorizeRequests((request) ->request.antMatchers("/api/v1/auth/login").permitAll().anyRequest().authenticated()).httpBasic();

            //http.authorizeRequests().anyRequest().authenticated();

           http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                           .authorizeRequests( (request) ->request.antMatchers("/h2-console/**","/api/v1/auth/login").permitAll().antMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated())
                            .addFilterBefore(new JWTAuthenticationFilter(userService, jWTTokenHelper), UsernamePasswordAuthenticationFilter.class);

         //   http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

        http.cors();
//        http.formLogin();
        http.csrf().disable().headers().frameOptions().disable();
    }
}
