package com.liferay.gasBudget.internal.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.gasBudget.dto.v1_0.GasBudget;
import com.liferay.gasBudget.dto.v1_0.GasBudgetRequest;
import com.liferay.gasBudget.dto.v1_0.GasBudgetRequest.MetersWaterIntake;
import com.liferay.gasBudget.dto.v1_0.GasBudgetExtra;
import com.liferay.gasBudget.dto.v1_0.GasBudgetRequest.PersonsWater;

import org.json.JSONException;
import org.json.JSONObject;

public class Calculator {
  static String GAS_BUDGET_REQUEST_URL = System.getenv().get("GAS_BUDGET_REQUEST_URL");
	static String SOLUSOFT_SECRET = System.getenv().get("SOLUSOFT_SECRET");

	public GasBudget createGasBudget(GasBudgetRequest gasBudgetRequest) {

    JSONObject jsonRequest = new JSONObject();
    GasBudget responseBudget = new GasBudget();

    try {
      jsonRequest.put("ZipCode", gasBudgetRequest.getPostalCode());
      jsonRequest.put("HouseType", gasBudgetRequest.getHouseType());
      jsonRequest.put("PropertyMeters", gasBudgetRequest.getPropertyMeters());
      jsonRequest.put("StaysNumber", gasBudgetRequest.getStaysNumber());
      jsonRequest.put("BathroomNumber", gasBudgetRequest.getBathroomNumber());
      jsonRequest.put("FloorNumber", gasBudgetRequest.getFloorNumber());
      jsonRequest.put("GasNaturalUse", gasBudgetRequest.getGasNaturalUse() );
      jsonRequest.put("ACSUse", gasBudgetRequest.getAcsUse());
      jsonRequest.put("KitchenUse", gasBudgetRequest.getKitchenUse());
      jsonRequest.put("HeatingUse", gasBudgetRequest.getHeatingUse());
      jsonRequest.put("PersonsWater", this.translatePersonsWater(gasBudgetRequest.getPersonsWater()));
      jsonRequest.put("BoilerLocation", gasBudgetRequest.getBoilerLocation());
      jsonRequest.put("Extras", new JSONObject());

      jsonRequest.getJSONObject("Extras").put("MetersBoilerToWindow", gasBudgetRequest.getMetersBoilerToWindow());
      jsonRequest.getJSONObject("Extras").put("MetersWaterIntake", this.translateMetersWaterIntake(gasBudgetRequest.getMetersWaterIntake()));
      jsonRequest.getJSONObject("Extras").put("HasVentilationGrill", gasBudgetRequest.getHasVentilationGrill() ? "Si" : "No");
      jsonRequest.getJSONObject("Extras").put("ControllHeatingFloor", gasBudgetRequest.getControllHeatingFloor() ? "Si" : "No");
      jsonRequest.getJSONObject("Extras").put("ConnectDeviceToKitchen", gasBudgetRequest.getConnectDeviceToKitchen() ? "Si" : "No");
      jsonRequest.getJSONObject("Extras").put("ConvertDeviceKitchen", gasBudgetRequest.getConvertDeviceKitchen() ? "Si" : "No");
      jsonRequest.getJSONObject("Extras").put("RadiatorsBathroom", gasBudgetRequest.getRadiatorsBathroom());

    } catch (JSONException e) {
      e.printStackTrace();
      return responseBudget;
    }

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().
			uri(URI.create(Calculator.GAS_BUDGET_REQUEST_URL)).
			header("x-auth-token", Calculator.SOLUSOFT_SECRET).
			header("Content-Type", "application/json").
			POST(HttpRequest.BodyPublishers.ofString(jsonRequest.toString())).
      build();

    System.out.println("Solicitando presupuesto a " + Calculator.GAS_BUDGET_REQUEST_URL);
    System.out.println(">    Detalle de presupuesto " + jsonRequest.toString());

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      System.out.println(">    Respuesta " + response.body());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      return null;
    }

    try {
      JSONObject jsonResponse =  new JSONObject(response.body());
      JSONObject jsonBudget = jsonResponse.getJSONObject("data").getJSONArray("items").getJSONObject(0);

      responseBudget.setProposedPack(jsonBudget.getString("ProposedPack"));
      responseBudget.setEquipment(jsonBudget.getString("Equipment"));
      responseBudget.setBaseBudget(jsonBudget.getString("BaseBadget"));
      responseBudget.setBonus(jsonBudget.getString("Bonus"));
      responseBudget.setTotalBudget(jsonBudget.getString("TotalBudget"));
      responseBudget.setVat(jsonBudget.getString("Iva21"));
      responseBudget.setTotalPrice(jsonBudget.getString("TotalPVP"));

      JSONObject jsonExtras = jsonBudget.getJSONObject("Extras");

      responseBudget.setMetersBoilerToWindow(this.createExtra(jsonExtras.getJSONObject("MetersBoilerToWindow")));
      responseBudget.setMetersWaterIntake(this.createExtra(jsonExtras.getJSONObject("MetersWaterIntake")));
      responseBudget.setHasVentilationGrill(this.createExtra(jsonExtras.getJSONObject("HasVentilationGrill")));
      responseBudget.setControllHeatingFloor(this.createExtra(jsonExtras.getJSONObject("ControllHeatingFloor")));
      responseBudget.setConnectDeviceToKitchen(this.createExtra(jsonExtras.getJSONObject("ConnectDeviceToKitchen")));
      responseBudget.setConvertDeviceKitchen(this.createExtra(jsonExtras.getJSONObject("ConvertDeviceKitchen")));
      responseBudget.setRadiatorsBathroom(this.createExtra(jsonExtras.getJSONObject("RadiatorsBathroom")));

      responseBudget.setExtraTotalPrice(jsonExtras.getString("ExtraTotalPrice"));
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }

    return responseBudget;

  }

  private GasBudgetExtra createExtra(JSONObject jsonExtra) throws JSONException {
    GasBudgetExtra tempExtra = new GasBudgetExtra();
    tempExtra.setValue(jsonExtra.getString("Value"));
    tempExtra.setPrice(jsonExtra.getString("Price"));
    return tempExtra;
  }

  private String translatePersonsWater(PersonsWater original) {
    switch (original.getValue()) {
      case "Hasta 2":
        return "Hasta 2 personas";
      case "Entre 3-4":
        return "De 3-4 personas";
      case "Más de 4":
        return "5 o más personas";
    }
    return null;
  }

  private String translateMetersWaterIntake(MetersWaterIntake original) {
    switch (original.getValue()) {
      case "m.0":
        return "0";
      case "m.1":
        return "1";
      case "m.2":
        return "2";
      case "m.3":
        return "3";
      case "m.4":
        return "4";
    }
    return null;
  }

}