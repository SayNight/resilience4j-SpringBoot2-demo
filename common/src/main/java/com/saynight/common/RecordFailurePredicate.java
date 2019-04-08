package com.saynight.common;

import java.util.function.Predicate;

import com.saynight.common.exception.BusinessException;

public class RecordFailurePredicate implements Predicate<Throwable> {
	
    @Override
    public boolean test(Throwable throwable) {
        return !(throwable instanceof BusinessException);
    }
}