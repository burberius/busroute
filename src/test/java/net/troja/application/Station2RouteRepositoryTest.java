package net.troja.application;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import net.troja.application.model.Station2Route;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Station2RouteRepositoryTest {
    @Autowired
    private Station2RouteRepository classToTest;

    @Before
    public void setUp() {
        classToTest.deleteAll();
    }

    @Test
    public void match() {
        classToTest.save(new Station2Route(1, 1));
        classToTest.save(new Station2Route(2, 1));

        assertThat(classToTest.onRoute(1, 2), equalTo(true));
    }

    @Test
    public void miss() {
        classToTest.save(new Station2Route(1, 1));
        classToTest.save(new Station2Route(2, 2));

        assertThat(classToTest.onRoute(1, 2), equalTo(false));
    }
}
