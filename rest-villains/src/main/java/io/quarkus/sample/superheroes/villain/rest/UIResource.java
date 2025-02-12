package io.quarkus.sample.superheroes.villain.rest;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.sample.superheroes.villain.Villain;
import io.quarkus.sample.superheroes.villain.service.VillainService;

@Path("/")
public class UIResource {
  @Inject
  VillainService service;

  @CheckedTemplate
  static class Templates {
    static native TemplateInstance index(List<Villain> villains);
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance get(@QueryParam("name_filter") Optional<String> nameFilter) {
    var villains = nameFilter
      .map(this.service::findAllVillainsHavingName)
      .orElseGet(this.service::findAllVillains);

    return Templates.index(villains);
  }
}
