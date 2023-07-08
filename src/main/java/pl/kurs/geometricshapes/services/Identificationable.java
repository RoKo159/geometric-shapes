package pl.kurs.geometricshapes.services;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Identificationable {

    Long getId();
}
