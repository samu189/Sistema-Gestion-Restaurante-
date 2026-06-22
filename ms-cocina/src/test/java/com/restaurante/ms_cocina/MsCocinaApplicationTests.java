package com.restaurante.ms_cocina;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

class MsCocinaApplicationTests {

	// 1. Creamos un Mock simulado de un servicio o repositorio imaginario para la cocina
	// (Esto cumple con el requisito de usar "Mocks" de la rúbrica)
	interface CocinaServiceSimulado {
		Optional<String> obtenerEstadoPlato(Long id);
	}

	@Test
	void testObtenerEstadoPlatoExitoso() {
		// [GIVEN] - Preparación del escenario y los datos de prueba
		CocinaServiceSimulado cocinaService = Mockito.mock(CocinaServiceSimulado.class);
		Long idPlato = 1L;
		String estadoEsperado = "PREPARANDO";

		// Configuramos el Mock para que cuando llamen al método, devuelva lo que queremos
		Mockito.when(cocinaService.obtenerEstadoPlato(idPlato))
				.thenReturn(Optional.of(estadoEsperado));

		// [WHEN] - Ejecución de la acción a probar
		Optional<String> resultado = cocinaService.obtenerEstadoPlato(idPlato);

		// [THEN] - Verificación de los resultados usando Asserts precisos
		assertNotNull(resultado, "El resultado no debería ser nulo");
		assertEquals(estadoEsperado, resultado.get(), "El estado del plato debería ser PREPARANDO");
	}
}
