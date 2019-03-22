package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
	
	@Test
	public void testAdjacentLocationsFromCornerExtern() {
		Location location = new Location(0, 2);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(2, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(1, 2)));
		assertTrue(adjacentLocations.contains(new Location(7, 2)));
	}

	@Test
	public void testAdjacentLocationsFromMiddleExtern() {
		Location location = new Location(5, 2);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(3, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(5, 1)));
		assertTrue(adjacentLocations.contains(new Location(6, 2)));
		assertTrue(adjacentLocations.contains(new Location(4, 2)));
	}

	@Test
	public void testAdjacentLocationsFromVerticalCross() {
		Location location = new Location(5, 1);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(4, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(4, 1)));
		assertTrue(adjacentLocations.contains(new Location(6, 1)));
		assertTrue(adjacentLocations.contains(new Location(5, 0)));
		assertTrue(adjacentLocations.contains(new Location(5, 2)));
	}

	@Test
	public void testAdjacentLocationsFromHorizontalCross() {
		Location location = new Location(7, 1);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(4, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(7, 2)));
		assertTrue(adjacentLocations.contains(new Location(7, 0)));
		assertTrue(adjacentLocations.contains(new Location(0, 1)));
		assertTrue(adjacentLocations.contains(new Location(6, 1)));
	}

	@Test
	public void testAdjacentLocationsFromIntermediateCorner() {
		Location location = new Location(4, 1);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(2, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(3, 1)));
		assertTrue(adjacentLocations.contains(new Location(5, 1)));
	}

	@Test
	public void testAdjacentLocationsFromInternalCorner() {
		Location location = new Location(4, 0);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(2, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(3, 0)));
		assertTrue(adjacentLocations.contains(new Location(5, 0)));
	}

	@Test
	public void testAdjacentLocationsFromMiddleIntern() {
		Location location = new Location(5, 0);
		List<Location> adjacentLocations = location.getAdjacentLocations();
		assertEquals(3, adjacentLocations.size());
		assertTrue(adjacentLocations.contains(new Location(4, 0)));
		assertTrue(adjacentLocations.contains(new Location(6, 0)));
		assertTrue(adjacentLocations.contains(new Location(5, 1)));
	}
}
