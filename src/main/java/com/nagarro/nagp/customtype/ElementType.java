package com.nagarro.nagp.customtype;

public class ElementType {
	public String element, identificationType, identificationValue;

	public void setElement(String element, String identificationType, String identificationValue) {
		this.element = element;
		this.identificationType = identificationType;
		this.identificationValue = identificationValue;
	}

	public String getElement() {
		return element;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public String getIdentificationValue() {
		return identificationValue;
	}
}
