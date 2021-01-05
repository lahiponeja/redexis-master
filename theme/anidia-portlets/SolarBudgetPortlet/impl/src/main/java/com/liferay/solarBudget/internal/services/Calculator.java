package com.liferay.solarBudget.internal.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.solarBudget.dto.v1_0.SolarBudget;
import com.liferay.solarBudget.dto.v1_0.SolarBudgetRequest;
//import com.liferay.solarBudget.dto.v1_0.GasBudgetRequest.MetersWaterIntake;
import com.liferay.solarBudget.dto.v1_0.SolarBudgetExtra;
import com.liferay.solarBudget.dto.v1_0.SolarOutputInverter;
import com.liferay.solarBudget.dto.v1_0.SolarSuperiorInstallation;
import com.liferay.solarBudget.dto.v1_0.SuperiorSize;
//import com.liferay.solarBudget.dto.v1_0.GasBudgetRequest.PersonsWater;

import org.json.JSONException;
import org.json.JSONObject;

public class Calculator {
  static String SOLAR_BUDGET_REQUEST_URL = System.getenv().get("SOLAR_BUDGET_REQUEST_URL");
	static String SOLUSOFT_SECRET = System.getenv().get("SOLUSOFT_SECRET");

	public SolarBudget createSolarBudget(SolarBudgetRequest solarBudgetRequest) {

    JSONObject jsonRequest = new JSONObject();
    SolarBudget responseBudget = new SolarBudget();
    System.out.println(solarBudgetRequest);
    try {
      jsonRequest.put("EntryKey", solarBudgetRequest.getEntryKey());
      jsonRequest.put("HouseType", solarBudgetRequest.getHouseTypeAsString());
      jsonRequest.put("MonthlyConsumption", solarBudgetRequest.getMonthlyConsumption());
      jsonRequest.put("AnnualConsumption", solarBudgetRequest.getAnnualConsumption());
      jsonRequest.put("AdditionalPanels", solarBudgetRequest.getAdditionalPanels());
      jsonRequest.put("PanelsCanTell", solarBudgetRequest.getPanelsCanTell());
      jsonRequest.put("PanelsNumber", solarBudgetRequest.getPanelsNumber());
      jsonRequest.put("PanelsSelected", solarBudgetRequest.getPanelsSelected());
      jsonRequest.put("InstallationType", solarBudgetRequest.getInstallationType());
      jsonRequest.put("InverterOversized", solarBudgetRequest.getInverterOversized());
      jsonRequest.put("InverterType", solarBudgetRequest.getInverterType());
      jsonRequest.put("RoofType", solarBudgetRequest.getRoofTypeAsString());
      jsonRequest.put("Pergola", solarBudgetRequest.getPergola());
      jsonRequest.put("PipelineUnderground", solarBudgetRequest.getPipelineUnderground());
      jsonRequest.put("PipelineMeters", solarBudgetRequest.getPipelineMeters());
      jsonRequest.put("CarCharger", solarBudgetRequest.getCarCharger());
      jsonRequest.put("NeedBattery", solarBudgetRequest.getNeedBattery());
      jsonRequest.put("HasAdditionalPanels", solarBudgetRequest.getHasAdditionalPanels());
      jsonRequest.put("NumberAdditionalPanels", solarBudgetRequest.getNumberAdditionalPanels());

      jsonRequest.put("ElectricalAppliances", new JSONObject());
      jsonRequest.getJSONObject("ElectricalAppliances").put("ElectricalAppliance1", solarBudgetRequest.getElectricalAppliances().getElectricalAppliance1());
      jsonRequest.getJSONObject("ElectricalAppliances").put("ElectricalAppliance2",solarBudgetRequest.getElectricalAppliances().getElectricalAppliance2());
      jsonRequest.getJSONObject("ElectricalAppliances").put("ElectricalAppliance3", solarBudgetRequest.getElectricalAppliances().getElectricalAppliance3());

    } catch (JSONException e) {
      e.printStackTrace();
      return responseBudget;
    }

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().
			uri(URI.create(Calculator.SOLAR_BUDGET_REQUEST_URL)).
			header("x-auth-token", Calculator.SOLUSOFT_SECRET).
			header("Content-Type", "application/json").
			POST(HttpRequest.BodyPublishers.ofString(jsonRequest.toString())).
      build();

    System.out.println("Solicitando presupuesto a " + Calculator.SOLAR_BUDGET_REQUEST_URL);
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
      responseBudget.setPanelsType(jsonBudget.getString("PanelsType"));
      responseBudget.setTotalPrize(jsonBudget.get("TotalPrice").toString());
      responseBudget.setSize(this.createExtra(jsonBudget.getJSONObject("Size")));
      responseBudget.setInverter(this.createInverter(jsonBudget.getJSONObject("Inverter")));
      responseBudget.setPanelsExtra(this.createExtra(jsonBudget.getJSONObject("PanelsExtra")));
      responseBudget.setTriphasicExtra(this.createExtra(jsonBudget.getJSONObject("TriphasicExtra")));
      responseBudget.setInverterExtra(this.createExtra(jsonBudget.getJSONObject("InverterExtra")));
      responseBudget.setRoofExtra(this.createExtra(jsonBudget.getJSONObject("RoofExtra")));
      responseBudget.setPergolaExtra(this.createExtra(jsonBudget.getJSONObject("PergolaExtra")));
      responseBudget.setPipelineExtra(this.createExtra(jsonBudget.getJSONObject("PipelineExtra")));
      responseBudget.setCarCharger(this.createExtra(jsonBudget.getJSONObject("CarCharcher")));
      responseBudget.setBattery(this.createExtra(jsonBudget.getJSONObject("Battery")));
      responseBudget.setAdditionalPanelsInstallation(this.createExtra(jsonBudget.getJSONObject("AdditionalPanelsInstallation")));

      JSONObject jsonSuperiorInstallation = jsonBudget.getJSONObject("SuperiorInstallation");
      responseBudget.setSuperiorInstallation(this.createSuperiorInstallation(jsonSuperiorInstallation, jsonBudget ));

    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }

