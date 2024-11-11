//package khuong.com.webmakeupconnection.config;
//
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/login", "/register").permitAll()  // Cho phép truy cập các trang login và register
//                .anyRequest().authenticated()  // Yêu cầu xác thực cho tất cả các trang còn lại
//                .and()
//                .formLogin()  // Cấu hình đăng nhập
//                .loginPage("/login")  // Đường dẫn trang login
//                .loginProcessingUrl("/login")  // Đường dẫn xử lý đăng nhập
//                .defaultSuccessUrl("/home", true)  // Chuyển hướng sau khi đăng nhập thành công
//                .permitAll()
//                .and()
//                .logout()  // Cấu hình logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")  // Chuyển hướng sau khi logout thành công
//                .permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Tạo session nếu cần
//                .maximumSessions(1)  // Giới hạn số phiên làm việc của một người dùng
//                .expiredUrl("/login?expired");  // Chuyển hướng khi session hết hạn
//
//        return http.build();
//    }
//}
