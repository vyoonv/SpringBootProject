package edu.kh.project.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/config.properties")
public class EmailConfig {
	
	// @Value : properties에 작성된 내용 중 키가 일치하는 값을 얻어와 필드에 대입
		@Value("${spring.mail.username}")
		private String userName;
		
		@Value("${spring.mail.password}")
		private String password; 
		
		@Bean
		public JavaMailSender javaMailSender() {
			
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp"); // 전송 프로토콜 설정
			prop.setProperty("mail.smtp.auth", "true"); // 서버인증 사용 여부
			prop.setProperty("mail.smtp.starttls.enable", "true"); // 안전한 연결을 할지말지 활성 여부
			prop.setProperty("mail.debug", "true"); // 디버그 모드 사용 여부
			prop.setProperty("mail.smtp.ssl.trust","smtp.gmail.com"); // 신뢰할 수 있는 smtp 서버 호스트 지정
			prop.setProperty("mail.smtp.ssl.protocols","TLSv1.2"); // 프로토콜 설정
			
			
			mailSender.setUsername(userName);
			mailSender.setPassword(password);
			mailSender.setHost("smtp.gmail.com"); // smtp 서버 호스트 설정
			mailSender.setPort(587);	// smtp 서버의 포트 설정
			mailSender.setDefaultEncoding("UTF-8"); // 기본 인코딩
			mailSender.setJavaMailProperties(prop); // 앞에서 정의한 속성 세팅(저장)
			
			
			return mailSender;
		}
	
	
}
	