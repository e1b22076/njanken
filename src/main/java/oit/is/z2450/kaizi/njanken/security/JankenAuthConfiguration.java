package oit.is.z2450.kaizi.njanken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class JankenAuthConfiguration {
  /**
   * 認可処理に関する設定（認証されたユーザがどこにアクセスできるか）
   *
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll()
        .defaultSuccessUrl("/janken", true))
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(AntPathRequestMatcher.antMatcher("/janken/**"))
            .authenticated() // /sample3/以下は認証済みであること
            .requestMatchers(AntPathRequestMatcher.antMatcher("/**"))
            .permitAll())// 上記以外は全員アクセス可能
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/*")))// h2-console用にCSRF対策を無効化
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions
                .sameOrigin()));
    return http.build();
  }

  /**
   * 認証処理に関する設定（誰がどのようなロールでログインできるか）
   *
   * @return
   */

  // 以下ユーザ登録
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // ユーザ名，パスワード，ロールを指定してbuildする
    // このときパスワードはBCryptでハッシュ化されているため，{bcrypt}とつける
    // ハッシュ化せずに平文でパスワードを指定する場合は{noop}をつける

    UserDetails user1 = User.withUsername("user1")
        .password("{bcrypt}$2y$05$tFHbngokmoau74A3jNA/fOMx6xrAhE5kHMGiqWNvES33FuziCl.v2").roles("USER").build();
    UserDetails user2 = User.withUsername("user2")
        .password("{bcrypt}$2y$05$MTYAXucN0I2y5D5gJBQnAOtxmC1SnIUxQl4yeNnyQJKMBFVP9Ig9m").roles("USER").build();
    UserDetails user3 = User.withUsername("ほんだ")
        .password("{bcrypt}$2y$05$22YKy0nU0SIbvtXbesQ7LejU3PeKI1TAySbHVjEx3Zut5voXBYEOu").roles("USER").build();
    UserDetails admin = User.withUsername("admin")
        .password("{bcrypt}$2y$05$CpXmarhg3Bo4VEFfHbNsHeNhrwOoPh.ob5I5AjB.j0b4.HskySBxq").roles("ADMIN").build();

    // 生成したユーザをImMemoryUserDetailsManagerに渡す（いくつでも良い）
    return new InMemoryUserDetailsManager(user1, user2, user3, admin);
  }

}
