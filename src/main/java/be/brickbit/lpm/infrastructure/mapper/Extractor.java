package be.brickbit.lpm.infrastructure.mapper;


import java.util.List;

@FunctionalInterface
public interface Extractor<S, T> {

    T extract(List<S> sources);
}
