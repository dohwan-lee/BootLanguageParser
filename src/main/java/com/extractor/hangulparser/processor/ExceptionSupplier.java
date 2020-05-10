package com.extractor.hangulparser.processor;

/**
 * Created by joseph on 2020/05/10.
 */
public interface ExceptionSupplier<T> {

	T get() throws Exception;

}
