package com.liferay.solarBudget.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author David Brenes
 * @generated
 */
@Generated("")
@GraphQLName("CalculatorSolar")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CalculatorSolar")
public class CalculatorSolar {

	@Schema
	public String getInstallerCode() {
		return InstallerCode;
	}

	public void setInstallerCode(String InstallerCode) {
		this.InstallerCode = InstallerCode;
	}

	@JsonIgnore
	public void setInstallerCode(
		UnsafeSupplier<String, Exception> InstallerCodeUnsafeSupplier) {

		try {
			InstallerCode = InstallerCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String InstallerCode;

	@Schema
	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}

	@JsonIgnore
	public void setFinalPrice(
		UnsafeSupplier<String, Exception> finalPriceUnsafeSupplier) {

		try {
			finalPrice = finalPriceUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String finalPrice;

	@Schema
	public String getFinalPriceIva() {
		return finalPriceIva;
	}

	public void setFinalPriceIva(String finalPriceIva) {
		this.finalPriceIva = finalPriceIva;
	}

	@JsonIgnore
	public void setFinalPriceIva(
		UnsafeSupplier<String, Exception> finalPriceIvaUnsafeSupplier) {

		try {
			finalPriceIva = finalPriceIvaUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String finalPriceIva;

	@Schema(description = "Input object")
	@Valid
	public SolarBudgetRequest getInput() {
		return input;
	}

	public void setInput(SolarBudgetRequest input) {
		this.input = input;
	}

	@JsonIgnore
	public void setInput(
		UnsafeSupplier<SolarBudgetRequest, Exception> inputUnsafeSupplier) {

		try {
			input = inputUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SolarBudgetRequest input;

	@Schema
	public String getIvaPrice() {
		return ivaPrice;
	}

	public void setIvaPrice(String ivaPrice) {
		this.ivaPrice = ivaPrice;
	}

	@JsonIgnore
	public void setIvaPrice(
		UnsafeSupplier<String, Exception> ivaPriceUnsafeSupplier) {

		try {
			ivaPrice = ivaPriceUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String ivaPrice;

	@Schema(description = "Output object")
	@Valid
	public SolarBudget getOutput() {
		return output;
	}

	public void setOutput(SolarBudget output) {
		this.output = output;
	}

	@JsonIgnore
	public void setOutput(
		UnsafeSupplier<SolarBudget, Exception> outputUnsafeSupplier) {

		try {
			output = outputUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SolarBudget output;

	@Schema(description = "Selected extras")
	@Valid
	public SelectedExtras getSelectedExtras() {
		return selectedExtras;
	}

	public void setSelectedExtras(SelectedExtras selectedExtras) {
		this.selectedExtras = selectedExtras;
	}

	@JsonIgnore
	public void setSelectedExtras(
		UnsafeSupplier<SelectedExtras, Exception>
			selectedExtrasUnsafeSupplier) {

		try {
			selectedExtras = selectedExtrasUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SelectedExtras selectedExtras;

	@Schema
	public Boolean getSuperiorInstallation() {
		return superiorInstallation;
	}

	public void setSuperiorInstallation(Boolean superiorInstallation) {
		this.superiorInstallation = superiorInstallation;
	}

	@JsonIgnore
	public void setSuperiorInstallation(
		UnsafeSupplier<Boolean, Exception> superiorInstallationUnsafeSupplier) {

		try {
			superiorInstallation = superiorInstallationUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean superiorInstallation;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CalculatorSolar)) {
			return false;
		}

		CalculatorSolar calculatorSolar = (CalculatorSolar)object;

		return Objects.equals(toString(), calculatorSolar.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (InstallerCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"InstallerCode\": ");

			sb.append("\"");

			sb.append(_escape(InstallerCode));

			sb.append("\"");
		}

		if (finalPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"finalPrice\": ");

			sb.append("\"");

			sb.append(_escape(finalPrice));

			sb.append("\"");
		}

		if (finalPriceIva != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"finalPriceIva\": ");

			sb.append("\"");

			sb.append(_escape(finalPriceIva));

			sb.append("\"");
		}

		if (input != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"input\": ");

			sb.append(String.valueOf(input));
		}

		if (ivaPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ivaPrice\": ");

			sb.append("\"");

			sb.append(_escape(ivaPrice));

			sb.append("\"");
		}

		if (output != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"output\": ");

			sb.append(String.valueOf(output));
		}

		if (selectedExtras != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectedExtras\": ");

			sb.append(String.valueOf(selectedExtras));
		}

		if (superiorInstallation != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"superiorInstallation\": ");

			sb.append(superiorInstallation);
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}