package com.ipartek.formacion.supermercado.utils;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.LowerCase;

public class Utilidades {

	/**
	 * obtenemos el id del pathinfo o uri
	 * 
	 * @param pathInfo parte de la uri donde debemos buscar un numero
	 * @return numero id
	 * @throws Exception si el parth info esta mal formado.
	 * 
	 *                   Ejemplos:
	 *                   <ol>
	 *                   <li>/ url valida</li>
	 *                   <li>/2 url valida</li>
	 *                   <li>/2/ ul valida</li>
	 *                   <li>/2/2 url pathinfo esta mal formado</li>
	 *                   <li>/2/otracosa/34 pathinfo esta mal formado</li>
	 *                   </ol>
	 */
	public static int obtenerId(String pathInfo) throws Exception {
		int id = 0;
		try {
			if (pathInfo.length() == 1 || pathInfo == null) {
				id = -1;
			} else if (pathInfo.endsWith("/")) {

				id = Integer.parseInt(pathInfo.substring(1, (pathInfo.length() - 1)));

			} else {

				id = Integer.parseInt(pathInfo.substring(1));
			}
		} catch (NumberFormatException e) {
			throw new Exception("formato erroneo");
		}
		// throw new Exception("Sin implementar");
		// return 0;
		return id;
	}

	/**
	 * Obtenemos el numero de palabras que hay en una frase.
	 * 
	 * @param frase String del que deberemos contar el numero de palabras.
	 * @return el numero de palabras.
	 */	
	public static int contarPalabras(String frase) {
		int resultado = 0;
		boolean sw = true;

		if (frase != null && frase.length() > 0) {
			for (char c : frase.toCharArray()) {
				if (!Character.isLetter(c) && !sw) {
					sw = true;
				} else if (Character.isLetter(c) && sw) {
					resultado++;
					sw = false;
				}
			}
		}
		return resultado;
	}
}
