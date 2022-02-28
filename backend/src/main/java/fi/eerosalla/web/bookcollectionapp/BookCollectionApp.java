package fi.eerosalla.web.bookcollectionapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("fi.eerosalla.web.bookcollectionapp.*")
@SpringBootApplication
public class BookCollectionApp {

    public static void main(final String[] args) {
        SpringApplication.run(BookCollectionApp.class, args);
    }

}
