package net.troja.application;

import net.troja.application.model.Station2Route;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Station2RouteRepository extends CrudRepository<Station2Route, Long> {

}
