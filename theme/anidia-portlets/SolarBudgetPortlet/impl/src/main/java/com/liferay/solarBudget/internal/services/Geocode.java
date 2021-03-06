package com.liferay.solarBudget.internal.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.kernel.log.*;

import org.json.*;
import com.liferay.solarBudget.dto.v1_0.PostalCode;
import com.liferay.solarBudget.dto.v1_0.Property;
import com.liferay.solarBudget.dto.v1_0.Address;
import com.liferay.solarBudget.dto.v1_0.Estate;
public class Geocode{
    static String GEOCODE_LOGIN_URL = System.getenv().get("GEOCODE_LOGIN_URL");
    static String GEOCODE_MUNICIPALITIES_URL = System.getenv().get("GEOCODE_MUNICIPALITIES_URL");
    static String GEOCODE_PROPERTIES_URL = System.getenv().get("GEOCODE_PROPERTIES_URL");
    static String GEOCODE_ADDRESSES_URL = System.getenv().get("GEOCODE_ADDRESSES_URL");
    static String GEOCODE_ESTATES_URL = System.getenv().get("GEOCODE_ESTATES_URL");

	  private Log log = LogFactoryUtil.getLog(Geocode.class.getName());

    private String getGeocodeToken() {

        JSONObject jsonRequestBody = new JSONObject();
        try {
            jsonRequestBody.put("UserName", System.getenv().get("GEOCODE_USER_NAME"));
            jsonRequestBody.put("UserAccess", System.getenv().get("GEOCODE_USER_ACCESS"));
            jsonRequestBody.put("UserService", System.getenv().get("GEOCODE_USER_SERVICE"));
        } catch (JSONException e) {
          e.printStackTrace();
        }

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().
        uri(URI.create(Geocode.GEOCODE_LOGIN_URL)).
        header("Content-Type", "application/json").
        POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody.toString())).
          build();

        log.info("Requesting token to " + Geocode.GEOCODE_LOGIN_URL);

      HttpResponse<String> response = null;
      try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
      } catch (IOException | InterruptedException e) {
        if(response != null) {
          log.info(response.body());
        }
        e.printStackTrace();
        return null;
      }

      JSONObject json;
      try {
        json = new JSONObject(response.body());
      } catch (JSONException e) {
        log.info(response.body());
        e.printStackTrace();
        return null;
      }

      try {
        return json.getString("UserToken") + "@" + json.getString("UserDomain");
      } catch (JSONException e) {
        log.info(json.toString());
        e.printStackTrace();
        return null;
      }
    }



    public List<PostalCode> getMunicipalities(String postalCode) {
        List<PostalCode> postalCodes = new ArrayList<PostalCode>();
        String url = Geocode.GEOCODE_MUNICIPALITIES_URL + "/" + postalCode;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
            uri(URI.create(url)).
            header("Content-Type", "application/json").
            header("codSesion", getGeocodeToken()).
            GET().
            build();

        HttpResponse<String> response = null;
        JSONArray responseJson;
        log.info("Requesting municipalities to " + url);
        try {
          response = client.send(request, HttpResponse.BodyHandlers.ofString());
          responseJson = new JSONArray(response.body());
          log.info("Geocode response: " + responseJson);
        } catch (JSONException | IOException | InterruptedException e) {
          e.printStackTrace();
          return postalCodes;
        }


        for (int i = 0 ; i < responseJson.length(); i++) {
          JSONObject postalcodeJson = null;
          try {
            postalcodeJson = responseJson.getJSONObject(i);
            PostalCode postalCodeData = new PostalCode();
            postalCodeData.setMunicipalityId(postalcodeJson.getString("codMunicipio"));
            postalCodeData.setMunicipalityName(postalcodeJson.getString("desMunicipio"));
            postalCodeData.setPopulationName(postalcodeJson.getString("desPoblacion"));
            postalCodeData.setPopulationId(postalcodeJson.getString("codPoblacion"));
            postalCodeData.setProvinceId(postalcodeJson.getString("codProvincia"));
            postalCodes.add(postalCodeData);
          } catch (JSONException e) {
            log.info("Geocode response: " + response.body());
            if(postalcodeJson != null) {
              log.info("Json Object with error: " + postalcodeJson.toString());
            }
            e.printStackTrace();
          }
        }
        return postalCodes;
      }


    public List<Property> getProperties( String postalCode, String municipalityId,
    String addressId, String portalNumber) {
      List<Property> properties = new ArrayList<Property>();


      StringBuilder urlBuilder = new StringBuilder();
      urlBuilder.append(Geocode.GEOCODE_PROPERTIES_URL);
      urlBuilder.append("?");
      urlBuilder.append("&codProvincia=");
      urlBuilder.append(postalCode.substring(0, 2));
      urlBuilder.append("&codMunicipio=");
      urlBuilder.append(municipalityId);
      urlBuilder.append("&codVia=");
      urlBuilder.append(addressId);
      urlBuilder.append("&numPortal=");
      urlBuilder.append(portalNumber);
      urlBuilder.append("&sistemaCoordenada=ETRS89");

      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().
          uri(URI.create(urlBuilder.toString())).
          header("Content-Type", "application/json").
          header("codSesion", getGeocodeToken()).
          GET().
          build();

      log.info("Requesting properties to " + urlBuilder.toString());


      HttpResponse<String> response = null;
      JSONArray responseJson;

      try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        responseJson = new JSONArray(response.body());
        log.info("Geocode response: " + responseJson);
      } catch (JSONException | IOException | InterruptedException e) {
        e.printStackTrace();
        return properties;
      }

          for (int i = 0 ; i < responseJson.length(); i++) {
              JSONObject propertyJson = null;
              try {
                propertyJson = responseJson.getJSONObject(i);
                Property property = new Property();
                property.setPropertyId(propertyJson.optString("referenciaCatastral"));
                property.setBlock(propertyJson.optString("bloque"));
                property.setLadder(propertyJson.optString("escalera"));
                property.setFloor(propertyJson.optString("piso"));
                property.setDoor(propertyJson.optString("puerta"));

                StringBuilder completeAddress = new StringBuilder();
                if(property.getBlock() != null && !property.getBlock().equals("")) {
                  completeAddress.append("Bloque ");
                  completeAddress.append(property.getBlock());
                  completeAddress.append(" ");
                }
                if(property.getLadder() != null && !property.getLadder().equals("")) {
                  completeAddress.append("Escalera ");
                  completeAddress.append(property.getLadder());
                  completeAddress.append(" ");
                }
                if(property.getFloor() != null && !property.getFloor().equals("")) {
                  completeAddress.append("Piso ");
                  completeAddress.append(property.getFloor());
                  completeAddress.append(" ");
                }
                if(property.getDoor() != null && !property.getDoor().equals("")) {
                  completeAddress.append("Puerta ");
                  completeAddress.append(property.getDoor());
                }

                if(completeAddress.toString().equals("")) {
                  property.setAddress(propertyJson.optString("Direccion_completa__c"));
                } else {
                  property.setAddress(completeAddress.toString());
                }

                properties.add(property);
              } catch (JSONException e) {
                log.info("Geocode response: " + response.body());
                if(propertyJson != null) {
                  log.info("Json Object with error: " + propertyJson.toString());
                }
                e.printStackTrace();
            }
          }
    return properties;
  }



  public List<Address> getAddresses(String populationId, String postalCode) {
    List<Address> addresses = new ArrayList<Address>();
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append(Geocode.GEOCODE_ADDRESSES_URL);
    urlBuilder.append("?");
    urlBuilder.append("&codPoblacion=");
    urlBuilder.append(populationId);
    urlBuilder.append("&codPostal=");
    urlBuilder.append(postalCode);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().
        uri(URI.create(urlBuilder.toString())).
        header("Content-Type", "application/json").
        header("codSesion", getGeocodeToken()).
        GET().
        build();

    log.info("Requesting addresses to " + urlBuilder.toString());

    HttpResponse<String> response = null;
    JSONArray responseJson;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      responseJson = new JSONArray(response.body());
      log.info("Geocode response: " + responseJson);
     } catch (JSONException | IOException | InterruptedException e) {
      e.printStackTrace();
      return addresses;
    }


    for (int i = 0 ; i < responseJson.length(); i++) {
      JSONObject addressJson = null;
      try {
        addressJson = responseJson.getJSONObject(i);
        Address address = new Address();
        address.setKind(addressJson.getString("tipoVia"));
        address.setName(addressJson.getString("desVia"));
        address.setAddressId(addressJson.getString("codVia"));
        addresses.add(address);
      } catch (JSONException e) {
        log.info("Geocode response: " + response.body());
        if(addressJson != null) {
          log.info("Json Object with error: " + addressJson.toString());
        }
        e.printStackTrace();
      }
    }
    return addresses;
  }

  public List<Estate> getEstates(String populationId, String addressId) {
    List<Estate> estates = new ArrayList<Estate>();
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append(Geocode.GEOCODE_ESTATES_URL);
    urlBuilder.append("?");
    urlBuilder.append("&codPoblacion=");
    urlBuilder.append(populationId);
    urlBuilder.append("&codVia=");
    urlBuilder.append(addressId);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().
        uri(URI.create(urlBuilder.toString())).
        header("Content-Type", "application/json").
        header("codSesion", getGeocodeToken()).
        GET().
        build();

    log.info("Requesting addresses to " + urlBuilder.toString());

    HttpResponse<String> response = null;
    JSONArray responseJson;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      responseJson = new JSONArray(response.body());
      log.info("Geocode response: " + responseJson);

     } catch (JSONException | IOException | InterruptedException e) {
      e.printStackTrace();
      return estates;
    }


    for (int i = 0 ; i < responseJson.length(); i++) {
      JSONObject estateJson = null;
      try {
        estateJson = responseJson.getJSONObject(i);
        Estate estate = new Estate();
        estate.setNumber(estateJson.getString("numPortal"));
        estate.setAnnex(estateJson.getString("letra"));
        estate.setGateId(estateJson.getString("codRedexisPortal"));
        estates.add(estate);
      } catch (JSONException e) {
        log.info("Geocode response: " + response.body());
        if(estateJson != null) {
          log.info("Json Object with error: " + estateJson.toString());
        }
        e.printStackTrace();
      }
    }
    return estates;
  }
}
