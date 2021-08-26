package br.com.sicredi.controlvoting.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.com.sicredi.controlvoting.exception.InvalidDataFormatException;

@JsonComponent
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) {
		try {
			final String dateTimeString = jsonParser.readValueAs(String.class);
			if (StringUtils.isNotBlank(dateTimeString)) {
				return LocalDateTime.parse(dateTimeString);

			}

		} catch (IOException | DateTimeParseException e) {
			throw new InvalidDataFormatException(e);

		}
		throw new InvalidDataFormatException(new RuntimeException("error deserializing"));

	}

}