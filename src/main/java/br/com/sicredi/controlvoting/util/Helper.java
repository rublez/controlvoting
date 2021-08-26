package br.com.sicredi.controlvoting.util;

import java.io.Serializable;

public class Helper {

	private Helper() {
		throw new IllegalStateException("Helper class");

	}

	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) {

		if (!(obj instanceof Serializable))
			throw new UnsupportedOperationException("To clone an object, it must be Serializable");

		return (T) org.apache.commons.lang3.SerializationUtils.clone((Serializable) obj);

	}

}
