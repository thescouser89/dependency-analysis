package org.jboss.da.rest.repositories;

import org.jboss.da.common.CommunicationException;
import org.jboss.da.communication.aprox.api.AproxConnector;
import org.jboss.da.communication.aprox.api.AproxConnector.RepositoryManipulationStatus;
import org.jboss.da.communication.aprox.model.Repository;
import org.jboss.da.rest.listings.model.SuccessResponse;
import org.jboss.da.rest.model.ErrorMessage;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/repositories")
@Api(value = "/repositories", description = "Get repositories used in analyse")
public class Repositories {

    @Inject
    private Logger log;

    @Inject
    private AproxConnector aprox;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add repository to group for searching ",
            response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Repository successfully added to group for searching"),
            @ApiResponse(code = 502, message = "Communication with remote repository failed"),
            @ApiResponse(code = 409,
                    message = "Repository with this name already exists with different url") })
    public Response addRepository(@ApiParam(value = "repository data") Repository repository) {
        SuccessResponse response = new SuccessResponse();
        RepositoryManipulationStatus status;
        try {
            status = aprox.addRepositoryToGroup(repository);
        } catch (CommunicationException ex) {
            log.error("Communication with remote repository failed", ex);
            return Response.status(502)
                    .entity(new ErrorMessage("Communication with remote repository failed"))
                    .build();
        }
        switch (status) {
            case DONE:
                response.setSuccess(true);
                return Response.status(Status.OK).entity(response).build();
            case NAME_EXIST_DIFFERENT_URL:
                response.setSuccess(false);
                response.setMessage("Repository with this name already exists with different url");
                return Response.status(Status.CONFLICT).entity(response).build();
            default:
                response.setSuccess(false);
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Remove repository from group for searching ",
            response = SuccessResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Repository successfully removed from group for searching"),
            @ApiResponse(code = 502, message = "Communication with remote repository failed"),
            @ApiResponse(code = 409,
                    message = "Repository with this name has different url than entered url"),
            @ApiResponse(code = 404, message = "Repository with this name was not found") })
    public Response removeRepository(Repository repository) {
        SuccessResponse response = new SuccessResponse();
        RepositoryManipulationStatus status;
        try {
            status = aprox.removeRepositoryFromGroup(repository);
        } catch (CommunicationException ex) {
            log.error("Communication with remote repository failed", ex);
            return Response.status(502)
                    .entity(new ErrorMessage("Communication with remote repository failed"))
                    .build();
        }
        switch (status) {
            case DONE:
                response.setSuccess(true);
                return Response.status(Status.OK).entity(response).build();
            case NAME_EXIST_DIFFERENT_URL:
                response.setSuccess(false);
                response.setMessage("Repository with this name has different url than entered url");
                return Response.status(Status.CONFLICT).entity(response).build();
            case NAME_NOT_EXIST:
                response.setSuccess(false);
                response.setMessage("Repository with this name was not found");
                return Response.status(Status.NOT_FOUND).entity(response).build();
            default:
                response.setSuccess(false);
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all repositories from group for searching ",
            responseContainer = "List", response = Repository.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Repositories successfully returned"),
            @ApiResponse(code = 502, message = "Communication with remote repository failed") })
    public Response getAllRepositories() throws CommunicationException {
        List<Repository> repositories = aprox.getAllRepositoriesFromGroup();
        return Response.status(Status.OK).entity(repositories).build();
    }

}
