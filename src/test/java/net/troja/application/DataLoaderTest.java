package net.troja.application;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataLoaderTest {
    @Autowired
    private DataLoader classToTest;

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LogEvent> captorLoggingEvent;

    private Logger logger;

    @Autowired
    private Station2RouteRepository respository;

    @Before
    public void setUp() {
        when(mockAppender.getName()).thenReturn("MockAppender");
        when(mockAppender.isStarted()).thenReturn(true);
        when(mockAppender.isStopped()).thenReturn(false);

        logger = (Logger) LogManager.getLogger(DataLoader.class);
        logger.addAppender(mockAppender);
        logger.setLevel(Level.INFO);
    }

    @After
    public void tearDown() {
        logger.removeAppender(mockAppender);
    }

    @Test
    public void validFile() {
        final Path path = Paths.get("src/test/resources/demodata.txt");

        classToTest.load(path);

        assertThat(respository.count(), equalTo(12l));
    }

    @Test
    public void errorFile1() {
        final Path path = Paths.get("src/test/resources/error1.txt");

        classToTest.load(path);

        verifyErrorMessages(DataLoader.LOG_INVALID_FIRST_LINE);
    }

    @Test
    public void errorFile2() {
        final Path path = Paths.get("src/test/resources/error2.txt");

        classToTest.load(path);

        verifyErrorMessages(DataLoader.LOG_INVALID_CONTENT);
    }

    @Test
    public void errorFile3() {
        final Path path = Paths.get("src/test/resources/error3.txt");

        classToTest.load(path);

        verifyErrorMessages(DataLoader.LOG_INVALID_FIRST_LINE);
    }

    @Test
    public void errorFile4() {
        final Path path = Paths.get("src/test/resources/error4.txt");

        classToTest.load(path);

        verifyErrorMessages(DataLoader.LOG_INVALID_FIRST_LINE, DataLoader.LOG_INVALID_LINE);
    }

    @Test
    public void errorFile5() {
        final Path path = Paths.get("src/test/resources/error5.txt");

        classToTest.load(path);

        verifyErrorMessages(DataLoader.LOG_INVALID_LINE);
    }

    private void verifyErrorMessages(final String... messages) {
        verify(mockAppender, times(messages.length)).append(captorLoggingEvent.capture());

        int i = 0;
        for (final LogEvent loggingEvent : captorLoggingEvent.getAllValues()) {
            assertThat(messages[i++], equalTo(loggingEvent.getMessage().getFormattedMessage()));
        }
    }
}
