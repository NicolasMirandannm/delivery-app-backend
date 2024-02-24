package delivery.deliveryapp.domain.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MeasurementType {
    
    @JsonProperty("G")
    GRAM("G"),
    
    @JsonProperty("ML")
    MILILITER("ML"),

    @JsonProperty("KG")
    KILOGRAM("KG"),
    
    @JsonProperty("L")
    LITER("L");

    MeasurementType(String type) {}

}
