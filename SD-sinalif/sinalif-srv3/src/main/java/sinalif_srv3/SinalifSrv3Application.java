package sinalif_srv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SinalifSrv3Application {

	public static void main(String[] args) {
		SpringApplication.run(SinalifSrv3Application.class, args);
	}

}
