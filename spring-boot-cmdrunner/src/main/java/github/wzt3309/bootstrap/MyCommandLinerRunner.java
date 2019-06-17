package github.wzt3309.bootstrap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Order(-1)
public class MyCommandLinerRunner implements CommandLineRunner {
    @Qualifier("hello")
    private final Consumer<String> hello;

    public MyCommandLinerRunner(Consumer<String> hello) {
        this.hello = hello;
    }

    @Override
    public void run(String... args) throws Exception {
        hello.accept(getClass() + ": " + args[0]);
    }
}
