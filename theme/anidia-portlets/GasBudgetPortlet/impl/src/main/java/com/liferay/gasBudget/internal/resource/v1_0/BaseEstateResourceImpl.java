package com.liferay.gasBudget.internal.resource.v1_0;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.gasBudget.dto.v1_0.Estate;
import com.liferay.gasBudget.resource.v1_0.EstateResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriInfo;

/**
 * @author David Brenes
 * @generated
 */
@Generated("")
@Path("/v1.0")
public abstract class BaseEstateResourceImpl implements EstateResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/gas-budget/v1.0/estates/{municipalityId}/{postalCode}/{addressKind}/{addressName}'  -u 'test@liferay.com:test'
	 */
	@Override
	@GET
	@Operation(
		description = "Get all the estates for a whole address from Anidia CRM"
	)
	@Parameters(
		value = {
			@Parameter(in = ParameterIn.PATH, name = "municipalityId"),
			@Parameter(in = ParameterIn.PATH, name = "postalCode"),
			@Parameter(in = ParameterIn.PATH, name = "addressKind"),
			@Parameter(in = ParameterIn.PATH, name = "addressName")
		}
	)
	@Path("/estates/{municipalityId}/{postalCode}/{addressKind}/{addressName}")
	@Produces({"application/json", "application/xml"})
	@Tags(value = {@Tag(name = "Estate")})
	public Page<Estate> getEstatesAddressNamePage(
			@NotNull @Parameter(hidden = true) @PathParam("municipalityId")
				String municipalityId,
			@NotNull @Parameter(hidden = true) @PathParam("postalCode") String
				postalCode,
			@NotNull @Parameter(hidden = true) @PathParam("addressKind") String
				addressKind,
			@NotNull @Parameter(hidden = true) @PathParam("addressName") String
				addressName)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(User contextUser) {
		this.contextUser = contextUser;
	}

	protected void preparePatch(Estate estate, Estate existingEstate) {
	}

	protected <T, R> List<R> transform(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		java.util.Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected UriInfo contextUriInfo;
	protected User contextUser;

}