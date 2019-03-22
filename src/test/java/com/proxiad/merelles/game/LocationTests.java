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

	@Test
	public void testSameLocationIsNotAdjacent() {
		Location location1 = new Location(3,1);
		Location location2 = new Location(3,1);
		assertFalse(location1.isAdjacent(location2));
	}

	@Test
	public void testMiddleOfHorizontalLineIsAdjacentToCornerOnSameLine() {
		Location location1 = new Location(1,2);
		Location location2 = new Location(0,2);
		assertTrue(location1.isAdjacent(location2));
	}

	@Test
	public void testMiddleOfHorizontalLineIsAdjacentToOtherCornerOnSameLine() {
		Location location1 = new Location(1,2);
		Location location2 = new Location(2,2);
		assertTrue(location1.isAdjacent(location2));
	}

	@Test
	public void testMiddleOfHorizontalLineIsNotAdjacentToMiddleOfOtherLine() {
		Location location1 = new Location(1,2);
		Location location2 = new Location(3,2);
		assertFalse(location1.isAdjacent(location2));
	}

	@Test
	public void testMiddleOfHorizontalLineIsAdjacentToClosestVerticalSlot() {
		Location location1 = new Location(1,1);
		Location location2 = new Location(1,2);
		assertTrue(location1.isAdjacent(location2));
	}

	@Test
	public void testAdjacentPassingDirection0Ascending() {
		Location location1 = new Location(0,1);
		Location location2 = new Location(7,1);
		assertTrue(location1.isAdjacent(location2));
	}

	@Test
	public void testAdjacentPassingDirection0Descending() {
		Location location1 = new Location(7,1);
		Location location2 = new Location(0,1);
		assertTrue(location1.isAdjacent(location2));
	}

	@Test
	public void testAdjacentNotAdjacentPassingRadisu0() {
		Location location1 = new Location(5,0);
		Location location2 = new Location(5,2);
		assertFalse(location1.isAdjacent(location2));
	}
}
