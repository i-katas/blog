package com.kata.blog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;

/**
 * @author i-katas
 * @since 1.0
 */
public class BlogTest {
    private Application blogs = new Application();

    @Before
    public void setUp() throws Exception {
        blogs.start();
    }

    @Test
    public void returnEmptyListIfNoBlogPublished() throws IOException, InterruptedException {
        blogs.list().assertThat("$", is(empty()));
    }

    @After
    public void tearDown() throws Exception {
        blogs.stop();
    }
}
