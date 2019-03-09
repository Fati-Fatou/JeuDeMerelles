package com.proxiad.merelles.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LocationTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToString() {
		Location location = new Location(3,2);
		String text = location.toString();
		assertEquals("3 2", text);
	}

	@Test
	public void testEqualLocationsAreEqual() {
		Location location1 = new Location(3,2);
		Location location2 = new Location(3,2);
		assertEquals(location1, location2);
		assertEquals(location1.hashCode(), location2.hashCode());
	}

	@Test
	public void testDifferentLocationsAreNotEqual() {
		Location location1 = new Location(3,2);
		Location location2 = new Location(3,1);
		assertNotEquals(location1, location2);
	}
}
