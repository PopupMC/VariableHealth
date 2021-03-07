package com.popupmc.variablehealth.utility;

import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringTools {
    public static String readableMobName(EntityType type) {
        return convertToTitleCaseSplitting(
                type.name().toLowerCase().replaceAll("_", " "));
    }

    public static String convertToTitleCaseSplitting(String text) {
        final String WORD_SEPARATOR = " ";

        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays
                .stream(text.split(WORD_SEPARATOR))
                .map(word -> word.isEmpty()
                        ? word
                        : Character.toTitleCase(word.charAt(0)) + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(WORD_SEPARATOR));
    }
}
