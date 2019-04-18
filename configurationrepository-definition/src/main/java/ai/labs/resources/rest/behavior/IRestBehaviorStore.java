package ai.labs.resources.rest.behavior;

import ai.labs.resources.rest.IRestVersionInfo;
import ai.labs.resources.rest.behavior.model.BehaviorConfiguration;
import ai.labs.resources.rest.documentdescriptor.model.DocumentDescriptor;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author ginccc
 */
@Api(value = "configurations")
@Path("/behaviorstore/behaviorsets")
public interface IRestBehaviorStore extends IRestVersionInfo {
    String resourceURI = "eddi://ai.labs.behavior/behaviorstore/behaviorsets/";
    String versionQueryParam = "?version=";

    @GET
    @Path("descriptors")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", paramType = "query", dataType = "string", format = "string", example = "<name_of_behavior>"),
            @ApiImplicitParam(name = "index", paramType = "query", dataType = "integer", format = "integer", example = "0"),
            @ApiImplicitParam(name = "limit", paramType = "query", dataType = "integer", format = "integer", example = "20")})
    @ApiResponse(code = 200, response = DocumentDescriptor.class, responseContainer = "List",
            message = "Array of DocumentDescriptors")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Read list of behavior descriptors.")
    List<DocumentDescriptor> readBehaviorDescriptors(@QueryParam("filter") @DefaultValue("") String filter,
                                                     @QueryParam("index") @DefaultValue("0") Integer index,
                                                     @QueryParam("limit") @DefaultValue("20") Integer limit);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Read behavior rule set.")
    BehaviorConfiguration readBehaviorRuleSet(@PathParam("id") String id,
                                              @ApiParam(name = "version", required = true, format = "integer", example = "1")
                                              @QueryParam("version") Integer version);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update behavior rule set.")
    Response updateBehaviorRuleSet(@PathParam("id") String id,
                                   @ApiParam(name = "version", required = true, format = "integer", example = "1")
                                   @QueryParam("version") Integer version, BehaviorConfiguration behaviorConfiguration);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create behavior rule set.")
    Response createBehaviorRuleSet(BehaviorConfiguration behaviorConfiguration);

    @POST
    @Path("/{id}")
    @ApiOperation(value = "Duplicate this behavior rule set.")
    Response duplicateBehaviorRuleSet(@PathParam("id") String id, @QueryParam("version") Integer version);

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete behavior rule set.")
    Response deleteBehaviorRuleSet(@PathParam("id") String id,
                                   @ApiParam(name = "version", required = true, format = "integer", example = "1")
                                   @QueryParam("version") Integer version);
}
