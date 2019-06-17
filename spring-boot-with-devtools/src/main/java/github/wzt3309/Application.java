package github.wzt3309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @RestController
	class AccountController {
        private final AccountService accService;

        public AccountController(AccountService accService) {
            this.accService = accService;
        }

        @RequestMapping("/{name}")
        public String hello(@PathVariable String name) {
            return accService.sayHello(name);
        }
    }

	@Service
	class AccountService {
	    final AccountRepository accRepository;

	    // If a bean has one constructor, you can omit the @Autowired
        AccountService(AccountRepository accRepository) {
            this.accRepository = accRepository;
        }

        String sayHello(String name) {
            Account acc = accRepository.get(name);
            if (acc != null) {
                return String.format("Account %s's age is %d", acc.name, acc.age);
            }
            return "No account found for " + name;
        }
    }

    @Bean
    AccountRepository getAccountRepository() {
	    return (name) -> {
	        Account acc = new Account();
	        acc.name = name;
	        acc.age = (int) (Math.random() * 15 + 20);
	        return acc;
        };
    }

    @Bean
    @Primary
    AccountRepository getAccountRepository2() {
        return (name) -> {
            Account acc = new Account();
            acc.name = String.format("[%s]", name);
            acc.age = (int) (Math.random() * 15 + 20);
            return acc;
        };
    }

    interface AccountRepository {
	    Account get(String name);
    }

    class Account {
	    String name;
	    Integer age;
    }
}
