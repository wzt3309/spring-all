package github.wzt3309;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String name;
    private final Integer age;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    private User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    static Builder builder() {
        return new Builder();
    }

    static final class Builder {
        private String name;
        private Integer age;

        Builder name(String name) {
            this.name = name;
            return this;
        }

        Builder age(Integer age) {
            this.age = age;
            return this;
        }

        User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
