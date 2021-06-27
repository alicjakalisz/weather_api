package com.example.demo.mapper;

public interface Mapper<D,E> {

    public D convertFromEntity (E entity );

    public E convertFromDto (D dto);
}
