package be.brickbit.lpm.infrastructure.mapper;

@FunctionalInterface
public interface Merger<Source, Target> {
    void merge(Source source, Target target);
}
