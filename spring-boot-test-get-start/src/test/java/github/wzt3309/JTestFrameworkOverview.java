package github.wzt3309;

import github.wzt3309.domain.Foo;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JTestFrameworkOverview {

    @Test
    public void hamcrestOverview() {
        Foo foo1 = new Foo("foo");
        Foo foo2 = new Foo("foo");
        MatcherAssert.assertThat(foo1, Matchers.equalTo(foo2));
    }

    @Test
    public void assertJOverview() {
        Foo foo1 = new Foo("foo");
        Foo foo2 = new Foo("foo");
        assertThat(foo1).isEqualTo(foo2);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void mockitoOverview() {
        List<String> mockedList = (List<String>) mock(List.class);
        when(mockedList.add("one")).thenReturn(true);
        when(mockedList.size()).thenReturn(1);

        assertThat(mockedList.add("one")).isTrue();
        assertThat(mockedList.add("two")).isFalse();
        assertThat(mockedList.size()).isEqualTo(1);
    }

}
