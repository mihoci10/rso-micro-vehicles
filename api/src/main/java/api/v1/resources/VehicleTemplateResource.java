package api.v1.resources;

import lib.VehicleDetails;
import lib.VehicleTemplateDetails;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import payload.VehiclePayload;
import payload.VehicleTemplatePayload;
import services.beans.VehicleBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("templates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleTemplateResource {

    private Logger log = Logger.getLogger(VehicleResource.class.getName());

    @Inject
    private VehicleBean vehicleBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all vehicle templates.", summary = "Get all templates")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of templates",
                    content = @Content(schema = @Schema(implementation = VehicleTemplateDetails.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getTemplates() {

        List<VehicleTemplateDetails> templatesData = vehicleBean.getAllTemplates();

        return Response.status(Response.Status.OK).entity(templatesData).build();
    }

    @Operation(description = "Add a vehicle template", summary = "Add vehicle template")
    @Path("add")
    @POST
    public Response addVehicleTemplate(@RequestBody(
            description = "Object with segment data.",
            required = true, content = @Content(
            schema = @Schema(implementation = VehicleTemplatePayload.class))) VehicleTemplatePayload vehicleTemplatePayload) {

        try{
            vehicleBean.addVehicleTemplate(vehicleTemplatePayload);
        }
        catch (Exception e){
            System.out.println(e);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
