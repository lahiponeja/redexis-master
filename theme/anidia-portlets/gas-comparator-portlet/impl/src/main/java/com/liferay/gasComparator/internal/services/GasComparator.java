package com.liferay.gasComparator.internal.services;

import com.liferay.gasComparator.dto.v1_0.GasConsumptionComparison;
import com.liferay.gasComparator.dto.v1_0.GasCalculatedConsumption;

import com.liferay.gasComparator.internal.exception.PortletException;

import java.util.*;

import com.liferay.portal.kernel.json.JSON;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class GasComparator {
  static String GAS_COMPARATOR_CONSUMPTION_REQUEST_URL = System.getenv().get("GAS_COMPARATOR_CONSUMPTION_REQUEST_URL");
  static String GAS_COMPARATOR_USE_REQUEST_URL = System.getenv().get("GAS_COMPARATOR_USE_REQUEST_URL");
  static String SOLUSOFT_SECRET = System.getenv().get("SOLUSOFT_SECRET");

  public GasConsumptionComparison compareByDirectConsumption(GasCalculatedConsumption gasCalculatedConsumption) throws PortletException {

    JSONObject jsonRequest = new JSONObject();
    GasConsumptionComparison gasConsumptionComparison = new GasConsumptionComparison();

    try {

      jsonRequest.put("AnualConsumptionButane", 0);
      jsonRequest.put("AnualConsumptionGLP", 0);
      jsonRequest.put("AnualConsumptionGOC", 0);
      jsonRequest.put("AnualConsuptionElectricity", gasCalculatedConsumption.getElectricityConsumption());
      jsonRequest.put("AcsUse", gasCalculatedConsumption.getAcsUse() ? "Si" : "No");
      jsonRequest.put("HeatingUse", gasCalculatedConsumption.getHeatingUse() ? "Si" : "No");
      jsonRequest.put("KitchenUse", gasCalculatedConsumption.getKitchenUse() ? "Si" : "No");

    } catch (JSONException e) {
      e.printStackTrace();
      throw new PortletException(1, "Error creating request");
    }

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().
      uri(URI.create(GAS_COMPARATOR_CONSUMPTION_REQUEST_URL)).
      header("x-auth-token", SOLUSOFT_SECRET).
      header("Content-Type", "application/json").
      POST(HttpRequest.BodyPublishers.ofString(jsonRequest.toString())).
      build();

    System.out.println("Solicitando comaprativa a " + GAS_COMPARATOR_CONSUMPTION_REQUEST_URL);
    System.out.println(">    Detalle de comparativa " + jsonRequest.toString());

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(">    Respuesta " + response.body());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      throw new PortletException(2, "Error sending request");
    }

    try {
      JSONObject jsonResponse =  new JSONObject(response.body());
      JSONObject jsonBudget = jsonResponse.getJSONObject("data").getJSONArray("items").getJSONObject(0);

      gasConsumptionComparison.setConsumptionRequired(jsonBudget.getString("HouseConsumptionRequired"));
      gasConsumptionComparison.setCurrentCost(jsonBudget.getString("CurrentEnergyCost"));
      gasConsumptionComparison.setFutureCost(jsonBudget.getString("GNCost"));
      gasConsumptionComparison.setSavings(jsonBudget.getString("GNSaving"));
    } catch (JSONException e) {
      e.printStackTrace();
      throw new PortletException(3, "Error in the response");
    }

    return gasConsumptionComparison;

  }

}
