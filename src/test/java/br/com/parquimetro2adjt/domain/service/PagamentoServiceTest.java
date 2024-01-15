package br.com.parquimetro2adjt.domain.service;


import br.com.parquimetro2adjt.domain.entity.Estacionamento;
import br.com.parquimetro2adjt.domain.enums.TipoEstacionamentoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagamentoServiceTest {

    private final PagamentoService pagamentoService = new PagamentoService();

    @Test
    @DisplayName("Calcular valor do pagamento com TipoEstacionamento FIXO")
    public void testCalcularValorPagamentoFixo() {
        Estacionamento estacionamento = Estacionamento.builder()
                .tipoEstacionamento(TipoEstacionamentoEnum.FIXO)
                .duracaoDesejada(2)
                .build();

        assertEquals(4, pagamentoService.calcularValorPagamento(estacionamento));
    }

    @ParameterizedTest
    @MethodSource("datasParaCalcularPagamento")
    @DisplayName("Calcular valor do pagamento com TipoEstacionamento Variavel")
    public void testCalcularValorPagamentoVariavel(LocalDateTime horaInicial, LocalDateTime horaFinal, Double valorEsperado) {
        Estacionamento estacionamento = Estacionamento.builder()
                .tipoEstacionamento(TipoEstacionamentoEnum.VARIAVEL)
                .horaInicial(horaInicial)
                .horaFinal(horaFinal)
                .build();

        assertEquals(valorEsperado, pagamentoService.calcularValorPagamento(estacionamento));
    }

    private static Stream<Arguments> datasParaCalcularPagamento() {
        return Stream.of(
                Arguments.of(LocalDateTime.now(), LocalDateTime.now().plusHours(2), 4.0),
                Arguments.of(LocalDateTime.now(), LocalDateTime.now().plusHours(1).plusMinutes(30), 4.0),
                Arguments.of(LocalDateTime.now(), LocalDateTime.now().plusHours(2).plusMinutes(30), 6.0)
        );
    }
}
