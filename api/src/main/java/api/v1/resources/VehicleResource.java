package api.v1.resources;

import lib.VehicleDetails;
import lib.VehicleTemplateDetails;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import payload.VehiclePayload;
import payload.VehicleUpdatePayload;
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
                    description = "List of vehicles data",
                    content = @Content(schema = @Schema(implementation = VehicleDetails.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getVehicles() {

        List<VehicleDetails> vehicleData = vehicleBean.getAllVehicles();

        return Response.status(Response.Status.OK).entity(vehicleData).build();
    }

    @Operation(description = "Get specific vehicle data.", summary = "Get vehicle data")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Vehicle data",
                    content = @Content(
                            schema = @Schema(implementation = VehicleDetails.class))
            )})
    @GET
    @Path("/{vehicleId}")
    public Response getImageMetadata(@Parameter(description = "Vehicle ID.", required = true)
                                     @PathParam("vehicleId") Integer vehicleId) {

        VehicleDetails vehicleDetails = vehicleBean.getVehicle(vehicleId);

        if (vehicleDetails == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(vehicleDetails).build();
    }

    @Operation(description = "Get specific user vehicles data.", summary = "Get user vehicles data")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Vehicles data",
                    content = @Content(
                            schema = @Schema(implementation = VehicleDetails.class, type = SchemaType.ARRAY))
            )})
    @GET
    @Path("/ofuser/{userId}")
    public Response getUsersVehicles(@Parameter(description = "User ID.", required = true)
                                     @PathParam("userId") Integer userId) {

        List<VehicleDetails> vehicleDetails = vehicleBean.getUserVehicles(userId);

        return Response.status(Response.Status.OK).entity(vehicleDetails).build();
    }

    @Operation(description = "Add a new vehicle", summary = "Add vehicle")
    @Path("add")
    @POST
    public Response addVehicle(@RequestBody(
            description = "Object with segment data.",
            required = true, content = @Content(
            schema = @Schema(implementation = VehiclePayload.class))) VehiclePayload vehiclePayload) {

        try{
            vehicleBean.addVehicle(vehiclePayload);
        }
        catch (Exception e){
            System.out.println(e);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @Operation(description = "Update vehicle data", summary = "Update vehicle")
    @Path("update")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "kWh",
                    content = @Content(
                            schema = @Schema(implementation = Double.class)))})
    @POST
    public Response updateVehicle(@RequestBody(
            description = "Object with geo stat data.",
            required = true, content = @Content(
            schema = @Schema(implementation = VehicleUpdatePayload.class))) VehicleUpdatePayload vehicleUpdatePayload) {

        try{
            Double usedKWH = vehicleBean.updateVehicle(vehicleUpdatePayload);

            return Response.status(Response.Status.OK).entity(usedKWH).build();
        }
        catch (Exception e){
            System.out.println(e);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
