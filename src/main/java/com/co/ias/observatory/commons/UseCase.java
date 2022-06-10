package com.co.ias.observatory.commons;

public interface UseCase<Input, Output> {

    Output execute(Input input);

}