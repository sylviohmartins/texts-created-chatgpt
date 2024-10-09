package io.martins.valhalla.util;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

/**
 * Classe utilitária para validação de strings que detecta caracteres especiais, invisíveis e emojis. Essa classe não pode ser instanciada e oferece métodos estáticos para validação.
 */
@UtilityClass
public class CharUtils {

  // [^\\w\\s] - Qualquer caractere que não seja uma letra, número, underscore ou espaço.
  // \p{C} - Caracteres de controle (ex.: quebras de linha, tabulação, etc.)
  private static final String SPECIAL_CHAR_AND_EMOJI_REGEX = "[^\\w\\s]|\\p{C}";

  private static final Pattern SPECIAL_CHAR_AND_EMOJI_PATTERN = Pattern.compile(SPECIAL_CHAR_AND_EMOJI_REGEX);

  /**
   * Verifica se a string contém caracteres especiais, invisíveis ou emojis. A verificação inclui:
   * <ul>
   *   <li>Caractere especial: qualquer coisa que não seja letra, número, underscore ou espaço</li>
   *   <li>Caractere de controle: como quebras de linha e tabulações</li>
   *   <li>Emojis ou pictogramas</li>
   * </ul>
   *
   * @param input a string a ser verificada
   * @return {@code true} se a string contiver algum caractere especial, invisível ou emoji, caso contrário {@code false}
   */
  public static boolean containsSpecialCharOrEmoji(String input) {
    if (input == null || input.isEmpty()) {
      return false;
    }

    return SPECIAL_CHAR_AND_EMOJI_PATTERN.matcher(input).find();
  }

  /**
   * Retorna um Predicate que pode ser usado para validar strings em streams ou coleções. O Predicate retorna {@code true} se a string contiver caracteres especiais, invisíveis ou emojis.
   *
   * @return um Predicate que valida strings quanto à presença de caracteres especiais, invisíveis ou emojis
   */
  public static Predicate<String> specialCharOrEmojiPredicate() {
    return CharUtils::containsSpecialCharOrEmoji;
  }

}
