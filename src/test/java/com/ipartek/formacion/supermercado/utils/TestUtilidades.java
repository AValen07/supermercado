package com.ipartek.formacion.supermercado.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestUtilidades {
	
	@Test
	public void testObtenerId() throws Exception {
		
		
		assertEquals(2, Utilidades.obtenerId("/2") );
		assertEquals(2, Utilidades.obtenerId("/2/") );
		assertEquals(99, Utilidades.obtenerId("/99/") );
		
		try {
			assertEquals(99, Utilidades.obtenerId("/99/333/hola/") );
			fail("Deberia haber lanzado Exception");
			
		}catch (Exception e) {
			
			assertTrue(true);
		}		
	}
	
	@Test
	public void contarPalabrasTest1() {	
		
		assertEquals(0, Utilidades.contarPalabras(null));	
		assertEquals(0, Utilidades.contarPalabras(""));	
		assertEquals(0, Utilidades.contarPalabras("   "));	
		assertEquals(2, Utilidades.contarPalabras("hola caracola"));	
		assertEquals(2, Utilidades.contarPalabras("hola    mundo"));	
		assertEquals(2, Utilidades.contarPalabras("   hola    mundo  "));	
	}
	
	@Test
	public void contarPalabrasTest2() {
		
		assertEquals(2, Utilidades.contarPalabras("hola,mundo"));	
		assertEquals(2, Utilidades.contarPalabras("hola , mundo"));	
		assertEquals(2, Utilidades.contarPalabras("hola...?mundo"));
			
	}
}
