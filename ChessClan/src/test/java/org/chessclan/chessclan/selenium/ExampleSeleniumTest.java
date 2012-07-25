package org.chessclan.chessclan.selenium;

import java.net.URI;
import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Grzegorz Duszyski <gfduszynski@gmail.com>
 */
public class ExampleSeleniumTest extends TestCase {

    private URI siteBase;
    private WebDriver drv;

    {
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                drv.close();
            }
        });
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        siteBase = new URI("http://localhost:10001/ChessClan-1.0-SNAPSHOT/index.html");
        drv = new FirefoxDriver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testWeSeeHelloWorld() {
        drv.get(siteBase.toString());
        assertTrue(drv.getPageSource().contains("Hello"));
    }
}