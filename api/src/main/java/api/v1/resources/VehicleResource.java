package api.v1.resources;

import lib.VehicleDetails;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import services.beans.VehicleBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResource {

    private Logger log = Logger.getLogger(VehicleResource.class.getName());

    @Inject
    private VehicleBean vehicleBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all vehicles data.", summary = "Get all vehicles")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of users data",
                    content = @Content(schema = @Schema(implementation = VehicleDetails.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getImageMetadata() {

        List<VehicleDetails> usersData = vehicleBean.getAllVehicles();

        return Response.status(Response.Status.OK).entity(usersData).build();
    }
}
