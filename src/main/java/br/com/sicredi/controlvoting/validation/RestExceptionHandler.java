package br.com.sicredi.controlvoting.validation;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

import java.text.MessageFormat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.controlvoting.dto.ResponseMessages;
import br.com.sicredi.controlvoting.exception.BadRequestException;
import br.com.sicredi.controlvoting.exception.DataAlreadyRegisteredException;
import br.com.sicredi.controlvoting.exception.DataNotFoundException;
import br.com.sicredi.controlvoting.exception.InvalidDataFormatException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseMessages> handleConstraintViolationException(
			final ConstraintViolationException exception) {
		final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		final Set<String> messages = constraintViolations.stream().map(this::buildMessage).collect(toSet());

		return badRequest().body(new ResponseMessages(messages));

	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ResponseMessages> handleNotFoundException(final DataNotFoundException exception) {
		return notFound().build();

	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseMessages> handleBadRequestException(final BadRequestException exception) {
		return badRequest().body(buildMessages(exception.getMessage()));

	}

	@ExceptionHandler(DataAlreadyRegisteredException.class)
	public ResponseEntity<ResponseMessages> handleDataRegisteredException(
			final DataAlreadyRegisteredException exception) {
		return status(HttpStatus.FORBIDDEN).body(buildMessages(exception.getMessage()));

	}

	@ExceptionHandler(InvalidDataFormatException.class)
	public ResponseEntity<ResponseMessages> handleInvalidDataFormatException(
			final InvalidDataFormatException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildMessages(exception.getMessage()));
	}

	private ResponseMessages buildMessages(final String message) {
		final ResponseMessages responseMessages = new ResponseMessages();
		responseMessages.addMessage(message);

		return responseMessages;

	}

	private String buildMessage(final ConstraintViolation<?> constraintViolation) {
		final RestController restControllerAnnotation = constraintViolation.getRootBeanClass()
				.getDeclaredAnnotation(RestController.class);

		String field = constraintViolation.getPropertyPath().toString();
		if (restControllerAnnotation != null) {
			field = ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName();

		}
		return MessageFormat.format("{} {}", field, constraintViolation.getMessage());

	}

}
