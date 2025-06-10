package sinalif_srv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SinalifSrv1Application {

	public static void main(String[] args) {
		SpringApplication.run(SinalifSrv1Application.class, args);
	}

}
