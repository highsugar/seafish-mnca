package la.seafish.mnca.app;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication(scanBasePackages={"la.seafish.mnca.dao.*","la.seafish.mnca.app.*"})
@MapperScan(basePackages = {"la.seafish.mnca.dao.mapper"})
public class MncaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MncaApplication.class);
    }
}
