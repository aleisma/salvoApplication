package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.Salvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface SalvoRepository extends JpaRepository<Salvo, Long> {

}