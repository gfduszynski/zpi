/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier;

/**
 *
 * @author Daniel
 */

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * @author Daniel
 */
public abstract class AbstractTest extends AbstractTransactionalJUnit4SpringContextTests {

	@BeforeTransaction
	public void setupData() throws Exception {
		if (countRowsInTable("Tournament") == 0) {
			executeSqlScript("classpath:data.sql", false);
		}
	}
}