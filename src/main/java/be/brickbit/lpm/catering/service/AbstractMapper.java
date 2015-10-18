package be.brickbit.lpm.catering.service;

public abstract class AbstractMapper<Source, T> {
    public abstract T map(Source source);
}
