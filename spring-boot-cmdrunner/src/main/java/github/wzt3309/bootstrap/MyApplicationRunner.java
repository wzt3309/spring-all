package github.wzt3309.bootstrap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Qualifier("hello")
    private final Consumer<String> hello;

    public MyApplicationRunner(Consumer<String> hello) {
        this.hello = hello;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        hello.accept(getClass() + ": " + args.getNonOptionArgs().get(0));
    }
}
