package com.vijac.notes.utility;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMHelper {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
		.getPersistenceManagerFactory("transactions-optional");

	private PMHelper() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}