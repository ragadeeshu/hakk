package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import networking.BitMask;

import org.junit.Before;
import org.junit.Test;

public class BitMaskTest {
	private HashMap<String, BitMask> bitmasks;

	@Before
	public void setUp() {
		bitmasks = new HashMap<>();
		try {
			String name = "sprites/empty.png";
			BufferedImage img = ImageIO.read(new File(name));
			bitmasks.put("empty.png", new BitMask(img));
			name = "sprites/full.png";
			img = ImageIO.read(new File(name));
			bitmasks.put("full.png", new BitMask(img));
			name = "sprites/left.png";
			img = ImageIO.read(new File(name));
			bitmasks.put("left.png", new BitMask(img));
			name = "sprites/right.png";
			img = ImageIO.read(new File(name));
			bitmasks.put("right.png", new BitMask(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEmpyOnEmpty() {
		assertFalse(bitmasks.get("empty.png").overlaps(0, 0, 0, 0,
				bitmasks.get("empty.png")));
	}

	@Test
	public void testFullOnFull() {
		assertTrue(bitmasks.get("full.png").overlaps(0, 0, 0, 0,
				bitmasks.get("full.png")));
	}

	@Test
	public void testFullUnderFull() {
		assertFalse(bitmasks.get("full.png").overlaps(0, 10, 0, 0,
				bitmasks.get("full.png")));
	}

	@Test
	public void testFullUnderFull2() {
		assertFalse(bitmasks.get("full.png").overlaps(0, 0, 0, 10,
				bitmasks.get("full.png")));
	}

	@Test
	public void testFullHalfUnderFull() {
		assertTrue(bitmasks.get("full.png").overlaps(0, 5, 0, 0,
				bitmasks.get("full.png")));
	}

	@Test
	public void testFullHalfUnderFull2() {
		assertTrue(bitmasks.get("full.png").overlaps(0, 0, 0, 5,
				bitmasks.get("full.png")));
	}

	@Test
	public void testLeftOnRight() {
		assertFalse(bitmasks.get("right.png").overlaps(0, 0, 0, 0,
				bitmasks.get("left.png")));
	}

	@Test
	public void testRightOnLeftShiftLeft() {
		assertTrue(bitmasks.get("left.png").overlaps(1, 0, 0, 0,
				bitmasks.get("right.png")));
	}

	@Test
	public void testRightOnLeftShifRight() {
		BitMask firstMask = bitmasks.get("left.png");
		BitMask secondMask = bitmasks.get("right.png");
		int x = 0;
		int y = 0;
		int ox = 1;
		int oy = 0;
		System.out.println("Before shift:");
		System.out.println(firstMask.toString());
		System.out.println(secondMask.toString());
		System.out.println("After shift: " + x + ", " + y + ", " + ox + ", "
				+ oy);
		System.out.println(firstMask.shiftedToString(x, y));
		System.out.println(secondMask.shiftedToString(ox, oy));
		assertFalse(firstMask.overlaps(x, y, ox, oy, secondMask));
	}

}
