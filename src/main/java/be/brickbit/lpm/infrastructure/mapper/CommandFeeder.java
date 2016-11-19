package be.brickbit.lpm.infrastructure.mapper;

public interface CommandFeeder<S, T> {

    void feed(S source, T target);
}
