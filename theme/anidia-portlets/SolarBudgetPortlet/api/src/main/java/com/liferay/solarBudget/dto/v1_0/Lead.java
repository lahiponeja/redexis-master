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
@GraphQLName("Lead")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Lead")
public class Lead {

	@Schema(description = "Calculator solar of the Lead")
	@Valid
	public CalculatorSolar getCalculatorSolar() {
		return calculatorSolar;
	}

	public void setCalculatorSolar(CalculatorSolar calculatorSolar) {
		this.calculatorSolar = calculatorSolar;
	}

	@JsonIgnore
	public void setCalculatorSolar(
		UnsafeSupplier<CalculatorSolar, Exception>
			calculatorSolarUnsafeSupplier) {

		try {
			calculatorSolar = calculatorSolarUnsafeSupplier.get();
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
	protected CalculatorSolar calculatorSolar;

	@Schema(description = "Personal information of the Lead")
	@Valid
	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	@JsonIgnore
	public void setPersonalData(
		UnsafeSupplier<PersonalData, Exception> personalDataUnsafeSupplier) {

		try {
			personalData = personalDataUnsafeSupplier.get();
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
	protected PersonalData personalData;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Lead)) {
			return false;
		}

		Lead lead = (Lead)object;

		return Objects.equals(toString(), lead.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (calculatorSolar != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"calculatorSolar\": ");

			sb.append(String.valueOf(calculatorSolar));
		}

		if (personalData != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"personalData\": ");

			sb.append(String.valueOf(personalData));
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