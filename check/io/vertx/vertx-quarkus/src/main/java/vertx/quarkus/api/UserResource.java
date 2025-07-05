package vertx.quarkus.api;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import vertx.quarkus.model.User;

import java.net.URI;
import java.util.List;

@Slf4j
@Path("/users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UserResource {

    @GET
    public Uni<List<User>> get() {
        log.info("Get all users...");
        return User.listAll(Sort.by("id"));
    }

    @GET
    @Path("/{id}")
    public Uni<User> getById(Long id) {
        log.info("Get by id: {}", id);
        return User.findById(id);
    }

    @POST
    public Uni<Response> create(User user) {
        log.info("Create: {}", user);
        return Panache.<User>withTransaction(user::persist)
                .onItem().transform(insertedUser ->
                        Response.created(URI.create("/users/" + insertedUser.id)).build()
                );
    }

}
