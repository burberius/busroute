package net.troja.application;

import net.troja.application.model.Station2Route;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Station2RouteRepository extends CrudRepository<Station2Route, Long> {
    @Query("SELECT count(*) > 0 FROM Station2Route s, Station2Route d WHERE s.route = d.route AND s.station = :source AND d.station = :destination")
    boolean onRoute(@Param("source") int source, @Param("destination") int destination);
}
