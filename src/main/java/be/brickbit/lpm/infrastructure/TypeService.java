package be.brickbit.lpm.infrastructure;

import be.brickbit.lpm.infrastructure.mapper.Extractor;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiConsumer;

public interface TypeService<E, T extends Serializable> {

    <C> C findAllExtracted(Extractor<E, C> extractor);

    <D> List<D> findAll(Mapper<E, D> mapper);

    <D> List<D> findAll(Mapper<E, D> dtoMapper, Sort sort);

    <D> D findOne(T id, Mapper<E, D> dtoMapper);

    <D> List<D> findAll(List<T> ids, Mapper<E, D> dtoMapper);

    <C> void feedCommand(T id, C command, BiConsumer<? super E, C> commandFeeder);
}
