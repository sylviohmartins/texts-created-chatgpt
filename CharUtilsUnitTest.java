package io.martins.valhalla;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.martins.valhalla.util.CharUtils;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CharUtilsUnitTest {

  // \p{P} - Pontuação (ex.: !, ?, ., ,)
  @Test
  void testContainsSpecialCharOrEmoji_successWithPunctuation() {
    final var input = "Hello, World!";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar pontuação como caractere especial.");
  }

  // \p{S} - Símbolos (ex.: $, %, &, #)
  @Test
  void testContainsSpecialCharOrEmoji_successWithSymbols() {
    final var input = "This costs $100.";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar símbolos como $, %, & e #.");
  }

  // \p{M} - Marcas de combinação (acentos combináveis, ex.: á, é, ç)
  @Test
  void testContainsSpecialCharOrEmoji_successWithCombiningMarks() {
    final var input = "Olá, você está bem?";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar acentos e marcas de combinação como á, é, ç.");
  }

  // \p{C} - Caracteres de controle (ex.: quebras de linha, tabulação)
  @Test
  void testContainsSpecialCharOrEmoji_successWithControlCharacters() {
    final var input = "Text with newline\nand tab\tcharacters";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar caracteres de controle como quebras de linha e tabulação.");
  }

  // \u200D - Zero-width joiner (ZWJ) para formar emojis compostos (ex.: 👨‍👩‍👧‍👦)
  @Test
  void testContainsSpecialCharOrEmoji_successWithZeroWidthJoiner() {
    final var input = "Family emoji 👨‍👩‍👧‍👦";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar um emoji composto com ZWJ.");
  }

  // \u200B - Espaço de largura zero (zero-width space)
  @Test
  void testContainsSpecialCharOrEmoji_successWithZeroWidthSpace() {
    final var input = "Text\u200Bwith zero-width space";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar o espaço de largura zero.");
  }

  // \u200C - Caractere separador sem largura (zero-width non-joiner)
  @Test
  void testContainsSpecialCharOrEmoji_successWithZeroWidthNonJoiner() {
    final var input = "Text\u200Cwith zero-width non-joiner";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar o zero-width non-joiner.");
  }

  // \u200E - Marcador de leitura da esquerda para direita (LTR)
  @Test
  void testContainsSpecialCharOrEmoji_successWithLTRMarker() {
    final var input = "Text with LTR marker\u200E";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar o marcador LTR.");
  }

  // \u200F - Marcador de leitura da direita para esquerda (RTL)
  @Test
  void testContainsSpecialCharOrEmoji_successWithRTLMarker() {
    final var input = "Text with RTL marker\u200F";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar o marcador RTL.");
  }

  // \u00A0 - Espaço em branco total (non-breaking space)
  @Test
  void testContainsSpecialCharOrEmoji_successWithNonBreakingSpace() {
    final var input = "This has a non-breaking space\u00A0here";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar o espaço em branco total (non-breaking space).");
  }

  // \uFEFF - Marca de ordem de bytes (Byte order mark - BOM, invisível)
  @Test
  void testContainsSpecialCharOrEmoji_successWithBOM() {
    final var input = "\uFEFFThis string starts with a BOM";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar a marca de ordem de bytes (BOM).");
  }

  // \u2600-\u27BF - Símbolos e pictogramas (ex.: ☀️, ✈️, ⚽)
  @Test
  void testContainsSpecialCharOrEmoji_successWithSymbolsAndPictograms() {
    final var input = "I love the sun ☀️ and airplanes ✈️";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar símbolos e pictogramas como ☀️, ✈️, ⚽.");
  }

  // \u1F000-\u1FAFF - Emojis (rostos, objetos, gestos, animais, ex.: 😁, 🐶, 🍕)
  @Test
  void testContainsSpecialCharOrEmoji_successWithEmojis() {
    final var input = "I love pizza 🍕 and dogs 🐶";
    assertTrue(CharUtils.containsSpecialCharOrEmoji(input), "Deveria detectar emojis de rostos, objetos, gestos, animais, etc.");
  }

  // Casos de falha

  @Test
  void testContainsSpecialCharOrEmoji_failureWithRegularText() {
    String input = "simple";
    System.out.println(Arrays.toString(input.toCharArray()));

    assertFalse(CharUtils.containsSpecialCharOrEmoji(input), "Não deveria detectar nenhum caractere especial em texto regular.");
  }


  @Test
  void testContainsSpecialCharOrEmoji_failureWithEmptyString() {
    final var input = "";
    assertFalse(CharUtils.containsSpecialCharOrEmoji(input), "Não deveria detectar caracteres especiais em uma string vazia.");
  }

  @Test
  void testContainsSpecialCharOrEmoji_failureWithNullInput() {
    final String input = null;
    assertFalse(CharUtils.containsSpecialCharOrEmoji(input), "Não deveria detectar caracteres especiais em uma string nula.");
  }

}