    return responseBudget;

  }
  private SolarSuperiorInstallation createSuperiorInstallation(JSONObject jsonSuperiorInstallation,JSONObject jsonBudget) throws JSONException {
    SolarSuperiorInstallation superiorInstallation = new SolarSuperiorInstallation();
    
    superiorInstallation.setSuperiorSize(this.createSuperiorSize(jsonSuperiorInstallation.getJSONObject("SuperiorSize")));
    superiorInstallation.setPanelType(jsonSuperiorInstallation.getString("PanelType"));
    superiorInstallation.setInverterType(jsonSuperiorInstallation.getString("InverterType"));
    superiorInstallation.setExtraFornius(jsonSuperiorInstallation.getString("ExtraFornius"));
    superiorInstallation.setPanelsExtra(this.createSuperiorExtra(jsonBudget.getJSONObject("PanelsExtra"),jsonSuperiorInstallation.getString("PanelsExtra") ));

    return superiorInstallation;
  }
  private SuperiorSize createSuperiorSize(JSONObject jsonSuperiorSize) throws JSONException {
    SuperiorSize superiorSize = new SuperiorSize();
    superiorSize.setValue(jsonSuperiorSize.get("Value") != null ? jsonSuperiorSize.get("Value").toString() : "" );
    superiorSize.setPrice(jsonSuperiorSize.get("Price") != null ? jsonSuperiorSize.get("Price").toString() : "" );
    return superiorSize;
  }

  private SolarBudgetExtra createSuperiorExtra(JSONObject jsonExtra, String unitPrize) throws JSONException {
    SolarBudgetExtra extra = new SolarBudgetExtra();
    extra.setValue(jsonExtra.get("Value") != null ? jsonExtra.get("Value").toString() : "" );
    extra.setUnitPrice(unitPrize);
    extra.setPrice( "" );
    return extra;
  }

  private SolarBudgetExtra createExtra(JSONObject jsonExtra) throws JSONException {
    SolarBudgetExtra extra = new SolarBudgetExtra();
    extra.setValue(jsonExtra.get("Value") != null ? jsonExtra.get("Value").toString() : "" );
    extra.setUnitPrice(jsonExtra.get("UnitPrice") != null ? jsonExtra.get("UnitPrice").toString() : "" );
    extra.setPrice(jsonExtra.get("Price") != null ? jsonExtra.get("Price").toString() : "" );
    return extra;
  }
  private SolarOutputInverter createInverter(JSONObject jsonExtra) throws JSONException {
    SolarOutputInverter inverter = new SolarOutputInverter();
    inverter.setBrand(jsonExtra.get("Brand") != null ? jsonExtra.get("Brand").toString() : "" );
    inverter.setModel(jsonExtra.get("Model") != null ? jsonExtra.get("Model").toString() : "" );
    inverter.setPrice(jsonExtra.get("Price") != null ? jsonExtra.get("Price").toString() : "" );
    return inverter;
  }
/*
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
*/
}
