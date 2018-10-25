package net.atpco.rnd.customerprofile.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class FileManagerTest {

	private FileManager manager;
	
	@Before
	public void before() {
		manager = new FileManager();
	}
	
	@Test
	public void preparePassword() throws Exception {
		String fileName = manager.preparePassword("foo");
		assertEquals("password.txt", fileName);
	}
	
	@Test
	public void prepareKeys() throws Exception {
		String fileName = manager.prepareKeys("foo", "bar");
		assertEquals("keys.txt", fileName);
	}
	
	@Test
	public void prepareProfile() throws Exception {
		Map<String, String> props = new HashMap<>();
		props.put("ffn", "1234");
		props.put("seat-preference", "window");
		props.put("dob", "01/01/2010");
		props.put("cabin", "economy");
		String fileName = manager.prepareProfile("ca", props);
		assertEquals("ca.txt", fileName);
	}
	
}
